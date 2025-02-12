


# 테스트를 위해 사용한 DB, TABLE 생성 쿼리문
```sql
CREATE DATABASE IF NOT EXISTS test_for_templates;
USE test_for_templates;

DROP TABLE IF EXISTS tb_file;
DROP TABLE IF EXISTS tb_member;

CREATE TABLE IF NOT EXISTS tb_member (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nickname VARCHAR(20) NOT NULL UNIQUE KEY,
  password VARCHAR(100) NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_file (
  id INT PRIMARY KEY AUTO_INCREMENT,
  path VARCHAR(500) NOT NULL,
  description VARCHAR(1000) DEFAULT '',
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  member_id INT NOT NULL
);

ALTER TABLE tb_file
	ADD CONSTRAINT fk_tb_member_tb_file_id
		FOREIGN KEY (member_id) REFERENCES tb_member(id);

-- 회원 및 파일과는 무관한 정보
DROP TABLE IF EXISTS tb_product;

CREATE TABLE tb_product (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL UNIQUE KEY,
	category VARCHAR(30) NOT NULL,
	description VARCHAR(1000) DEFAULT '',
	amount INT DEFAULT 0,
	price INT DEFAULT 0
);
```