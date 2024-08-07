# 실전 SQL 문 튜닝

## 유저이름으로 특정 기간에 작성된 글 검색하는 SQL 튜닝

### 데이터 생성

```mysql
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

SET SESSION cte_max_recursion_depth = 1000000;

-- users 테이블에 더미 데이터 삽입
INSERT INTO users (name, created_at)
WITH RECURSIVE cte (n) AS
                   (SELECT 1
                    UNION ALL
                    SELECT n + 1
                    FROM cte
                    WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT CONCAT('User', LPAD(n, 7, '0'))                                                                       AS name,      -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;

-- posts 테이블에 더미 데이터 삽입
INSERT INTO posts (title, created_at, user_id)
WITH RECURSIVE cte (n) AS
                   (SELECT 1
                    UNION ALL
                    SELECT n + 1
                    FROM cte
                    WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT CONCAT('Post', LPAD(n, 7, '0'))                                                                       AS name,       -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at, -- 최근 10년 내의 임의의 날짜와 시간 생성
       FLOOR(1 + RAND() * 50000)                                                                             AS user_id     -- 1부터 50000 사이의 난수로 급여 생성
FROM cte;
```

### 기존 SQL 성능 및 실행 계획 조회

```mysql
SELECT p.id, p.title, p.created_at
FROM posts p
         JOIN users u ON p.user_id = u.id
WHERE u.name = 'User0000046'
  AND p.created_at BETWEEN '2022-01-01' AND '2024-03-07';
```

- 실행 계획을 살펴보면 user 테이블의 type은 ALL 로 풀 테이블 스캔이 진행된다
- 그리고 posts 테이블의 type은 ref 이고, user_id 컬럼을 사용하여 조인이 진행된다

### 인덱스 생성 및 확인

```mysql
CREATE INDEX idx_name ON users (name);
CREATE INDEX idx_created_at ON posts (created_at);

SELECT p.id, p.title, p.created_at
FROM posts p
         JOIN users u ON p.user_id = u.id
WHERE u.name = 'User0000046'
  AND p.created_at BETWEEN '2022-01-01' AND '2024-03-07';
```

- 어떤 인덱스를 생성하는게 유효한지 확인하기 위해 인덱스 후보군 모두 생성
- 실행계획을 보면 옵티마이저는 idx_name만을 사용하여 조회를 진행한다
    - 즉, idx_created_at 인덱스는 사용되지 않으므로 인덱스를 삭제한다

---

## 특정 부서에서 최대 연봉을 가진 사용자 조회 SQL

### 데이터 생성

```mysql
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100),
    department VARCHAR(100),
    salary     INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, department, salary, created_at)
WITH RECURSIVE cte (n) AS
                   (SELECT 1
                    UNION ALL
                    SELECT n + 1
                    FROM cte
                    WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT CONCAT('User', LPAD(n, 7, '0'))                                                                       AS name,       -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
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
           END                                                                                               AS department, -- 의미 있는 단어 조합으로 부서 이름 생성
       FLOOR(1 + RAND() * 100000)                                                                            AS salary,     -- 1부터 100000 사이의 난수로 나이 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at  -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 기존 SQL 성능 및 실행 계획 조회

```mysql
SELECT *
FROM users
WHERE salary = (SELECT MAX(salary) FROM users)
  AND department IN ('Sales', 'Marketing', 'IT');
```

- 실행 계획을 살펴보면 users 테이블의 type은 ALL 로 풀 테이블 스캔이 진행된다
- 서브쿼리로 실행된 type 또한 ALL로 풀 테이블 스캔이 진행된다

### 인덱스 생성 및 확인

```mysql
CREATE INDEX idx_salary ON users (salary);

SELECT *
FROM users
WHERE salary = (SELECT MAX(salary) FROM users)
  AND department IN ('Sales', 'Marketing', 'IT');
```

- 인덱스로 사용할 수 있는건 salary와 department가 존재한다
- 하지만 인덱스를 활용하는 이유가 데이터 접근 횟수를 줄이는 것이므로 중복도가 낮은 컬럼을 고르는게 좋으므로 department는 적합하지 않을 수 있따
- 따라서 salary만으로 인덱스를 생성한다

---

## 부서별 최대 영본을 가진 사용자들 조회하는 SQL

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100),
    department VARCHAR(100),
    salary     INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SET SESSION cte_max_recursion_depth = 1000000;

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, department, salary, created_at)
WITH RECURSIVE cte (n) AS
                   (SELECT 1
                    UNION ALL
                    SELECT n + 1
                    FROM cte
                    WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT CONCAT('User', LPAD(n, 7, '0'))                                                                       AS name,       -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
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
           END                                                                                               AS department, -- 의미 있는 단어 조합으로 부서 이름 생성
       FLOOR(1 + RAND() * 100000)                                                                            AS salary,     -- 1부터 100000 사이의 난수로 나이 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at  -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 기존 SQL 성능 및 실행 계획 조회

```mysql
SELECT u.id, u.name, u.department, u.salary, u.created_at
FROM users u
         JOIN (SELECT department, MAX(salary) AS max_salary
               FROM users
               GROUP BY department) d ON u.department = d.department AND u.salary = d.max_salary;
```

### 인덱스 생성 및 확인

```mysql
CREATE INDEX idx_department_salary ON users (department, salary);

SELECT u.*
FROM users u
         JOIN (SELECT department, MAX(salary) AS max_salary
               FROM users
               GROUP BY department) d ON u.department = d.department AND u.salary = d.max_salary;
```

- JOIN 문 내 서브쿼리를 보면 department로 group by를 하고 있으므로 department가 정렬되어 있으면 더 빠르게 조회할 수 있다
- 또한 부서 내 salary 또한 정렬되어 있다면 최댓값을 구하는데 더 효과적일 수 있다
- 따라서, 멀티 컬럼 인덱스 생성을 통해 해당 쿼리를 개선해볼 수 있다
    - 부서 내 에서 최대 salary 이기 때문에

---

## 2023년 주문 데이터를 조회하는 SQL

### 데이터 생성

```mysql
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;

CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    ordered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id    INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

SET SESSION cte_max_recursion_depth = 1000000;

-- users 테이블에 더미 데이터 삽입
INSERT INTO users (name, created_at)
WITH RECURSIVE cte (n) AS
                   (SELECT 1
                    UNION ALL
                    SELECT n + 1
                    FROM cte
                    WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT CONCAT('User', LPAD(n, 7, '0'))                  AS name,      -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;

-- orders 테이블에 더미 데이터 삽입
INSERT INTO orders (ordered_at, user_id)
WITH RECURSIVE cte (n) AS
                   (SELECT 1
                    UNION ALL
                    SELECT n + 1
                    FROM cte
                    WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                   )
SELECT TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND) AS ordered_at, -- 최근 10년 내의 임의의 날짜와 시간 생성
       FLOOR(1 + RAND() * 1000000)                      AS user_id
FROM cte;
```

### 기존 SQL 성능 및 실행 계획 조회

```mysql
SELECT *
FROM orders
WHERE YEAR(ordered_at) = 2023
ORDER BY ordered_at
LIMIT 30;
```

### 인덱스 생성 및 확인

```mysql
CREATE INDEX idx_ordered_at ON orders (ordered_at);

SELECT *
        FROM orders
        WHERE YEAR(ordered_at) = 2023
        ORDER BY ordered_at
        LIMIT 30;
```

- 기존과 같이 풀 테이블 스캔하던걸 ordered_at에 인덱스를 생성하여 조회를 진행했다
- 하지만 결과는 기존보다 더 느려진걸 확인할 수 있다
- 왜 그럴까?
  - type은 index로, 인덱스 풀 스캔이 진행한다
  - 하지만, ordered_at 컬럼에 대한 조건이 YEAR() 함수로 감싸져 있기 때문에 인덱스를 사용하지 못한다
  - 즉, 인덱스 컬럼을 가공했기 때문에 문제가 생긴 것이다

```mysql
SELECT *
FROM orders
WHERE ordered_at >= '2023-01-01 00:00:00' 
  AND ordered_at < '2024-01-01 00:00:00'
ORDER BY ordered_at
LIMIT 30;
```

- 위와 같이 인덱스 컬럼에 가공을 가하지 않는 쿼리로 변경하면 type이 range로 변경된 것을 확인할 수 있다

---

### 2024년 1학기 평균 성적이 100점인 학생 조회 SQL

### 데이터 생성

```mysql
DROP TABLE IF EXISTS scores;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS students;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT
);

CREATE TABLE subjects (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE scores (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    subject_id INT,
    year INT,
    semester INT,
    score INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

SET SESSION cte_max_recursion_depth = 1000000;

-- students 테이블에 더미 데이터 삽입
INSERT INTO students (name, age)
WITH RECURSIVE cte (n) AS
                 (
                   SELECT 1
                   UNION ALL
                   SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                 )
SELECT
  CONCAT('Student', LPAD(n, 7, '0')) AS name,  -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
  FLOOR(1 + RAND() * 100) AS age -- 1부터 100 사이의 랜덤한 점수 생성
FROM cte;

-- subjects 테이블에 과목 데이터 삽입
INSERT INTO subjects (name)
VALUES
  ('Mathematics'),
  ('English'),
  ('History'),
  ('Biology'),
  ('Chemistry'),
  ('Physics'),
  ('Computer Science'),
  ('Art'),
  ('Music'),
  ('Physical Education'),
  ('Geography'),
  ('Economics'),
  ('Psychology'),
  ('Philosophy'),
  ('Languages'),
  ('Engineering');

-- scores 테이블에 더미 데이터 삽입
INSERT INTO scores (student_id, subject_id, year, semester, score)
WITH RECURSIVE cte (n) AS
                 (
                   SELECT 1
                   UNION ALL
                   SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                 )
SELECT
  FLOOR(1 + RAND() * 1000000) AS student_id,  -- 1부터 1000000 사이의 난수로 학생 ID 생성
  FLOOR(1 + RAND() * 16) AS subject_id,             -- 1부터 16 사이의 난수로 과목 ID 생성
  YEAR(NOW()) - FLOOR(RAND() * 5) AS year,   -- 최근 5년 내의 임의의 연도 생성
  FLOOR(1 + RAND() * 2) AS semester,                -- 1 또는 2 중에서 랜덤하게 학기 생성
  FLOOR(1 + RAND() * 100) AS score -- 1부터 100 사이의 랜덤한 점수 생성
FROM cte;
```

### 기존 SQL 성능 및 실행 계획 조회

```mysql
SELECT 
    st.student_id,
    st.name,
    AVG(sc.score) AS average_score
FROM 
    students st
JOIN 
    scores sc ON st.student_id = sc.student_id
GROUP BY 
    st.student_id,
    st.name,
    sc.year,
    sc.semester
HAVING 
    AVG(sc.score) = 100
    AND sc.year = 2024
    AND sc.semester = 1;
```

### 성능 개선하기

```mysql
SELECT 
    st.student_id,
    st.name,
    AVG(sc.score) AS average_score
FROM 
    students st
JOIN 
    scores sc ON st.student_id = sc.student_id
WHERE 
    sc.year = 2024
    AND sc.semester = 1
GROUP BY 
    st.student_id,
    st.name
HAVING 
    AVG(sc.score) = 100;
```

- HAVING이 나오면 일단 WHERE로 변경할 수 있는지 확인해야 한다
  - sc.score는 group by 이후에 처리할 수 있기에 불가능
  - 하지만 sc.year, sc.semester는 WHERE로 변경할 수 있다

---

## 좋아요 많은 순으로 게시글 조회하는 SQL

### 데이터 생성

```mysql
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts;


CREATE TABLE posts
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  title      VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE likes
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  post_id    INT,
  user_id    INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (post_id) REFERENCES posts (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000;

-- posts 테이블에 더미 데이터 삽입
INSERT INTO posts (title, created_at)
WITH RECURSIVE cte (n) AS
                 (SELECT 1
                  UNION ALL
                  SELECT n + 1
                  FROM cte
                  WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                 )
SELECT CONCAT('Post', LPAD(n, 7, '0'))                                                                       AS name,      -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;

-- users 테이블에 더미 데이터 삽입
INSERT INTO users (name, created_at)
WITH RECURSIVE cte (n) AS
                 (SELECT 1
                  UNION ALL
                  SELECT n + 1
                  FROM cte
                  WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                 )
SELECT CONCAT('User', LPAD(n, 7, '0'))                                                                       AS name,      -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;

-- likes 테이블에 더미 데이터 삽입
INSERT INTO likes (post_id, user_id, created_at)
WITH RECURSIVE cte (n) AS
                 (SELECT 1
                  UNION ALL
                  SELECT n + 1
                  FROM cte
                  WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
                 )
SELECT FLOOR(1 + RAND() * 1000000)                                                                           AS post_id,   -- 1부터 1000000 사이의 난수로 급여 생성
       FLOOR(1 + RAND() * 1000000)                                                                           AS user_id,   -- 1부터 1000000 사이의 난수로 급여 생성
       TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650) DAY) +
                 INTERVAL FLOOR(RAND() * 86400) SECOND)                                                      AS created_at -- 최근 10년 내의 임의의 날짜와 시간 생성
FROM cte;
```

### 기존 SQL 성능 및 실행 계획 조회

```mysql
SELECT p.id,
       p.title,
       p.created_at,
       COUNT(l.id) AS like_count
FROM posts p
         INNER JOIN
     likes l ON p.id = l.post_id
GROUP BY p.id, p.title, p.created_at
ORDER BY like_count DESC
LIMIT 30;
```

- 실행 계획을 살펴보면 posts 테이블의 type은 index 로 인덱스 풀 스캔이 진행된 것을 확인할 수 있다
- 보다 자세한 사항을 확인하기 위해 EXPLAIN ANALYZE를 사용하여 실행 계획을 확인해보자
- 내부적으로 보면 loop inner join과 aggregate using temporary table에서 많은 시간이 소요되고 있다
  - loop inner join의 경우 두 테이블간 조인을 위해 전체 데이터를 스캔하는 데 시간이 소모된다
  - aggregate의 경우 임시 테이블을 생성하는데, 조인한 전체 데이터에 대해 group by를 함으로써 성능의 저하가 발생했다

### 성능 개선하기

```mysql
SELECT p.*, l.like_count
FROM posts p
INNER JOIN
	(SELECT post_id, count(post_id) AS like_count FROM likes l
	GROUP BY l.post_id
	ORDER BY like_count DESC
	LIMIT 30) l
ON p.id = l.post_id;
```

- 기존 쿼리는 100만건에 대한 join과 해당 테이블에 대한 group by에서 성능상의 문제가 발생했다
- 하지만, 우리가 원하는건 좋아요 갯수가 많은 순으로 30개를 뽑을 것이기 때문에, 서브쿼리를 통해 좋아요순으로 정렬된 30개의 데이터의 post_id와
like_count를 join하는 것으로 변경하였다