# SQL 튜닝 연습

## 한번에 너무 많은 데이터를 조회하는 SQL 튜닝

- 조회하는 데이터 수를 줄이는 것이 성능에 영향을 미치는 것을 확인

### 데이터 생성

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
```

### 데이터 조회

```mysql
SELECT * FROM users LIMIT 10000;

SELECT * FROM users LIMIT 10;
```

위 두개의 속도는 10배 정도 차이가 나는데, 이는 MySQL이 한번에 너무 많은 데이터를 조회할 때 발생하는 문제이다.
즉, 조회하는 데이터의 갯수가 성능에 영향을 끼친다는 말이다.

따라서, 데이터를 조회할 때는 한 번에 너무 많은 데이터가 아닌 `LIMIT, WHERE 등을 통해 한번에 조회하는 데이터 수를 줄이는 방법을 고려`해야한다.

---

## WHERE 문이 사용된 SQL문 튜닝 - 1

- WHERE 문에 날짜 데이터를 사용하는 경우, 인덱스를 활용해 성능을 개선할 수 있음을 확인
- WHERE 문에 부등호, IN, BETWEEN, LIKE 같은 곳에 사용되는 컬럼은 인덱스를 사용하는 경우 성능이 향상될 가능성이 높다

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users; 

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, department, created_at)
WITH RECURSIVE cte (n) AS
                   (
                       SELECT 1
                       UNION ALL
                       SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT
    CONCAT('User', LPAD(n, 7, '0')) AS name,  -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
    CASE
        WHEN n % 10 = 1 THEN 'Engineering'
        WHEN n % 10 = 2 THEN 'Marketing'
        WHEN n % 10 = 3 THEN 'Sales'
        WHEN n % 10 = 4 THEN 'Finance'
        WHEN n % 10 = 5 THEN 'HR'
        WHEN n % 10 = 6 THEN 'Operations'
        WHEN n % 10 = 7 THEN 'IT'
        WHEN n % 10 = 8 THEN 'Customer Service'
        WHEN n % 10 = 9 THEN 'Research and Development'
        ELSE 'Product Management'
        END AS department,  -- 의미 있는 단어 조합으로 부서 이름 생성
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 성능 측정 및 실행 계획 확인

```mysql
SELECT * FROM users
WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

EXPLAIN SELECT * FROM users
        WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY); 
```

- 해당 실행계획을 확인해보면 type이 ALL로, 풀 테이블 스캔이 실행되고 rows도 대부분의 row가 나온다.

### 성능 개선 및 성능 확인

```mysql
CREATE INDEX idx_created_at ON users (created_at);

SHOW INDEX FROM users;

SELECT * FROM users
WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

type이 range로 변경되며, 인덱스 레인지 스캔으로 변경되어 성능이 개선되는 것을 확인할 수 있었다.

---

## WHERE 문이 사용된 SQL문 튜닝 - 2

- WHERE문에 1개 이상의 컬럼이 사용되는 경우, 어떤 컬럼에 인덱스를 추가할지에 대해 고민
- 모든 컬럼에 인덱스를 추가하는 게 아닌 실행계획을 확인하여 인덱스를 추가, 삭제하는 것을 확인

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users; 

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, department, created_at)
WITH RECURSIVE cte (n) AS
                   (
                       SELECT 1
                       UNION ALL
                       SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT
    CONCAT('User', LPAD(n, 7, '0')) AS name,  -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
    CASE
        WHEN n % 10 = 1 THEN 'Engineering'
        WHEN n % 10 = 2 THEN 'Marketing'
        WHEN n % 10 = 3 THEN 'Sales'
        WHEN n % 10 = 4 THEN 'Finance'
        WHEN n % 10 = 5 THEN 'HR'
        WHEN n % 10 = 6 THEN 'Operations'
        WHEN n % 10 = 7 THEN 'IT'
        WHEN n % 10 = 8 THEN 'Customer Service'
        WHEN n % 10 = 9 THEN 'Research and Development'
        ELSE 'Product Management'
        END AS department,  -- 의미 있는 단어 조합으로 부서 이름 생성
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 성능 측정 및 실행 계획 확인

```mysql
SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)

# 실행 계획
EXPLAIN SELECT * FROM users
        WHERE department = 'Sales'
          AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
                WHERE department = 'Sales'
                  AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

- 해당 실행계획을 확인해보면 type이 ALL로, 풀 테이블 스캔이 실행되고 rows도 대부분의 row가 나온다
- EXPLAIN ANALYZE를 살펴보면 필터링 시간이 풀 테이블 스캔에 걸린 시간과 필터링에 걸린 시간을 확인할 수 있다

### 성능 개선 및 성능 확인

위와 같이 WHERE 문에 1개 이상이 나오는 경우 `인덱스를 어디에 추가할지에 대해 고민`이 될 수 있다.
이 경우, 어떤 컬럼에 인덱스를 추가하는게 효율적인가를 판단할 수 있어야한다.
따라서 처음에는 여러가지를 시도해보자.

1. CREATED_AT 기준으로 INDEX 생성
```mysql
CREATE INDEX idx_created_at ON users (created_at);

# 성능 측정
SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획
EXPLAIN SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

- 시간상으로 효율적으로 성능이 개선되었고, type 또한 range로 변경된 것을 확인
- EXPALIN ANALYZE를 통해 스캔이 range로 변경되고 인덱스 테이블을 스캔해서 성능이 개선된 것을 확인할 수 있다

2. DEPARTMENT 기준으로 INDEX 생성
```mysql
ALTER TABLE users DROP INDEX idx_created_at; # 기존 created_at 인덱스 삭제
CREATE INDEX idx_department ON users (department);

# 성능 측정
SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획
EXPLAIN SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

- 시간상으로 index를 걸기전보다는 빨라졌지만, created_at 인덱스를 걸었을 때보다는 성능이 떨어진 것을 확인할 수 있다
- type을 ref로 변경되었고, 비고유 인덱스 조회를 통해 성능이 개선된 것을 확인할 수 있다

3. DEPARTMENT, CREATED_AT 모두를 기준으로 INDEX 생성

```mysql
# CREATE INDEX idx_department ON users (department); # 위에서 이미 추가함
CREATE INDEX idx_created_at ON users (created_at); # created_at 인덱스 추가

# 성능 측정
SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획
EXPLAIN SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

- CREATED_AT 인덱스를 추가한 것 만큼의 성능 개선을 확인할 수 있다
- 실제 실행 계획을 보면 type이 range로 되어있고 possible_keys에 두 인덱스가 모두 나오지만, key에는 created_at 인덱스가 나오는 것을 확인할 수 있다
- 이는 인덱스를 두개 생성했지만, MySQL이 더 효율적인 인덱스를 선택했음을 확인할 수 있다
- 이 경우 두 인덱스를 모두 생성하는 것보다 created_at 컬럼에만 인덱스를 생성하는게 쓰기 성능에서 더 효율적일 수 있다

데이터 액세스 횟수가 적은, 즉, rows 수 를 줄일 수 있는 컬럼이 중복 정도가 낮은 컬럼이다.
해당 컬럼을 골라서 인덱스를 생성하자.

### 멀티 컬럼 인덱스 활용해보기

1. (created_at, department) 멀티 컬럼 인덱스 생성
```mysql
ALTER TABLE users DROP INDEX idx_created_at;
ALTER TABLE users DROP INDEX idx_department;
CREATE INDEX idx_created_at_department ON users (created_at, department);

SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획
EXPLAIN SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

2. (department, created_at) 멀티 컬럼 인덱스 생성
```mysql
ALTER TABLE users DROP INDEX idx_created_at_department;
CREATE INDEX idx_department_created_at ON users (department, created_at);

SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);

# 실행 계획
EXPLAIN SELECT * FROM users
WHERE department = 'Sales'
AND created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY);
```

- 두 인덱스를 생성했을 때보다 멀티 컬럼 인덱스를 생성했을 때 더 효율적인 것을 확인할 수 있다
- 하지만 created_at 인덱스만 걸었을 때와 크게 성능 차이가 없는 것을 확인할 수 있다
- 이런 경우 멀티 컬럼 인덱스보다는 단일 컬럼 인덱스를 적용시키는게 낫다