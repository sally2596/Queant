import urllib.request
import xml.etree.ElementTree as ET
from pymysql import NULL
import pymysql

bankcode = ["020000","030300"]
banktype_num = 0
url = "http://finlife.fss.or.kr/finlifeapi/companySearch.xml?auth=47c0e868fdb16333d47d0e385641c3c0&topFinGrpNo=" + bankcode[banktype_num]+ "&pageNo=1"
print(url)

data_str = urllib.request.urlopen(url).read().decode('euc-kr')
data_xml = ET.fromstring(data_str)

conn = pymysql.connect(host="localhost", user='queant', password='queant1234', db = 'queant', charset='utf8mb4')
cur = conn.cursor()

cur.execute("SELECT code_id FROM queant.commoncode where code_value = \"은행 분류\"")

row = cur.fetchone()
if row != None:
    common_code_bank = row[0] #D가 저장됨
    
query_find_bank = """SELECT * FROM queant.specificcode where code_id = (%s)"""
cur.execute(query_find_bank,common_code_bank)
bank_types = {}
while True:
    row = cur.fetchone()
    if row == None:
        break
    bank_types[row[2]] = row[0]
    
query_bank_search = """select * from queant.bank where bank_id = (%s);""" #중복체크 확인 쿼리문
query_bank = """INSERT INTO queant.bank (bank_id, scode_id, bank_name, homepage, tel) values (%s,%s,%s,%s,%s);""" #데이터 insert 쿼리문


for bank_tag in data_xml[5]:
    bank_code = bank_tag[0].find("fin_co_no").text
    bank_name = bank_tag[0].find("kor_co_nm").text
    if banktype_num == 0:
        bank_type = bank_types["은행"]
    else:
        bank_type = bank_types["저축은행"]
    homepage = bank_tag[0].find("homp_url").text
    tel = bank_tag[0].find("cal_tel").text
    cur.execute(query_bank_search, bank_code)
    if cur.fetchone() == None:
        values = (bank_code, bank_type, bank_name, homepage, tel)
        cur.execute(query_bank, values)
        
conn.commit()
conn.close()