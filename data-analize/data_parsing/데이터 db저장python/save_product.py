#필요한 라이브러리 import
import urllib.request
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

conn = pymysql.connect(host="localhost", user='queant', password='queant1234', db = 'queant', charset='utf8mb4')
cur = conn.cursor()       

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

is_deposit = 1
url = url_deposit[is_deposit] + api_key + code_front + code_bank[0] + page_front + page_num[1]
print(url)
data_str = urllib.request.urlopen(url).read().decode('euc-kr')
data_xml = ET.fromstring(data_str)

# 공통코드에서 필요한 코드를 가져온다.
cur.execute("SELECT code_id FROM queant.commoncode where code_value = \"가입 방법\"")

row = cur.fetchone()
if row != None:
    common_code_join = row[0] #A가 저장됨
    
cur.execute("SELECT code_id FROM queant.commoncode where code_value = \"우대 사항\"")
row = cur.fetchone()
if row != None:
    common_code_condition = row[0] #A가 저장됨


#가입 방법 코드
query_find_join = """SELECT * FROM queant.specificcode where code_id = (%s)"""
cur.execute(query_find_join,common_code_join)
join_ways = {}
while True:
    row = cur.fetchone()
    if row == None:
        break
    join_ways[row[2]] = int(row[0])
    
    
query_find_condition = """SELECT * FROM queant.specificcode where code_id = (%s)"""
cur.execute(query_find_condition,common_code_condition)
condition_tags = {}
while True:
    row = cur.fetchone()
    if row == None:
        break
    condition_tags[row[2]] = int(row[0])
    
    
#상품 table에 정보들을 담는다.
#python은 전부다 %s를 써야함.
query_prdt_search = """select * from queant.product where product_id = (%s);""" #중복체크 확인 쿼리문
query_join_search = """select * from queant.joinway where product_id = (%s) and code_id = (%s) and scode_id = (%s);"""
query_option_search = """select * from queant.options where product_id = (%s) and save_term = (%s) and rate_type = (%s);"""
query_condition_search = """select * from queant.conditions where product_id = (%s) and code_id = (%s) and scode_id = (%s) and special_rate = (%s) and condition_info = (%s);"""
query_prdt = """INSERT INTO queant.product (product_id, bank_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);""" #데이터 insert 쿼리문
query_join = """INSERT INTO queant.joinway (product_id, code_id, scode_id) values (%s,%s,%s);"""
query_option = """INSERT INTO queant.options (product_id, base_rate, save_term, rate_type, rsrv_type) values (%s,%s,%s,%s,%s);"""
query_condition = """INSERT INTO queant.conditions (product_id, code_id, scode_id, special_rate, condition_info) values (%s,%s,%s,%s,%s)"""


for product_tag in data_xml[5]:
    prdt_id = product_tag[0].find("fin_prdt_cd").text #상품코드
    
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
       
    #가입방법 table에 가입방법 저장
    for join_way in join_ways:
        if join_way in join:
            values = (prdt_id, common_code_join, join_ways[join_way])
            cur.execute(query_join_search, values)
            if cur.fetchone() == None:
                cur.execute(query_join, values)
    
    #옵션 체크
    for option_tag in product_tag[1]:
        rate = float(option_tag.find("intr_rate").text)
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
        
        values = (prdt_id, save_term, rate_type)
        cur.execute(query_option_search, values)
        if cur.fetchone() == None:
            values = (prdt_id, rate, save_term, rate_type, rsrv_type)
            cur.execute(query_option, values)
            
    #상품 table에 상품 저장
    cur.execute(query_prdt_search, prdt_id)
    if cur.fetchone() == None:
        values = (prdt_id,bank_id,deposit,prdt_name, age_min, age_max, term_min, term_max, min_cost, max_cost)
        cur.execute(query_prdt, values)
        
    #우대사항 table에 조건들 저장
    special = product_tag[0].find("spcl_cnd").text
    spcl_list = spcl_parsing(special)
    for data in spcl_list:
        is_opverlaped = 0
        for tag in data[0]:
            if is_opverlaped == 0:
                values = (prdt_id, common_code_condition, condition_tags[tag], data[1], data[2])
                is_opverlaped = 1
            else:
                values = (prdt_id, common_code_condition, condition_tags[tag], data[1], None)
            
            cur.execute(query_condition_search, values)
            if cur.fetchone() == None:
                cur.execute(query_condition, values)    
            
            
conn.commit()

conn.close()