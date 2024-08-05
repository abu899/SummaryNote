# SQL 문의 실행 계획

## 실행 계획이란?

`옵티마이저가 SQL문을 어떤 방식으로 어떻게 처리할지 계획한 것`이다.
해당 실행 계획을 보고 비효율적으로 처리하는 방식을 점검하고, 비효율적인 부분을 튜닝하는게 목표.

## 실행 계획 확인 방법

```mysql
EXPLAIN [SQL 문]

# 실행 계획에 대한 자세한 정보 조회
EXPLAIN ANALYZE [SQL 문] 
```

### 실행 계획 튜닝에 중요한 정보

- id
  - 실행 순서
- table
  - 조회한 테이블
- type
  - 테이블의 데이터 조회 방식
- possible_keys
  - 사용 가능한 인덱스
- key
  - 실제 사용한 인덱스
- ref
  - 테이블을 조인하는 상황에서 어떤 값을 기준으로 조인했는지
- rows
  - SQL 수행을 위해 테이블에 접근한 데이터의 갯수
  - 데이터 엑세스 수
  - 많으면 많을수록 비효율적
  - 정확한 값이 아닌 추정치
- filtered
  - 필터링된 데이터의 비율
  - 높을 수록 효율적
  - 정확한 값이 아닌 추정치

## 실행 계획의 type 의미 분석

### ALL

풀 테이블 스캔

- 인덱스를 활용하지 않고 테이블을 처음부터 끝까지 전부 다 찾는 방식
- 비효율적

```mysql
DROP TABLE IF EXISTS users; # 기존 테이블 삭제

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT
);

INSERT INTO users (name, age) VALUES
                                  ('Alice', 30),
                                  ('Bob', 23),
                                  ('Charlie', 35);

EXPLAIN SELECT * FROM users WHERE age = 23; # type : ALL
```

### Index

풀 인덱스 스캔

- 인덱스 테이블을 처음부터 끝까지 확인하여 데이터를 찾는 방식
- 풀 테이블 스캔보다 효율적이지만, 인덱스 테이블 전체를 읽어야하기 떄문에 아주 효율적이라고 보긴 어렵다

```mysql
DROP TABLE IF EXISTS users; # 기존 테이블 삭제

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, age)
WITH RECURSIVE cte (n) AS
                   (
                       SELECT 1
                       UNION ALL
                       SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT
    CONCAT('User', LPAD(n, 7, '0')),   -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
    FLOOR(1 + RAND() * 1000) AS age    -- 1부터 1000 사이의 난수로 나이 생성
FROM cte;

CREATE INDEX idx_name ON users (name);

EXPLAIN SELECT * FROM users
        ORDER BY name
        LIMIT 10;
```

### const

1건의 데이터를 바로 찾을 수 있는 경우

- 고유 인덱스 또는 기본 키를 사용해서 1건의 데이터를 조회한 경우 const
- 아주 효율적인 방식

```mysql
DROP TABLE IF EXISTS users; # 기존 테이블 삭제

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account VARCHAR(100) UNIQUE
);

INSERT INTO users (account) VALUES
                                ('user1@example.com'),
                                ('user2@example.com'),
                                ('user3@example.com');

EXPLAIN SELECT * FROM users WHERE id = 3;
EXPLAIN SELECT * FROM users WHERE account = 'user3@example.com';
```
### range

인덱스 레인지 스캔

- 인덱스를 활용해 범위 형태의 데이터를 조회한 경우
- BETWEEN, 부등호, IN, LIKE를 활용한 데이터 조회
- 인덱스를 활용하기 떄문에 효율적인 방식
  - 하지만, 인덱스를 사용하더라도 `데이터 범위가 넓어질수록 성능저하의 요인`이 될 수 있음

```mysql
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    age INT
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (age)
WITH RECURSIVE cte (n) AS
                   (
                       SELECT 1
                       UNION ALL
                       SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT
    FLOOR(1 + RAND() * 1000) AS age    -- 1부터 1000 사이의 난수로 나이 생성
FROM cte;

CREATE INDEX idx_age ON users(age);

EXPLAIN SELECT * FROM users
        WHERE age BETWEEN 10 and 20;

EXPLAIN SELECT * FROM users
        WHERE age IN (10, 20, 30);

EXPLAIN SELECT * FROM users
        WHERE age < 20;
```

### ref

비고유 인덱스 활용

- UNIQUE 인덱스가 아닌 컬럼의 인덱스를 사용한 경우
- 즉, 중복되는 값이 존재할 수 있지만 정렬되어 있기 때문에 효율적으로 데이터를 찾을 수 있는 경우

```mysql
DROP TABLE IF EXISTS users; # 기존 테이블 삭제

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

INSERT INTO users (name) VALUES
                             ('박재성'),
                             ('김지현'),
                             ('이지훈');

CREATE INDEX idx_name ON users(name);

EXPLAIN SELECT * FROM users WHERE name = '박재성';
```