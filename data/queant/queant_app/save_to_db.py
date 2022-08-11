#필요한 라이브러리 import
import requests
import urllib.request
from bs4 import BeautifulSoup
import xml.etree.ElementTree as ET
from pymysql import NULL
import pymysql
import re

def max_min_join(join_member):
    min_age = None
    max_age = None
    join_member = join_member.replace(" ", "")
    if "만" in join_member and "세" in join_member:
        ages = re.findall('만(.+?)세', join_member)
        ages = list(map(int, ages))
        if len(ages) == 1:
            if "이상" in join_member:
                min_age = ages[0]
            elif "초과" in join_member:
                min_age = ages[0] + 1
            elif "이하" in join_member:
                max_age = ages[0]
            elif "미만" in join_member:
                max_age = ages[0] - 1
        else:
            ages.sort()
            min_age = int(ages[0])
            max_age = int(ages[1])
    return min_age, max_age

#string형태의 가격을 int형으로 변환
def change_cost(cost):
    multi = 1
    if "억원" in cost:
        multi = 100000000
    elif "천만원" in cost:
        multi = 10000000
    elif "백만원" in cost:
        multi = 1000000
    elif "만원" in cost:
        multi = 10000
    elif "천원" in cost:
        multi = 1000
    
    number = 0
    for ch in cost:
        if ch >= '0' and ch <= '9':
            number = number * 10 + int(ch)
    return number*multi

#최대 최소금액 추출
def max_min_cost(etc_note):
    min_cost = None
    max_cost = None
    etc_note = etc_note.replace(" ", "")
    etc_note = etc_note.replace("\n","")
    etc_note = etc_note.replace(",","")
    tags = ["계약금액","최소금액","한도","가입금액","최저","이상","최대","이하","최고"]
    
    #기본적인 문구들이 포함되어있지 않으면 가격과 무관하므로 return 해준다.
    filter1 = 0
    for tag in tags:
        if tag in etc_note:
            filter1 = 1
           
    if filter1 == 0:
        return min_cost, max_cost
    
    flag = 0 #숫자가 등장하면 0이 됨
    costs = []
    costs_num = []
    cost = ""
    for i in etc_note:
        if i >= '0' and i <= '9' and flag == 0: #숫자가 처음 등장했을 경우
            flag = 1
            cost += i
            continue
        if flag == 1:
            if i >= '0' and i <= '9':
                cost += i
            elif i == '만' or i == '억' or i == '천' or i == '백' or i == '십':
                cost += i
            elif i == '원':
                cost += i
                flag = 0
                costs.append(cost)
                cost = ""
            else:
                flag = 0
                cost = ""
    for n in costs:
        costs_num.append(change_cost(n))
    if not costs_num:
        return min_cost, max_cost
    min_cost = min(costs_num)
    max_cost = max(costs_num)
    
    if min_cost == max_cost:
        if "이상" in etc_note:
            max_cost = None
        elif "이하" in etc_note or "미만" in etc_note:
            min_cost = None
        elif "한도":
            min_cost = None
    
    return min_cost, max_cost

#특수문자 (숫자+동그라미)를 일반 숫자. 로 바꾸는 메서드
def spcl_number(st):
    for i in range(10):
        st = st.replace(chr(9312+i), chr(49+i)+'.')
    return st

#한줄로 되어있는 조건들 깔끔하게 파싱
def split_line_num(st):
    st = spcl_number(st)
    p = re.compile("[0-9][.][^0-9]")
    cnt = p.finditer(st)
    #print(cnt)
    if cnt:
        num = 0
        for i in cnt:
            index = i.start()
            st_front = st[:index+num]
            st_back = st[index+num:]
            if st_front == "":
                continue
            if st_front[-1] == '\n':
                continue
            st = st_front + '\n' + st_back
            num+=1

    return st

def filter_spcl(split_spcl):
    spcl_list = []
    for i in split_spcl:
        if i.isspace():
           continue
        if not i:
            continue
        spcl_list.append(i)
        
    return spcl_list

#몇 퍼센트인지 뽑아내는 메서드
def find_percent(tag):
    k = re.compile("[0-9][.][0-9]+[%]")
    per = k.search(tag)
    if per:
        rate = per.group()
        rate = rate.replace("%","")
        return float(rate)
    else:
        return None
    
# 앞부분 목록 리스트 나타내는 표시들 없애주는 메서드
def delete_number(tag):
    tag_front = tag[:10]
    tag_back = tag[10:]
    tag_front = tag_front.replace("-", "")
    tag_front = tag_front.replace("▶", "")
    tag_front = tag_front.replace("*", "")
    tag_front = tag_front.replace("-", "")
    p = re.compile("[0-9][.][^0-9]")
    is_num = p.search(tag)
    if is_num:
        idx = is_num.end()
        tag = tag_front[(idx-1):] + tag_back
    else:
        tag = tag_front + tag_back
    
    tag = tag.lstrip()
    return tag

def spcl_parsing(spcl):
    spcl = spcl.replace("  :", " ")
    spcl = spcl.replace(":  "," ")
    spcl = spcl.replace("  0", " :0")
    p = re.compile("[주][\n][ㄱ-ㅎ|ㅏ-ㅣ|가-힣]")
    check = p.search(spcl)
    if check:
        spcl = spcl.replace("\n", " ")
    spcl = spcl.replace("  ", "\n")
    spcl = split_line_num(spcl)
    
    split_spcl = spcl.split("\n")
    split_spcl = filter_spcl(split_spcl)

    special_list = ["비대면","모바일","급여","연금","공과금","재예치","입출금통장","첫거래","카드","인터넷"]

    flag = 0
    all_percent = 0
    data_list = []
    for tag in split_spcl:
        #첫번째줄에 전체 항목에 대한 퍼센트가 있을경우
        if flag == 0 and tag == split_spcl[0]:
            if "항목" in tag:
                flag = 1
                new_tag = tag[tag.find("항목"):]
                all_percent = find_percent(new_tag)
                continue
        
        if "최고" in tag or "최대" in tag:
            if tag == split_spcl[0]:
                continue
                            
        percent = find_percent(tag)
        if percent == None:
            if flag == 1:
                percent = all_percent
            else:
                continue
        specials=[]
        for tag_list in special_list:
            if tag_list in tag:
                specials.append(tag_list)
        if not specials:
            specials.append("기타")
        
        tag = delete_number(tag)
        data = (specials, percent, tag)
        data_list.append(data)
    return data_list   


def change_tel(tel):
    if len(tel) == 8:
        tel_front = tel[:4]
        tel_back = tel[4:]
        tel = tel_front + "-" + tel_back
    elif len(tel) == 9:
        tel_front = tel[:2]
        tel_middle = tel[2:5]
        tel_back = tel[5:]
        tel = tel_front + '-' + tel_middle + '-' + tel_back
    elif len(tel) == 10 and tel[0] == '0' and tel[1] == '2':
        tel_front = tel[:2]
        tel_middle = tel[2:6]
        tel_back = tel[6:]
        tel = tel_front + '-' + tel_middle + '-' + tel_back
    elif len(tel) == 10:
        tel_front = tel[:3]
        tel_middle = tel[3:6]
        tel_back = tel[6:]
        tel = tel_front + '-' + tel_middle + '-' + tel_back
    elif len(tel) == 11:
        tel_front = tel[:3]
        tel_middle = tel[3:7]
        tel_back = tel[7:]
        tel = tel_front + '-' + tel_middle + '-' + tel_back
    return tel

def change_name(bank_name):
    new_name = bank_name.replace("주식회사","")
    new_name = new_name.replace("저축은행","저축")
    
    if "키움예스" in new_name:
        return "키움"
    
    elif "상상인" in new_name:
        return "상상인"
    
    elif "한국투자" in new_name:
        return "한투"
    
    elif "한국투자" in new_name:
        return "한투"
    
    elif "대아상호" in new_name:
        return "대아"
    
    elif "머스트삼일" in new_name:
        return "머스트"
    
    elif "에스앤티" in new_name:
        return "SNT"
    
    elif "솔브레인" in new_name:
        return "솔브"
    
    elif "엔에이치" in new_name:
        return "NH"
    
    elif "JT친애" in new_name:
        return "친애"
    
    elif "스탠다드차타드" in new_name:
        return "SC제일"
    
    elif "한국산업은행" in new_name:
        return "KDB"
    
    elif "중소기업은행" in new_name:
        return "IBK"
    
    return new_name.strip()

def connect_db():
    conn = pymysql.connect(host='172.17.0.2', port = 3306, user='queant', password='A201Queant', db = 'queant', charset='utf8mb4')
    cur = conn.cursor()
    return conn, cur
       
def fetch_commoncode(cur):
    # 공통코드에서 필요한 코드를 가져온다.
    cur.execute("SELECT code_id FROM queant.common_code where code_value = \"가입 방법\"")
    row = cur.fetchone()
    if row != None:
        common_code_join = row[0] #A가 저장됨
        
    cur.execute("SELECT code_id FROM queant.common_code where code_value = \"우대 사항\"")
    row = cur.fetchone()
    if row != None:
        common_code_condition = row[0] #A가 저장됨
        
    cur.execute("SELECT code_id FROM queant.common_code where code_value = \"상품 등록 경로\"")
    row = cur.fetchone()
    if row != None:
        common_code_product = row[0] #A가 저장됨
    return common_code_join, common_code_condition, common_code_product

def fetch_specificcode(common_code_join, common_code_condition, common_code_product, cur):
    #가입 방법 코드
    query_find_join = """SELECT * FROM queant.specific_code where code_id = (%s)"""
    cur.execute(query_find_join,common_code_join)
    join_ways = {}
    while True:
        row = cur.fetchone()
        if row == None:
            break
        join_ways[row[2]] = row[0]
        
        
    query_find_condition = """SELECT * FROM queant.specific_code where code_id = (%s)"""
    cur.execute(query_find_condition,common_code_condition)
    condition_tags = {}
    while True:
        row = cur.fetchone()
        if row == None:
            break
        condition_tags[row[2]] = row[0]

    query_find_condition = """SELECT * FROM queant.specific_code where code_id = (%s)"""
    cur.execute(query_find_condition,common_code_product)
    product_tags = {}
    while True:
        row = cur.fetchone()
        if row == None:
            break
        product_tags[row[2]] = row[0]
    return join_ways, condition_tags, product_tags

def fetch_commoncode_trait(cur):
    # 공통코드에서 필요한 코드를 가져온다.
    cur.execute("SELECT code_id FROM queant.common_code where code_value = \"특징\"")
    row = cur.fetchone()
    if row != None:
        common_code_trait = row[0]
        
    return common_code_trait

def fetch_specificcode_trait(common_code_trait, cur):
    #가입 방법 코드
    query_find_join = """SELECT * FROM queant.specific_code where code_id = (%s)"""
    cur.execute(query_find_join,common_code_trait)
    trait_tags = {}
    while True:
        row = cur.fetchone()
        if row == None:
            break
        trait_tags[row[2]] = row[0]
    return trait_tags


#상품 table에 정보들을 담는다.
#python은 전부다 %s를 써야함.
def save_into_db(cur, conn, data_xml, is_deposit):
    query_prdt_search = """select * from queant.product where product_code = (%s) and bank_id = (%s) and name = (%s);""" #중복체크 확인 쿼리문
    query_prdt_update_search = """select term_min, term_max from queant.product where product_id = (%s);""" #중복체크 확인 쿼리문
    query_join_search = """select * from queant.joinway where product_id = (%s) and scode_id = (%s);"""
    query_option_search = """select * from queant.options where product_id = (%s) and save_term = (%s) and rate_type = (%s);"""
    query_condition_search = """select * from queant.conditions where product_id = (%s) and scode_id = (%s) and condition_info = (%s);"""
    query_condition_null_search = """select * from queant.conditions where product_id = (%s) and scode_id = (%s) and condition_info is null;"""
    query_trait_search = """select * from queant.trait_set where product_id = (%s) and scode_id = (%s)"""
    query_bank_picture_search = """select picture from queant.bank where bank_id = (%s)"""
    #query_bank_condition_search = """select * from queant.bank_conditions where bank_id = (%s) and scode_id = (%s)"""
    query_prdt = """INSERT INTO queant.product (product_code, bank_id, scode_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max, etc, is_enabled, picture) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);""" #데이터 insert 쿼리문
    query_join = """INSERT INTO queant.joinway (product_id, scode_id) values (%s,%s);"""
    query_option = """INSERT INTO queant.options (product_id, base_rate, high_base_rate, save_term, rate_type, rsrv_type) values (%s,%s,%s,%s,%s,%s);"""
    query_condition = """INSERT INTO queant.conditions (product_id, scode_id, special_rate, condition_info) values (%s,%s,%s,%s)"""
    query_trait = """INSERT INTO queant.trait_set (product_id, scode_id) values (%s,%s)"""
    #query_bank_condition = """INSERT INTO queant.bank_conditions (bank_id, scode_id) values (%s, %s)"""
    query_update_prdt = """UPDATE queant.product SET term_min = (%s) ,term_max = (%s) where product_id = (%s)"""
    
    common_code_join, common_code_condition, common_code_product = fetch_commoncode(cur)
    join_ways, condition_tags, product_tags = fetch_specificcode(common_code_join, common_code_condition, common_code_product, cur)
    
    common_code_trait = fetch_commoncode_trait(cur)
    trait_tags = fetch_specificcode_trait(common_code_trait,cur)
    
    for product_tag in data_xml[5]:
        prdt_code = product_tag[0].find("fin_prdt_cd").text #상품코드
        
        bank_id = int(product_tag[0].find("fin_co_no").text) #은행코드
        
        deposit = is_deposit #예금인지 적금인지
        
        prdt_name = product_tag[0].find("fin_prdt_nm").text #상품명
        prdt_name = prdt_name.replace("\n", " ")
        
        join = product_tag[0].find("join_way").text # 가입방법
        join_member = product_tag[0].find("join_member").text #가입제한
        
        etc = product_tag[0].find("etc_note").text #기타
        min_cost, max_cost = max_min_cost(etc) #최소, 최대금액
        age_min, age_max = max_min_join(join_member)
        
        term_min = None
        term_max = None

        if not max_cost:
            max_cost = product_tag[0].find("max_limit").text
        
        cur.execute(query_bank_picture_search, bank_id)
        row = cur.fetchone()
        if row == None:
            picture = None
        else:
            picture = row[0]
    
        #상품 table에 상품 저장
        values = (prdt_code, bank_id, prdt_name)
        cur.execute(query_prdt_search, values)
        row = cur.fetchone()
        if row == None:
            #product_id, bank_id, scode_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max, etc, is_enabled
            values = (prdt_code,bank_id,product_tags["금융감독원API"],deposit,prdt_name, age_min, age_max, term_min, term_max, min_cost, max_cost, etc, 1, picture)
            cur.execute(query_prdt, values)
            prdt_id = cur.lastrowid
        else:
            prdt_id = row[0]

        
        
        #가입방법 table에 가입방법 저장
        for join_way in join_ways:
            if join_way in join:
                values = (prdt_id, join_ways[join_way])
                cur.execute(query_join_search, values)
                if cur.fetchone() == None:
                    cur.execute(query_join, values)
        
        #옵션 체크 후 db저장
        for option_tag in product_tag[1]:
            rate = float(option_tag.find("intr_rate").text)
            high_base_rate = option_tag.find("intr_rate2").text
            if high_base_rate != None:
                high_base_rate = float(option_tag.find("intr_rate2").text)
            save_term = int(option_tag.find("save_trm").text)
            if term_min == None or term_min > save_term:
                term_min = save_term
            if term_max == None or term_max < save_term:
                term_max = save_term
            if option_tag.find("intr_rate_type_nm").text == "단리":
                rate_type = 0
            else:
                rate_type = 1
            
            if deposit == 1:
                rsrv_type = None
            else:
                rsrv_type = option_tag.find("rsrv_type_nm").text
                if rsrv_type == "자유적립식":
                    rsrv_type = 0
                else:
                    rsrv_type = 1
            
            values = (prdt_id, save_term, rate_type)
            cur.execute(query_option_search, values)
            if cur.fetchone() == None:
                values = (prdt_id, rate, high_base_rate ,save_term, rate_type, rsrv_type)
                cur.execute(query_option, values)
        
        cur.execute(query_prdt_update_search, prdt_id)
        update_row = cur.fetchone()
        if update_row != None:
            if update_row[0] == None:
                values= (term_min, term_max, prdt_id)
                cur.execute(query_update_prdt,values)        
            
        #우대사항 table에 조건들 저장
        special = product_tag[0].find("spcl_cnd").text
        spcl_list = spcl_parsing(special)
        for data in spcl_list:
            is_opverlaped = 0
            for tag in data[0]:
                if is_opverlaped == 0:
                    values = (prdt_id, condition_tags[tag], data[1], data[2])
                    check_values = (prdt_id, condition_tags[tag], data[2])
                    is_opverlaped = 1
                    cur.execute(query_condition_search, check_values)
                    if cur.fetchone() == None:
                        cur.execute(query_condition, values)  
                else:
                    values = (prdt_id, condition_tags[tag], data[1], None)
                    check_values = (prdt_id, condition_tags[tag])
                    cur.execute(query_condition_null_search, check_values)
                    if cur.fetchone() == None:
                        cur.execute(query_condition, values)
    conn.commit()
    
def test_connect():
    print("testing!")
    conn, cur = connect_db()
    print("hihi")
    conn.close()

def save_bank_into_db(cur, conn, data_xml, banktype_num):
    cur.execute("SELECT code_id FROM queant.common_code where code_value = \"은행 분류\"")

    row = cur.fetchone()
    if row != None:
        common_code_bank = row[0] #D가 저장됨
        
    query_find_bank = """SELECT * FROM queant.specific_code where code_id = (%s)"""
    cur.execute(query_find_bank,common_code_bank)
    bank_types = {}
    while True:
        row = cur.fetchone()
        if row == None:
            break
        bank_types[row[2]] = row[0]
        
    query_bank_search = """select * from queant.bank where bank_id = (%s);""" #중복체크 확인 쿼리문
    query_bank = """INSERT INTO queant.bank (bank_id, scode_id, bank_name,short_name, homepage, tel, picture) values (%s,%s,%s,%s,%s,%s,%s);""" #데이터 insert 쿼리문
    png_url = "https://queant.s3.ap-northeast-2.amazonaws.com/banks/"

    for bank_tag in data_xml[5]:
        bank_code = bank_tag[0].find("fin_co_no").text
        bank_name = bank_tag[0].find("kor_co_nm").text
        if banktype_num == 0:
            bank_type = bank_types["은행"]
        else:
            bank_type = bank_types["저축은행"]
            
        new_name = change_name(bank_name)
        homepage = bank_tag[0].find("homp_url").text
        tel = change_tel(bank_tag[0].find("cal_tel").text)
        bank_img = png_url + new_name + ".png"
        cur.execute(query_bank_search, bank_code)
        if cur.fetchone() == None:
            values = (bank_code, bank_type, bank_name,new_name, homepage, tel, bank_img)
            cur.execute(query_bank, values)

    conn.commit()

def save_db():
    #DB연결
    conn, cur = connect_db()
    
    #api활용할 url부분
    #각 데이터 별 url 앞부분
    url_deposit = ["https://finlife.fss.or.kr/finlifeapi/savingProductsSearch.xml?auth=", "https://finlife.fss.or.kr/finlifeapi/depositProductsSearch.xml?auth="]
    #발급받은 키
    api_key = "47c0e868fdb16333d47d0e385641c3c0"
    #권역코드
    code_front = "&topFinGrpNo="
    code_bank = ["020000", "030300"]
    #페이지
    page_front = "&pageNo="
    page_num = ["1", "2", "3", "4"]
    
    is_deposit = 0
    url = url_deposit[is_deposit] + api_key + code_front + code_bank[0] + page_front + page_num[0]
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)
    total_num = int(int(data_xml.find("total_count").text) /100)
    
    for i in range(total_num + 1):
        url = url_deposit[is_deposit] + api_key + code_front + code_bank[0] + page_front + page_num[i]
        data_str = urllib.request.urlopen(url).read().decode('euc-kr')
        data_xml = ET.fromstring(data_str)
        save_into_db(cur, conn, data_xml, is_deposit)
        
    url = url_deposit[is_deposit] + api_key + code_front + code_bank[1] + page_front + page_num[0]
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)
    total_num = int(int(data_xml.find("total_count").text) /100)
    
    for i in range(total_num + 1):
        url = url_deposit[is_deposit] + api_key + code_front + code_bank[1] + page_front + page_num[i]
        data_str = urllib.request.urlopen(url).read().decode('euc-kr')
        data_xml = ET.fromstring(data_str)
        save_into_db(cur, conn, data_xml, is_deposit)
    
    is_deposit = 1
    url = url_deposit[is_deposit] + api_key + code_front + code_bank[0] + page_front + page_num[0]
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)
    total_num = int(int(data_xml.find("total_count").text) /100)
    
    for i in range(total_num + 1):
        url = url_deposit[is_deposit] + api_key + code_front + code_bank[0] + page_front + page_num[i]
        data_str = urllib.request.urlopen(url).read().decode('euc-kr')
        data_xml = ET.fromstring(data_str)
        save_into_db(cur, conn, data_xml, is_deposit)
        
    url = url_deposit[is_deposit] + api_key + code_front + code_bank[1] + page_front + page_num[0]
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)
    total_num = int(int(data_xml.find("total_count").text) /100)
    
    for i in range(total_num + 1):
        url = url_deposit[is_deposit] + api_key + code_front + code_bank[1] + page_front + page_num[i]
        data_str = urllib.request.urlopen(url).read().decode('euc-kr')
        data_xml = ET.fromstring(data_str)
        save_into_db(cur, conn, data_xml, is_deposit)
        
    conn.close()
    
    
def save_bank_db():
    conn, cur = connect_db()
    bankcode = ["020000","030300"]
    
    banktype_num = 0
    url = "http://finlife.fss.or.kr/finlifeapi/companySearch.xml?auth=47c0e868fdb16333d47d0e385641c3c0&topFinGrpNo=" + bankcode[banktype_num]+ "&pageNo=1"
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)
    save_bank_into_db(cur, conn, data_xml, banktype_num)
    
    banktype_num = 1
    url = "http://finlife.fss.or.kr/finlifeapi/companySearch.xml?auth=47c0e868fdb16333d47d0e385641c3c0&topFinGrpNo=" + bankcode[banktype_num]+ "&pageNo=1"
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)
    save_bank_into_db(cur, conn, data_xml, banktype_num)
    
    conn.close()
    
    
def save_db_by_myself():
    conn, cur = connect_db()
    cur.execute("INSERT INTO queant.specific_code scode_id,code_id, scode_value values \"C003\",\'C\',\"기타\")")
    cur.execute("UPDATE queant.bank SET short_name = \"상상인플러스\" ,picture = \"https://queant.s3.ap-northeast-2.amazonaws.com/banks/상상인플러스.png\" where bank_name = \"상상인플러스저축은행\"")
    conn.commit()
    conn.close()