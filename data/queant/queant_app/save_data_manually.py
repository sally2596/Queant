#필요한 라이브러리 import
import requests
import urllib.request
from bs4 import BeautifulSoup
import xml.etree.ElementTree as ET
from pymysql import NULL
import pymysql
import re

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
            
def save_into_db(conn, cur, datas):
    query_prdt_search = """select * from queant.product where bank_id = (%s) and name = (%s);""" #중복체크 확인 쿼리문
    query_prdt_update_search = """select term_min, term_max from queant.product where product_id = (%s);""" #중복체크 확인 쿼리문
    query_bank_search = """select bank_id from queant.bank where bank_name = (%s);"""
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
    
    for data in datas:
        bank_name = data["bank"]
        
        cur.execute(query_bank_search, bank_name)
        row = cur.fetchone()
        bank_id = row[0]
        
        prdt_name = data["name"]
        
        cur.execute(query_bank_picture_search, bank_id)
        row = cur.fetchone()
        if row == None:
            picture = None
        else:
            picture = row[0]
        
        value = (bank_id, prdt_name)
        cur.execute(query_prdt_search, value)
        #(bank_id, scode_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max, etc, is_enabled, picture)
        row = cur.fetchone()
        if row == None:
            value = (bank_id, product_tags["admin"], data["is_deposit"], prdt_name, data["age_min"], data["age_max"],data["term_min"],data["term_max"], data["budget_min"], data["budget_max"], data["etc"], 1, picture)
            cur.execute(query_prdt, value)
            prdt_id = cur.lastrowid
        else:
            prdt_id = row[0]
            
        values = (prdt_id, prdt_name)
        
        for join_way in join_ways:
            if join_way in data["join"]:
                values = (prdt_id, join_ways[join_way])
                cur.execute(query_join_search, values)
                if cur.fetchone() == None:
                    cur.execute(query_join, values)
                    
        for opt in data["option"]:
            values = (prdt_id, opt[2], opt[3])
            cur.execute(query_option_search, values)
            if cur.fetchone() == None:
                values = (prdt_id,) + opt
                cur.execute(query_option, values)


        for condition in data["conditions"]:
            is_opverlaped = 0
            for tag in condition[0]:
                if is_opverlaped == 0:
                    values = (prdt_id, condition_tags[tag], condition[1], condition[2])
                    check_values = (prdt_id, condition_tags[tag], condition[2])
                    is_opverlaped = 1
                    cur.execute(query_condition_search, check_values)
                    if cur.fetchone() == None:
                        cur.execute(query_condition, values)
                else:
                    values = (prdt_id, condition_tags[tag], condition[1], None)
                    check_values = (prdt_id, condition_tags[tag])
                    cur.execute(query_condition_null_search, check_values)
                    if cur.fetchone() == None:
                        cur.execute(query_condition, values)
                        
    conn.commit()

def save_into_db_soldier(conn, cur, datas):
    query_prdt_search = """select * from queant.product where bank_id = (%s) and name = (%s);""" #중복체크 확인 쿼리문
    query_prdt_update_search = """select term_min, term_max from queant.product where product_id = (%s);""" #중복체크 확인 쿼리문
    query_bank_search = """select bank_id from queant.bank where bank_name = (%s);"""
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
    
    for data in datas:
        bank_name = data["bank"]
        
        cur.execute(query_bank_search, bank_name)
        row = cur.fetchone()
        bank_id = row[0]
        
        prdt_name = data["name"]
        
        cur.execute(query_bank_picture_search, bank_id)
        row = cur.fetchone()
        if row == None:
            picture = None
        else:
            picture = row[0]
        
        value = (bank_id, prdt_name)
        cur.execute(query_prdt_search, value)
        #(bank_id, scode_id, is_deposit, name, age_min, age_max, term_min, term_max, budget_min, budget_max, etc, is_enabled, picture)
        row = cur.fetchone()
        if row == None:
            value = (bank_id, product_tags["admin"], data["is_deposit"], prdt_name, data["age_min"], data["age_max"],data["term_min"],data["term_max"], data["budget_min"], data["budget_max"], data["etc"], 1, picture)
            cur.execute(query_prdt, value)
            prdt_id = cur.lastrowid
        else:
            prdt_id = row[0]
            
        values = (prdt_id, prdt_name)  
        
        for join_way in join_ways:
            if join_way in data["join"]:
                values = (prdt_id, join_ways[join_way])
                cur.execute(query_join_search, values)
                if cur.fetchone() == None:
                    cur.execute(query_join, values)
                    
        for opt in data["option"]:
            values = (prdt_id, opt[2], opt[3])
            cur.execute(query_option_search, values)
            if cur.fetchone() == None:
                values = (prdt_id,) + opt
                cur.execute(query_option, values)


        for condition in data["conditions"]:
            is_opverlaped = 0
            for tag in condition[0]:
                if is_opverlaped == 0:
                    values = (prdt_id, condition_tags[tag], condition[1], condition[2])
                    check_values = (prdt_id, condition_tags[tag], condition[2])
                    is_opverlaped = 1
                    cur.execute(query_condition_search, check_values)
                    if cur.fetchone() == None:
                        cur.execute(query_condition, values)
                else:
                    values = (prdt_id, condition_tags[tag], condition[1], None)
                    check_values = (prdt_id, condition_tags[tag])
                    cur.execute(query_condition_null_search, check_values)
                    if cur.fetchone() == None:
                        cur.execute(query_condition, values)
        
        value = (prdt_id, trait_tags["군인"])
        cur.execute(query_trait_search, value)
        if cur.fetchone() == None:
            cur.execute(query_trait,value)
    conn.commit()
        

def save_db_manually():
    conn, cur = connect_db()
    f = open("/data/queant/queant_app/prdt_datas/uri.txt","r")
    pd = f.read()
    f.close()
    product_data = eval(pd)

    save_into_db(conn, cur, product_data)
    conn.close()
    
def save_db_manually_sondier():
    conn, cur = connect_db()
    f = open("/data/queant/queant_app/prdt_datas/soldier.txt","r")
    pd = f.read()
    f.close()
    soldier_data = eval(pd)
    save_into_db_soldier(conn, cur, soldier_data)
    conn.close()