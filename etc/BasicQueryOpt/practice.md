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

---

## 인덱스를 걸었는데도 인덱스가 작동하지 않는 경우 1

- 넓은 범위의 데이터를 조회하는 경우 인덱스가 동작하지 않는다

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

### 인덱스 생성 및 실행계획 조회

```mysql
CREATE INDEX idx_name ON users (name);

EXPLAIN SELECT * FROM users
        ORDER BY name DESC;
```

- 해당 실행계획을 확인해보면 name에 인덱스를 걸었는데도 불구하고 type이 ALL로, 풀 테이블 스캔이 실행되고 rows도 대부분의 row가 나온다.
- 왜 풀테이블 스캔으로 진행되었을까?
  - 옵티마이저가 넓은 데이터를 조회할 때 인덱스를 활용하는 것이 비효율적이라고 판단하여 풀 테이블 스캔으로 진행
  - 위 쿼리도 *, 즉 모든 데이터를 조회하기 때문에 인덱스를 활용하지 않는 것으로 판단
  - 실제로도 인덱스 테이블을 거쳐서 데이터에 접근해서 가져오는 것보다 풀테이블 스캔이 더 효율적
- 넓은 범위의 데이터를 조회하는 경우, 인덱스를 사용하는 것보다 풀 테이블 스캔이 효과적이라고 판단한다

---

## 인덱스를 걸었는데도 인덱스가 작동하지 않는 경우 2

- 인덱스 컬럼에 가공을 가하는 경우 인덱스가 동작하지 않을 수 있다

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users; 

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    salary INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- users 테이블에 더미 데이터 삽입
INSERT INTO users (name, salary, created_at)
WITH RECURSIVE cte (n) AS
                   (
                       SELECT 1
                       UNION ALL
                       SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT
    CONCAT('User', LPAD(n, 7, '0')) AS name,  -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
    FLOOR(1 + RAND() * 1000000) AS salary,    -- 1부터 1000000 사이의 난수로 급여 생성
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 인덱스 생성 및 실행계획 조회

```mysql
CREATE INDEX idx_name ON users (name);
CREATE INDEX idx_salary ON users (salary);

# User000000으로 시작하는 이름을 가진 유저 조회
EXPLAIN SELECT * FROM users
        WHERE SUBSTRING(name, 1, 10) = 'User000000';

# 2달치 급여(salary)가 1000 이하인 유저 조회
SELECT * FROM users
WHERE salary * 2 < 1000
ORDER BY salary;
```

- 해당 실행계획 또한 인덱스를 걸었는데도 불구하고 type이 ALL로, 풀 테이블 스캔이 실행되고 rows도 대부분의 row가 나온다.
- 왜 풀테이블 스캔으로 진행되었을까?
  - 인덱스 컬럼을 가공(함수, 산술, 문자열 조작 등)하면 인덱스를 활용하지 못할 가능성이 존재
  - 즉, 위 쿼리에서 SUBSTRING, salary * 2 와 같은 가공된 컬럼으로 인해 인덱스가 동작하지 않음 
  - 인덱스를 활용하려면 인덱스를 활용할 수 있는 형태로 쿼리를 작성해야한다

쿼리를 다음과 같이 바꿔보자

```mysql
# User000000으로 시작하는 이름을 가진 유저 조회
EXPLAIN SELECT * FROM users
WHERE name LIKE 'User000000%';

# 2달치 급여(salary)가 1000 이하인 유저 조회
EXPLAIN SELECT * FROM users
WHERE salary < 1000 / 2
ORDER BY salary;
```

- 인덱스 컬럼을 가공하면 인덱스를 활용하지 못할 수 있으니, 인덱스 컬럼 자체는 최대한 가공하지 말자

---

## ORDER BY 문이 사용된 SQL 튜닝

- ORDER BY에서 인덱스 풀 스캔을 활용해서 성능을 개선할 수 있다
- 큰 범위 데이터를 조회하는 경우 인덱스 활용하지 않으므로 성능 효율을 위해 LIMIT을 통해 범위를 제한하자

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users; 

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(100),
    salary INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, department, salary, created_at)
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
    FLOOR(1 + RAND() * 1000000) AS salary,    -- 1부터 1000000 사이의 난수로 나이 생성
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 데이터 조회 및 실행 계획 확인

```mysql
SELECT * FROM users
ORDER BY salary
LIMIT 100;

# 실행 계획
EXPLAIN SELECT * FROM users
        ORDER BY salary
        LIMIT 100;

# 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
                ORDER BY salary
                LIMIT 100;
```

- 당연히 인덱스 추가가 없기에 풀 테이블 스캔으로 동작

### 인덱스 생성 및 실행 계획 확인

```mysql

CREATE INDEX idx_salary ON users (salary);

EXPLAIN SELECT * FROM users
ORDER BY salary
LIMIT 100;
```

- 해당 실행계획을 확인해보면 type이 index로 변경되었고, rows도 100개로 제한되어 있는 것을 확인할 수 있다
- 앞서 인덱스가 동작하지 않는 넓은 범위의 스캔이지만 LIMIT으로 인해 인덱스가 동작한 것을 확인할 수 있다
  - LIMIT이 없이 동작하면 풀 테이블 스캔으로 동작
- 인덱스 풀 스캔으로 동작하는 경우 이미 정렬되어 있기에, 정렬하는 작업이 들어가지 않음
- ORDER BY는 시간이 오래 걸리는 작업이므로 인덱스를 사용하면 미리 정렬해둔 상태기에 최적화를 할 수 있따

---

## WHERE 문에 인덱스 걸기 vs ORDER BY문에 인덱스 걸기

- 어느 곳에 인덱스를 거는 것이 더 효율적인지 확인
- ORDER BY의 특징 상 인덱스 풀 스캔 또는 테이블 풀 스캔을 활용할 수 밖에 없다
- 따라서 ORDER BY 보다는 WHERE 문 컬럼에 인덱스를 걸었을 때 성능이 향상되는 경우가 많다
  - 항상 정해진 정답이 존재하는 것은 아니고 실행 계획을 확인해야한다

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users; 

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(100),
    salary INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, department, salary, created_at)
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
    FLOOR(1 + RAND() * 1000000) AS salary,    -- 1부터 1000000 사이의 난수로 나이 생성
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 데이터 조회 및 실행 계획 조회

```mysql
SELECT * FROM users
WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
AND department = 'Sales'
ORDER BY salary
LIMIT 100;

EXPLAIN SELECT * FROM users
        WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
          AND department = 'Sales'
        ORDER BY salary
        LIMIT 100;
```

### 인덱스 생성 및 성능 측정

- 먼저 created_at과 department 중에 어떤 것이 인덱스에 더 효율적일까?
  - department에 비해 created_at이 더 범위가 좁기에 created_at에 인덱스를 걸었을 때 더 효율적이라는 것을 알 수 있다
- 그렇다면 created_at과 salary 중에는 어떤게 더 효율적일까? 둘 다 생성해서 확인해보자

1. salary에 인덱스를 걸었을 때

```mysql
CREATE INDEX idx_salary ON users (salary);

SELECT * FROM users
WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
  AND department = 'Sales'
ORDER BY salary
LIMIT 100;

-- 실행 계획
EXPLAIN SELECT * FROM users
        WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
          AND department = 'Sales'
        ORDER BY salary
        LIMIT 100;

-- 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
                WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
                  AND department = 'Sales'
                ORDER BY salary
                LIMIT 100;
```

- 오히려 성능이 안좋아 짐을 확인할 수 있다
- 실행 계획을 확인해보면 ALL 에서 index로 변경되었지만 성능이 오히려 떨어짐을 확인할 수 있다
- 왜그럴까?
  - EXPLAIN ANLAYZE로 확인해보면, salary 인덱스로 스캔을 하지만 필터에 해당하는 조건(created_at, salary)을 확인할 수 없기에 
  결국 실제 테이블에 접근해야 가져와야 한다
  - 인덱스 풀 스캔이었지만 풀 테이블 스캔과 거의 비슷하게 동작해버리는 것을 확인할 수 있다

2. created_at에 인덱스를 걸었을 때

```mysql
ALTER TABLE users DROP INDEX idx_salary; -- 기존 인덱스 삭제
CREATE INDEX idx_created_at ON users (created_at);

SELECT * FROM users
WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
  AND department = 'Sales'
ORDER BY salary
LIMIT 100;

-- 실행 계획
EXPLAIN SELECT * FROM users
        WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
          AND department = 'Sales'
        ORDER BY salary
        LIMIT 100;

-- 실행 계획 세부 내용
EXPLAIN ANALYZE SELECT * FROM users
                WHERE created_at >= DATE_SUB(NOW(), INTERVAL 3 DAY)
                  AND department = 'Sales'
                ORDER BY salary
                LIMIT 100;
```

- 성능이 개선되었음을 확인할 수 있다
- 실행 계획을 확인해보면 range 로 인덱스 레인지 스캔을 한 것을 확인할 수 있다
- 어떻게 동작했을까?
  - ANALYZE로 확인해보면, 인덱스 레인지 스캔으로 데이터를 가져오는 것을 확인할 수 있고 가져오는 데이터 수도 확연히 적어진 것을 확인할 수 있다
  - 이후 적은 데이터를 가지고 필터 후 정렬을 하는 것을 확인할 수 있다

--- 

## HAVING문이 사용된 SQL 튜닝

- HAVING 문은 GROUPBY 이후 처리되기에 느릴수 있다
- 따라서 HAVING 문 대시 WHERE 문을 쓸 수 있는지 확인하고 인덱스를 통해 성능을 개선해보자
- 물론 HAVING 문이 필요한 경우에는 HAVING 문을 사용해야한다

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users; 

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    department VARCHAR(100),
    salary INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, age, department, salary, created_at)
WITH RECURSIVE cte (n) AS
                 (
                   SELECT 1
                   UNION ALL
                   SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                 )
SELECT
  CONCAT('User', LPAD(n, 7, '0')) AS name,  -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
  FLOOR(1 + RAND() * 100) AS age, -- 1부터 100 사이의 난수로 생성
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
  FLOOR(1 + RAND() * 1000000) AS salary,    -- 1부터 1000000 사이의 난수로 생성
  TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 인덱스 생성 및 실행 계획 확인

```mysql
CREATE INDEX idx_age ON users (age);

SELECT age, MAX(salary) FROM users
GROUP BY age
HAVING age >= 20 AND age < 30;

EXPLAIN SELECT age, MAX(salary) FROM users
        GROUP BY age
        HAVING age >= 20 AND age < 30;
```

- 해당 실행계획을 확인해보면 type이 index로 변경되었지만, rows는 대부분의 row가 나온다
- EXPLAIN ANALYZE를 확인해보자
  - salary의 최댓값을 가져오기 위해서는 age로 인덱스로는 충분하지 않기에 인덱스 풀 스캔을 했지만 실제 데이터에 접근한다
  - 해당 데이터를 가지고 Group By를 하고, HAVING을 걸어서 필터링을 한다
  - 실행 계획에서 보면 인덱스 풀 스캔을 했을 떄의 시간이 가장 오래 걸린 것을 확인할 수 있다

### 성능 개선하기

```mysql
SELECT age, MAX(salary) FROM users
WHERE age >= 20 AND age < 30
GROUP BY age;

EXPLAINB SELECT age, MAX(salary) FROM users
WHERE age >= 20 AND age < 30
GROUP BY age;
```

- GROUPBY가 처리되고 HAVING이 처리되는 것이 아니라, WHERE에서 필터링을 한 후 GROUP BY를 하는 것으로 변경해보자
- 실행 계획이 range로 변경됬음을 확인할 수 있고 인덱스를 통해 해당 범위의 데이터만 가져오기에 성능이 개선된 것을 확인할 수 있다