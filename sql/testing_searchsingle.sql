use queant;
-- select * from product where product.is_deposit=false and bank_id=10001;

-- SELECT * FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- where product.is_deposit=false and options.rsrv_type=false and bank_id=10001
-- order by options.base_rate DESC;

-- -- Left join Condition
-- SELECT * FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- left join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003') 
-- where product.is_deposit=false and options.rsrv_type=false 
-- order by options.base_rate DESC;

-- SELECT * FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- inner join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003') 
-- where product.is_deposit=false and options.rsrv_type=false 
-- order by options.base_rate DESC;

-- -- inner join이 맞으나 문제가 생김 -> 급여이체 3~6개월 :0.5, 6~12:1.0 이런식으로 우대금리가 올라가는것이 있어서 우대금리 순으로 불가능할 듯
-- SELECT product.product_id,product.name,Round(options.base_rate+ sum(conditions.special_rate),3) as rate FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- inner join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- where product.is_deposit=false and options.rsrv_type=false 
-- group by product_id
-- order by rate DESC;

-- -- SELECT product.product_id,product.name,Round(options.base_rate+ sum(conditions.special_rate),3) as rate FROM product
-- -- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- -- left join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- -- where product.is_deposit=false and options.rsrv_type=false 
-- -- group by product_id
-- -- order by rate DESC;

-- -- SELECT * FROM product
-- -- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- -- inner join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- -- where product.is_deposit=false and options.rsrv_type=false 
-- -- order by options.base_rate DESC;

-- -- SELECT * FROM product
-- -- inner join options on (product.product_id=options.product_id and options.save_term=12)  
-- -- where product.is_deposit=false and options.rsrv_type=false 
-- -- order by options.base_rate DESC;

-- SELECT product.product_id,product.name,Round(options.base_rate+ max(conditions.special_rate),3) as rate FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- inner join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- where product.is_deposit=false and options.rsrv_type=false 
-- group by product_id
-- order by rate DESC;

-- SELECT product.product_id,product.name,options.base_rate, max(conditions.special_rate) as rate FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- inner join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- where product.is_deposit=false and options.rsrv_type=false 
-- group by product_id
-- order by rate DESC;

-- SELECT product.product_id,product.name,options.base_rate, max(conditions.special_rate),conditions.scode_id as rate FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- left join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- where product.is_deposit=false and options.rsrv_type=false 
-- group by product_id
-- order by rate DESC;

-- SELECT product.product_id FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- left join conditions on (product.product_id=conditions.product_id and conditions.scode_id='B003')  
-- where product.is_deposit=false and options.rsrv_type=false 
-- group by product_id;

SELECT product.product_id,product.name,Round(options.base_rate+ ifnull(max(conditions.special_rate),0),3) as rate,max(conditions.special_rate) ,conditions.scode_id FROM product
inner join options on (product.product_id=options.product_id and options.save_term=12) 
left join conditions on (product.product_id=conditions.product_id and conditions.scode_id in ('B001','B002','B003'))  
where product.is_deposit=false and options.rate_type = false and options.rsrv_type=false and coalesce(product.budget_min,-9223372036854775808) <=0 
group by product_id
order by rate DESC,product.name asc;

-- SELECT * FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- where product.is_deposit=false and coalesce(product.budget_min,-9223372036854775808) <=0 and options.rate_type = false and options.rsrv_type=false 
-- order by options.base_rate DESC;

-- SELECT * FROM product
-- inner join options on (product.product_id=options.product_id and options.save_term=12) 
-- where product.is_deposit=false and options.rate_type = false and options.rsrv_type=false 
-- order by options.base_rate DESC;


SELECT product.product_id,product.name,Round(options.base_rate,3) as rate FROM product
inner join options on (product.product_id=options.product_id and options.save_term=12) 
where product.is_deposit=false and options.rate_type = false and options.rsrv_type=false and coalesce(product.budget_min,-9223372036854775808) <=0 
group by product_id
order by rate DESC,product.name asc;