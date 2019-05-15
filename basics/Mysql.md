### DataBase

1. 登录`mysql -uroot -p`
2. 显示所有数据库`show databases`
3. 选择数据库`use databasename`
4. 显示表`show tables`

### SELECT

> `SELECT` `FROM` `WHERE`  `GROUP BY` `HAVING` `OREDER BY` `LIMIT`

#### 1.检索
1. select语句: `SELECT cloumnname FROM tablename;`
2. 去除重复: `SELECT DISTINCT vend_id FROM products;`
3. 限制:
	- (5行)`SELECT prod_name FROM products LIMIT 5;`
	- (第5行开始的5行)`SELECT prod_name FROM products LIMIT 5,5;`
5. 完全限定表名
	
	- `SELECT tablename.columnname FROM databasename.tablename`;

#### 2.ORDER BY

	- 升序 `SELECT columnname FROM tablename ORDER BY columnname, columnname2`
	- 降序 `SELECT columnname FROM tablename ORDER BY columnname DESC`
	
#### 3.WHERE

1. where语句 `SELECT prod_name, prod_price FROM products WHERE prod_price = 2.5;`

2. 范围检测 `SELECT prod_name, prod_price FROM products WHERE prod_price BETWEEN 5 AND 10;`

3. null检测 `SELECT * FROM recipe WHERE record IS NULL;`

4. AND `SELECT prod_name, prod_price FROM products WHERE prod_price = 2.5 AND vend_id =5;`

5. OR `SELECT prod_name, prod_price FROM products WHERE prod_price = 2.5 OR vend_id =5;`

6. IN `SELECT prod_name, prod_price FROM products WHERE vend_id IN (2002, 2003)`

7. NOT `SELECT prod_name, prod_price FROM products WHERE vend_id NOT IN (2002, 2003)`

#### 4. LIKE
1. `%`任意字符任意次数 `SELECT prod_name, prod_price FROM products WHERE prod_name LIKE 'jet%'`

2. `_`下划线匹配任意字符单个 `SELECT prod_name, prod_price FROM products WHERE prod_name LIKE 'jet_'`

#### 5. 正则
1. REGEXP `SELECT * FROM recipe WHERE name  REGEXP '.*炒饭.*'`

2. 进行OR匹配 `SELECT * FROM recipe WHERE name  REGEXP '炒饭|肉' ` 

3. 区间 `SELECT * FROM recipe WHERE name  REGEXP '[123]'`

4. mysql 正则表达转义需要双`\\` 匹配`.`-> `\\.`

5. 字符类 `SELECT * FROM recipe WHERE name  REGEXP '[[:alpha:]]{4}'`

6. 定位符 `SELECT * FROM recipe WHERE name  REGEXP '^[[:alpha:]]{4}$'`
	
	- `^` 文本开始
	- `$` 文本结束

#### 6. 拼接
1. concat `SELECT concat(name,'(', data, ')') FROM recipe;`

2. 别名 `SELECT concat(name,'(', data, ')') as recipe_data FROM recipe;`

3. 执行算术计算 `SELECT prod_id, quantity*item_price AS expanded_price FROM orderitems WHERE order_num = 20005`; 

4. 文本处理函数 `SELECT vend_name, Upper(vend_name) AS vend_name_upcase FROM vendors;` 

5. 日期处理函数 `SELECT mobile FROM user WHERE Date(create_at) BETWEEN '2019-01-01' AND '2019-12-31';`

6. 数值处理函数 `Abs(), Sqrt()`

#### 7. 汇总数据

1. AVG() (avg函数忽略null行) `SELECT AVG(prod_price) AS avg_price FROM products;`

2. COUNT() `SELECT count(*) FROM user;`

3. MAX() `SELECT MAX(prod_price) FROM products;`

4. MIN() `SELECT MIN(prod_price) FROM products;`

5. SUM() `SELECT sum(quantity) FROM products;`

#### 8. 分组数据

1. GROUP BY `SELECT vend_id, COUNT(*) FROM products GROUP BY vend_id;`

#### 9. 联结表

1. INNER JOIN ON `SELECT vend_name, prod_name, prod_price FROM vendors INNER JOIN products ON vendors.vend_id = products.vend_id;`

### INSERT

1. 插入使用列名 `INSERT INTO customers(address) VALUES("hz");`
2. 插入多行 `INSERT INTO customers(address) VALUES("hz"),("SH");`
3. 插入检索出的数据 `INSERT INTO users(user_id) SELECT user_id FROM userold;`

### UPDATE
1. 组成 `UPDATE customers SET cust_email = "test@163.com", cust_name = "The Fudds" WHERE cust_id = 10005;`
	1. 要更新的表
	2. 列名和它们的新值
	3. 确定要更新行的过滤条件

### DELETE
1. `DELETE FROM customers WHERE cust_id = 10006;`

* 使用`UPDATE`和`DELETE`之前应先用`SELECT`进行测试

## Spring Data Jpa
1. [spring-data-jpa官方文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
2. [Spring Data JPA @Query](https://www.baeldung.com/spring-data-jpa-query)	
