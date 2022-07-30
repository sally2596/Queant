def save_prdt():
    #필요한 라이브러리 import
    import requests
    import urllib.request
    from bs4 import BeautifulSoup
    import xml.etree.ElementTree as ET
    from pymysql import NULL
    import pymysql
    import re

    print("save start!")
    
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
    url = url_deposit[is_deposit] + api_key + code_front + code_bank[is_deposit] + page_front + page_num[0]
    #print(url)
    data_str = urllib.request.urlopen(url).read().decode('euc-kr')
    data_xml = ET.fromstring(data_str)

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

    # 공통코드에서 가입방법의 코드를 가져온다.
    cur.execute("SELECT code_id FROM queant.commoncode where code_value = \"가입 방법\"")

    row = cur.fetchone()
    if row != None:
        code_id = row[0] #A가 저장됨
        
    #가입 방법 코드
    query_find_common = """SELECT * FROM queant.specificcode where code_id = (%s)"""
    cur.execute(query_find_common,code_id)

    join_ways = {}
    while True:
        row = cur.fetchone()
        if row == None:
            break
        join_ways[row[2]] = int(row[0])
        
    #print(join_ways)

    #상품 table에 정보들을 담는다.
    #python은 전부다 %s를 써야함.
    query_prdt_search = """select * from queant.product where product_id = (%s);""" #중복체크 확인 쿼리문
    query_join_search = """select * from queant.joinway where product_id = (%s) and code_id = (%s) and scode_id = (%s);"""
    query_option_search = """select * from queant.options where product_id = (%s) and save_term = (%s) and rate_type = (%s);"""
    query_prdt = """INSERT INTO queant.product (product_id, bank_id, is_deposit, name, age_min, age_max, term_min, term_max) values (%s,%s,%s,%s,%s,%s,%s,%s);""" #데이터 insert 쿼리문
    query_join = """INSERT INTO queant.joinway (product_id, code_id, scode_id) values (%s,%s,%s);"""
    query_option = """INSERT INTO queant.options (product_id, base_rate, save_term, rate_type, rsrv_type) values (%s,%s,%s,%s,%s);"""
    for product_tag in data_xml[5]:
        prdt_id = product_tag[0].find("fin_prdt_cd").text #상품코드
        
        bank_id = int(product_tag[0].find("fin_co_no").text) #은행코드
        
        deposit = is_deposit #예금인지 적금인지
        
        prdt_name = product_tag[0].find("fin_prdt_nm").text #상품명
        prdt_name = prdt_name.replace("\n", " ")
        
        join = product_tag[0].find("join_way").text # 가입방법
        join_member = product_tag[0].find("join_member").text #가입제한
        
        age_min, age_max = max_min_join(join_member)    
        term_min = None
        term_max = None
        
        #가입방법 table에 가입방법 저장
        for join_way in join_ways:
            if join_way in join:
                values = (prdt_id, code_id, join_ways[join_way])
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
                rsrv_tpye = None
            else:
                rsrv_type = option_tag.find("rsrv_type_nm").text
            values = (prdt_id, save_term, rate_type)
            cur.execute(query_option_search, values)
            if cur.fetchone() == None:
                values = (prdt_id, rate, save_term, rate_type, rsrv_tpye)
                cur.execute(query_option, values)
                
        #상품 table에 상품 저장
        cur.execute(query_prdt_search, prdt_id)
        if cur.fetchone() == None:
            values = (prdt_id,bank_id,deposit,prdt_name, age_min, age_max, term_min, term_max)
            cur.execute(query_prdt, values)
        
    conn.commit()
    conn.close()
    print("save done!")