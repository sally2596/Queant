import requests as req
import pprint
from html_table_parser import parser_functions
import unicodedata
import re
from bs4 import BeautifulSoup
import collections
import pymysql
collections.Callable = collections.abc.Callable

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
        common_code_condition = row[0]
        
    cur.execute("SELECT code_id FROM queant.common_code where code_value = \"상품 등록 경로\"")
    row = cur.fetchone()
    if row != None:
        common_code_product = row[0]
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

#우대이율 관련된 표를 반환하는 메서드
def return_table(content):
    pyo = content.find("table")
    pyo_check_list = ["우대금리", "이율", "이자율", "우대조건"]
    
    while pyo:
        pyo_summary = pyo.get("summary") #summary값 확인
        check_flag = 0 #우대조건 관련 표인지 확인하는 flag변수
        
        for check in pyo_check_list:
            if check in pyo_summary:
                check_flag = 1
                break
        if "기본이율" in pyo_summary or "해지" in pyo_summary:
            check_flag = 0
        
        if check_flag == 0: #우대조건 관련 테이블이 아닌 경우
            content.find("table").decompose()
            pyo = content.find("table")
            continue
        
        else:         
            table = parser_functions.make2d(pyo)
            for group in table:
                length = len(group)
                for i in range(length):
                    group[i] = re.sub('\n|\t|\r','',group[i])
                    if '-' in group[i]:
                        index = group[i].find('-')
                        group[i] = group[i][:index].replace(u'\xa0', u'').replace(chr(9678),"") # ◎제거, \xa0제거
            return table
    return None   
    
    
    #우대이율 관련된 표를 반환하는 메서드
def return_sub_table(content):
    pyo = content.find("table")
    pyo_check_list = ["우대금리", "이율", "이자율", "우대조건"]
    
    while pyo:
        pyo_summary = pyo.get("summary") #summary값 확인
        check_flag = 0 #우대조건 관련 표인지 확인하는 flag변수
        
        for check in pyo_check_list:
            if check in pyo_summary:
                check_flag = 1
                break      
        if check_flag == 0: #우대조건 관련 테이블이 아닌 경우
            content.find("table").decompose()
            pyo = content.find("table")
            continue
        
        else:         
            table = parser_functions.make2d(pyo)
            for group in table:
                length = len(group)
                for i in range(length):
                    group[i] = re.sub('\n|\t|\r','',group[i])
                    if '-' in group[i]:
                        index = group[i].find('-')
                        group[i] = group[i][:index].replace(u'\xa0', u'').replace(chr(9678),"") # ◎제거, \xa0제거
            return table
    return None   
    
    
def extract_data(lists, name, sub_lists):
    join_tag = ["신규가입", "가입채널", "가입방법", "신규가입 및 해지"] #가입방법 tag 통합해줄 것들
    list = lists.find_all()

    data_list = {}

    title = None
    main_content = ""
    table = return_table(lists)

    sub_table = return_sub_table(sub_lists)

    
    tb = lists.find("table")
    
    if tb:
        lists.find("table").decompose()

    #상품이름 저장
    data_list["상품명"] = name
    
    for tag in list:
        if tag.name == "dt":
            if title:
                if title in join_tag:
                    data_list["가입방법"] = main_content
                elif title == "가입대상":
                    data_list["가입대상"] = main_content
                elif title == "가입금액":
                    data_list["가입금액"] = main_content
                elif title == "예금종류":
                    data_list["예금종류"] = main_content
                title = None
                main_content = ""
            title = tag.text
        elif tag.name == "dd":
            main_content += tag.text.strip()
            
    return [data_list, table, sub_table]


#모든 상품들의 url을 크롤링하는 부분
def get_contents():
    main_url = "https://epostbank.go.kr/IBHttpChannel.channel?com_cmd=ibig0820&act_type=ib&class_cd=&rcmdNum=01&rcmd_code=03&LeftMenuCnt=020000&TopMenuCnt=010102&allType=Y&selOrder=1&srch_dvsn=&good_kind=&srch_nm=&currentPage="
    common_url = "https://www.epostbank.go.kr"
    url_list = []
    i = 1
    while True:
        url = main_url + str(i)
        res = req.get(url)
        res.encoding = 'euc-kr'
        main_html = res.text
        soup = BeautifulSoup(main_html, 'html.parser')
        pro_tit = soup.find_all(class_='pro_tit')
        if not pro_tit:
            break
        for pt in pro_tit:
            url_list.append("https://www.epostbank.go.kr" + pt.find('a').get('href'))
        i += 1
            
    contents = []
    url_num = 1
    for url in url_list:
        res_url = req.get(url)
        res_url.encoding = 'euc-kr'
        html = res_url.text
        html = BeautifulSoup(html,'lxml')
        
        prdt_name = html.find(class_="sub_mtit").find("dt").text #상품 이름
        content = html.find(class_="titGreen")
        
        new_addr = html.find("iframe").get("src")
        new_url = common_url + new_addr
        sub_url = req.get(new_url)
        sub_url.encoding = 'euc-kr'
        sub_html = sub_url.text
        sub_html = BeautifulSoup(sub_html,'lxml')
        
        contents.append(extract_data(content, prdt_name, sub_html))
        url_num += 1
    return contents


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


def max_min_join(join_member):
    min_age = None
    max_age = None
    join_member = join_member.replace(" ", "")
    age_list = []
    if "만" in join_member and "세" in join_member:
        ages = re.findall('[0-9]*[세]', join_member)
        for age in ages:
            age = age.replace("세","")
            age_list.append(age)
        age_list = list(map(int, age_list))
        if len(ages) == 1:
            if "이상" in join_member:
                min_age = age_list[0]
            elif "초과" in join_member:
                min_age = age_list[0] + 1
            elif "이하" in join_member:
                max_age = age_list[0]
            elif "미만" in join_member:
                max_age = age_list[0] - 1
        else:
            age_list.sort()
            min_age = int(age_list[0])
            max_age = int(age_list[1])
    return min_age, max_age


def make_join_list(register_way):
    if "해지" in register_way:
        out = register_way.find("해지")
        register = register_way.find("가입")
        cha = out - register
        if cha > 4:
            join_data = register_way[:out]
        else:
            join_data = register_way
    else:
        join_data = register_way
        
    join_way_lists = []
    if "우체국창구" in join_data:
        join_way_lists.append("영업점")
    if "인터넷뱅킹" in join_data:
        join_way_lists.append("인터넷")
    if "스마트뱅킹" in join_data:
        join_way_lists.append("스마트폰")
    if not join_way_lists:
        join_way_lists.append("영업점")
        
    return join_way_lists
        
        
        
def condition_content(pyo_data):
    k = re.compile("[0-9][.][ ]")
    check = k.search(pyo_data[:4])
    if check:
        pyo_data = k.sub('',pyo_data)
    pyo_data = re.sub('\n|\t|\r','',pyo_data)
    pyo_data = re.sub("[주][0-9]*[」]","",pyo_data)
    pyo_data = re.sub('\n|\t|\r','',pyo_data)
    pyo_data = pyo_data.replace(u'\xa0', u'').replace(chr(9678),"") # ◎제거, \xa0제거
    pyo_data = pyo_data.replace("           ", " ")
    return pyo_data

#몇 퍼센트인지 뽑아내는 메서드
def find_percent(tag):
    k = re.compile("[0-9][.][0-9]*[%]*")
    per = k.search(tag)
    if per:
        rate = per.group()
        rate = rate.replace("%","")
        return float(rate)
    else:
        return None

def matching_spcl(pyo_data, rate):
    spcl_list = ["비대면","모바일","급여","연금","공과금","재예치","입출금통장","첫거래","카드","인터넷"]
    pyo_data = pyo_data.replace("첫 거래", "첫거래")
    specials = []
    data_list = []
    for tag_list in spcl_list:
        if tag_list in pyo_data:
            specials.append(tag_list)
    if not specials:
        specials.append("기타")
        
    data = (specials, rate, pyo_data)
    return data

def parsing_conditions(pyo):
    condition_list = []
    if not pyo:
        return condition_list
    check_list = ["우대이율", "이자율", "이율"]
    checking = pyo[0][-1]
    if "우대이율" in  checking or "이자율" in checking or "이율" in checking:
        if "제공조건" in checking:
            all_rate = 0.1
            for i in range(1, len(pyo)):
                pyo_data = pyo[i][0]
                pyo_data = condition_content(pyo_data)
                rate = float(0.1)
                data = matching_spcl(pyo_data, rate)
                condition_list.append(data)

        elif "기간별" in checking:
            return condition_list
        
        elif "평잔" in pyo[0][0]:
            rate_index = -1
            for i in range(1,len(pyo)):
                pyo_data = "예금평잔: " + pyo[i][0]
                pyo_data = condition_content(pyo_data)
                rate = find_percent(pyo[i][rate_index])
                data = matching_spcl(pyo_data, rate)
                condition_list.append(data)   
        else:      
            rate_index = -1
            for i in range(1, len(pyo)):
                pyo_data = pyo[i][0]
                if len(pyo[0]) > 2:
                    for j in range(1, len(pyo[0])-1):
                        pyo_data += "-"
                        pyo_data += pyo[i][j]
                pyo_data = condition_content(pyo_data)
                rate = find_percent(pyo[i][rate_index])
                data = matching_spcl(pyo_data, rate)
                condition_list.append(data)
    
    
    elif "고객군별" in checking:
        rate_index = 1
        for i in range(2,len(pyo)):
            pyo_data = pyo[i][0]
            pyo_data = condition_content(pyo_data)
            rate = find_percent(pyo[i][rate_index])
            data = matching_spcl(pyo_data, rate)
            condition_list.append(data)
            
    else:
        rate_index = pyo[0].index("개인")
        for i in range(1, len(pyo)):
            pyo_data = pyo[i][0]
            pyo_data = condition_content(pyo_data)
            rate = find_percent(pyo[i][rate_index])
            data = matching_spcl(pyo_data, rate)
            condition_list.append(data)
            
    return condition_list


def parsing_term(term_text):
    term_check = re.search("[0-9]*[년|개][월]*", term_text)
    term = None
    if term_check:
        term = term_check.group()
        if "년" in term:
            term = term.replace("년","")
            term = int(term)*12
        elif "개월" in term:
            term = term.replace("개월","")
            term = int(term)
            
    return term
    
    
def parsing_rate(rate_text):
    rate = re.search("[0-9][.][0-9]*", rate_text)
    if rate:
        rate = rate.group()
        rate = float(rate)
    return rate


def parsing_option(pyo, datas): # 표, 저축방식, 상품이름
    main_div = pyo[1][0] #구분의 기준
    main_div2 = pyo[1][1]
    main_len = len(pyo[1]) # 첫번째 raw의 길이
    
    term_index = 2
    rate_index = 3
    max_rate_index = 4
    
    #복리로 저장
    rate_type = 1
    
    #적립방식 저장
    rsrv_type = None
    if "예금종류" in datas:
        if "적립식" in datas["예금종류"]:
            if "자유" in datas["예금종류"]:
                rsrv_type = 0
            else:
                rsrv_type = 1
    elif "적금" in datas["상품명"]:
        rsrv_type = 1
    
    option_list = []

    for i in range(1,len(pyo)):
        #구분이 없을때
        if not main_div:
            term = 12
            rate = parsing_rate(pyo[i][3])
            max_rate = parsing_rate(pyo[i][4])
            if not max_rate:
                max_rate = rate
            if max_rate < rate:
                max_rate = round(rate + max_rate,2)
            option_list.append([rate, max_rate, term, rate_type, rsrv_type])
            break
        
        if pyo[i][0] != main_div:
            break
        
        if len(pyo[i]) != main_len:
            term = parsing_term(pyo[i][1])
            if not term:
                term = 12
            rate = parsing_rate(pyo[i][2])
            max_rate = parsing_rate(pyo[i][3])
            if not rate:
                rate = 2.3
            if not max_rate:
                max_rate = rate
            if max_rate < rate:
                max_rate = round(rate + max_rate,2)
            option_list.append([rate, max_rate, term, rate_type, rsrv_type])
            continue
        
        else:
            if pyo[i][1] != main_div2:
                break
            term = parsing_term(pyo[i][2])
            if not term:
                term = 12
            rate = parsing_rate(pyo[i][3])
            max_rate = parsing_rate(pyo[i][4])
            if not rate:
                rate = 2.3
            if not max_rate:
                max_rate = rate
            if max_rate < rate:
                max_rate = round(rate + max_rate,2)
            option_list.append([rate, max_rate, term, rate_type, rsrv_type])
            continue

            
    return option_list


def save_into_db(cur, conn, contents):
    
    query_bank_search = """select bank_id from queant.bank where bank_name = (%s);"""
    query_prdt_search = """select * from queant.product where bank_id = (%s) and name = (%s);""" #중복체크 확인 쿼리문
    query_prdt_update_search = """select term_min, term_max from queant.product where product_id = (%s);""" #중복체크 확인 쿼리문
    query_join_search = """select * from queant.joinway where product_id = (%s) and scode_id = (%s);"""
    query_option_search = """select * from queant.options where product_id = (%s) and save_term = (%s) and rate_type = (%s);"""
    query_condition_search = """select * from queant.conditions where product_id = (%s) and scode_id = (%s) and condition_info = (%s);"""
    query_condition_null_search = """select * from queant.conditions where product_id = (%s) and scode_id = (%s) and condition_info is null;"""
    query_trait_search = """select * from queant.trait_set where product_id = (%s) and scode_id = (%s)"""
    query_bank_picture_search = """select picture from queant.bank where bank_id = (%s)"""
    #query_bank_condition_search = """select * from queant.bank_conditions where bank_id = (%s) and scode_id = (%s)"""
    query_prdt = """INSERT INTO queant.product (bank_id, scode_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max, etc, is_enabled, picture) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);""" #데이터 insert 쿼리문
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
    
    cur.execute(query_bank_search, "우체국")
    bank_id = cur.fetchone()
    
    
    cur.execute(query_bank_picture_search, bank_id)
    row = cur.fetchone()
    if row == None:
        picture = None
    else:
        picture = row[0]
        
    for i in contents:
        
        #상품명 저장
        prdt_name = i[0]["상품명"]
        
        # 최대 최소금액 파싱 부분 (min_cost, max_cost에 데이터 저장됨)
        if "가입금액" in i[0]:
            min_cost, max_cost = max_min_cost(i[0]["가입금액"])
        else:
            min_cost, max_cost = max_min_cost("-")
        
        #가입 최대금액 최소금액 파싱 부분
        min_age, max_age = max_min_join(i[0]["가입대상"])
        
        #가입방법 make_join_list에 저장
        if "가입방법" in i[0]:
            join_list = make_join_list(i[0]["가입방법"])
        else:
            join_list = make_join_list("")
        
        #예금인지 적금인지 is_deposit에 저장
        if "예금종류" in i[0]:
            if  "적립식" in i[0]["예금종류"]:
                is_deposit = 0
            else:
                is_deposit = 1
        else:
            if "적금" in i[0]["상품명"]:
                is_deposit = 0
            else:
                is_deposit = 1
    
        values = (bank_id, prdt_name)
        cur.execute(query_prdt_search, values)
        row = cur.fetchone()
        if row == None:
            #bank_id, scode_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max, etc, is_enabled
            values = (bank_id,product_tags["우체국API"],is_deposit,prdt_name, min_age, max_age, None, None, min_cost, max_cost, None, 1, picture)
            cur.execute(query_prdt, values)
            prdt_id = cur.lastrowid
        else:
            prdt_id = row[0]
        
        #가입방법 table에 가입방법 저장
        for join_way in join_ways:
            if join_way in join_list:
                values = (prdt_id, join_ways[join_way])
                cur.execute(query_join_search, values)
                if cur.fetchone() == None:
                    cur.execute(query_join, values)
        
        options = parsing_option(i[2], i[0])
        
        term_min = None
        term_max = None
        
        #옵션 저장
        for option in options:
            #option[3]이 기간
            if term_min == None or term_min > option[2]:
                term_min = option[2]
            if term_max == None or term_max < option[2]:
                term_max = option[2]
                
            option.insert(0,prdt_id)
            values = (prdt_id, option[3], option[4])
            cur.execute(query_option_search, values)
            if cur.fetchone() == None:
                values = (option[0],option[1],option[2],option[3],option[4],option[5])
                cur.execute(query_option, values)
        
        
        cur.execute(query_prdt_update_search, prdt_id)
        update_row = cur.fetchone()
        
        if update_row != None:
            if update_row[0] == None:
                values= (term_min, term_max, prdt_id)
                cur.execute(query_update_prdt,values)         
        
        
        spcl_list = parsing_conditions(i[1])
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
        
        
        
def save_postdata_db():
    conn, cur = connect_db()
    contents = get_contents()
    save_into_db(cur, conn, contents)
    conn.close()