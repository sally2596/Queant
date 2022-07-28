#필요한 라이브러리 import
from pymysql import NULL
import pymysql

conn = pymysql.connect(host="localhost", user='queant', password='queant1234', db = 'queant', charset='utf8mb4')
cur = conn.cursor()       

delete_table = [
    """DROP TABLE `queant`.`product`""",
    """DROP TABLE `queant`.`joinway`""",
    """DROP TABLE `queant`.`options`""",
    """DROP TABLE `queant`.`conditions`""",
    """DROP TABLE `queant`.`commoncode`""",
    """DROP TABLE `queant`.`specificcode`""",
    """DROP TABLE `queant`.`bank`"""
]
for drop in delete_table:
    cur.execute(drop)
#db 테이블 생성
make_table = [
  """CREATE TABLE `queant`.`product` (
  `product_id` VARCHAR(50) NOT NULL,
  `bank_id` INT NOT NULL,
  `is_deposit` TINYINT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `age_min` INT NULL,
  `age_max` INT NULL,
  `term_min` INT NULL,
  `term_max` INT NULL,
  `budget_min` INT NULL,
  `budget_max` INT NULL,
  PRIMARY KEY (`product_id`));
  """,
  """ CREATE TABLE if not exists `queant`.`joinway` (
  `joinway_id` INT NOT NULL AUTO_INCREMENT,
  `product_id` VARCHAR(50) NOT NULL,
  `code_id` VARCHAR(2) NOT NULL,
  `scode_id` INT NOT NULL,
  PRIMARY KEY (`joinway_id`));
  """,
  """CREATE TABLE if not exists `queant`.`options` (
  `option_id` INT NOT NULL AUTO_INCREMENT,
  `product_id` VARCHAR(50) NOT NULL,
  `base_rate` FLOAT NOT NULL,
  `save_term` INT NOT NULL,
  `rate_type` INT NOT NULL,
  `rsrv_type` VARCHAR(10) NULL,
  PRIMARY KEY (`option_id`));
  """,
  """CREATE TABLE if not exists `queant`.`conditions` (
    `condition_id` INT NOT NULL AUTO_INCREMENT,
    `product_id` VARCHAR(50) NOT NULL,
    `code_id` VARCHAR(2) NOT NULL,
    `scode_id` INT NOT NULL,
    `special_rate` FLOAT NOT NULL,
    `condition_info` VARCHAR(225) NULL,
    PRIMARY KEY (`condition_id`));
  """,
  """CREATE TABLE if not exists `queant`.`commoncode` (
    `code_id` VARCHAR(2) NOT NULL,
    `code_value` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`code_id`));
  """,
  """CREATE TABLE if not exists `queant`.`specificcode` (
    `scode_id` INT NOT NULL AUTO_INCREMENT,
    `code_id` VARCHAR(2) NOT NULL,
    `scode_value` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`scode_id`));
  """,
  """CREATE TABLE `queant`.`bank` (
  `bank_id` INT NOT NULL,
  `code_id` VARCHAR(2) NOT NULL,
  `scode_id` INT NOT NULL,
  `bank_name` VARCHAR(50) NOT NULL,
  `homepage` VARCHAR(100) NULL,
  `tel` VARCHAR(30) NULL,
  `picture` INT NULL,
  PRIMARY KEY (`bank_id`));
  """
  ]
for query in make_table:
    cur.execute(query)
    
code_values = [
    ('A','가입 방법'),
    ('B',"우대 사항"),
    ('C',"은행 분류"),
]
scode_values = [
    ('A',"인터넷"),
    ('A',"스마트폰"),
    ('A',"영업점"),
    ('A',"전화"),
    ('A',"기타"),
    ('B',"비대면"),
    ('B',"모바일"),
    ('B',"급여"),
    ('B',"연금"),
    ('B',"공과금"),
    ('B',"재예치"),
    ('B',"입출금통장"),
    ('B',"카드"),
    ('B',"인터넷"),
    ('B',"첫거래"),
    ('B',"기타"),
    ('C',"은행"),
    ('C',"저축은행"),
    ]

query_common_search = """select * from queant.commoncode where code_id = (%s) and code_value = (%s);"""
query_specific_search = """select * from queant.specificcode where code_id = (%s) and scode_value = (%s);"""
query_common = """INSERT INTO queant.commoncode (code_id, code_value) values (%s,%s);"""
query_specific = """INSERT INTO queant.specificcode (code_id, scode_value) values (%s,%s);"""

for value in code_values:
    cur.execute(query_common_search, value)
    if cur.fetchone() == None:
        cur.execute(query_common, value)
        
for value in scode_values:
    cur.execute(query_specific_search, value)
    if cur.fetchone() == None:
        cur.execute(query_specific, value)
        
conn.commit()
conn.close()
