# Index

## 정의

- Index는 DB 테이블에 대한 검색 성능의 속도를 높여주는 자료구조
- 데이터를 빨리 찾기 위해 `특정 컬럼을 기준으로 미리 정렬해 놓은 표`

## 인덱스 실습

```mysql
DROP TABLE IF EXISTS users;

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
    FLOOR(1 + RAND() * 1000) AS age    -- 1부터 1000 사이의 랜덤 값으로 나이 생성
FROM cte;

-- 잘 생성됐는 지 확인
SELECT COUNT(*) FROM users;

# 인덱스 생성
# CREATE INDEX 인덱스명 ON 테이블명 (컬럼명);
CREATE INDEX idx_age ON users(age);

# SHOW INDEX FROM 테이블명;
SHOW INDEX FROM users;

SELECT * FROM users
WHERE age = 23;
```

---

## 기본으로 설정되는 인덱스(PK)

Primary Key(PK)는 기본적으로 PK를 기준으로 정렬을 해서 데이터를 보관한다.

- PK가 인덱스의 일종이기 때문
- 원본 데이터 자체가 정렬되는 인덱스를 `클러스터링 인덱스`라고 부른다
- 클러스터링 인덱스는 PK 밖에 없음

```mysql
SHOW INDEX FROM users;
```

인덱스를 조회해보면 `Key_name`이 `PRIMARY`로 생성된 것을 확인할 수 있다.
즉, `PK는 인덱스가 기본적으로 적용되며 PK를 기준으로 데이터가 정렬`된다.

## 제약 조건을 추가하면 자동으로 생성되는 인덱스(UNIQUE)

UNIQUE 제약 조건을 추가하면 자동으로 인덱스가 생성된다. MySQL에서 UNIQUE 제약조건을 구성할 때, 기본적으로
index 원리를 사용해서 데이터를 보관하기 때문이다.

```mysql
DROP TABLE IF EXISTS users; # 기존 테이블 삭제

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

SHOW INDEX FROM users;
```

인덱스를 확인해보면 `Key_name`이 `name`으로 생성된 것을 확인할 수 있다.
즉, UNIQUE 옵션을 사용하면 인덱스가 같이 생성되기 때문에 조회 성능이 향상된다.

## 인덱스를 많이 걸면 좋을까?

인덱스를 추가하는 건 인덱스용 테이블을 추가적으로 생성하는 것이다. 따라서 인덱스를 많이 걸면 성능이 좋아질 것 같지만,
인덱스가 많아지면 많아질 수록 모든 인덱스용 테이블에 쓰기와 같은 수정, 삭제등이 이뤄져야하므로 성능저하가 발생한다.

따라서 `최소한의 인덱스`만 사용해야한다.

## 멀티 컬럼 인덱스(Multi Column Index)

2개 이상의 컬럼을 묶어서 설정하는 인덱스를 `멀티 컬럼 인덱스`라고 한다.

```mysql
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    이름 VARCHAR(100),
    부서 VARCHAR(100),
    나이 INT
);

INSERT INTO users (이름, 부서, 나이)
VALUES ('박미나', '회계', 26),
       ('김미현', '회계', 23),
       ('김민재', '회계', 21),
       ('이재현', '운영', 24),
       ('조민규', '운영', 23),
       ('하재원', '인사', 22),
       ('최지우', '인사', 22);

CREATE INDEX idx_부서_이름 ON users (부서, 이름);

SHOW INDEX FROM users;
```

### 멀티 컬럼 인덱스 생성 시 주의점

1. 멀티 컬럼 인덱스를 만들어두면 일반 인덱스처럼 활용할 수 있따

위 실습에서 `부서`와 `이름`을 묶어서 인덱스를 생성했기에, `부서`를 기준으로 정렬되어 있고 이후 이름을 기준으로 정렬되어 있다.
따라서, `부서`만 놓고보면 일반 인덱스처럼 활용할 수 있고 추가적으로 `부서 컬럼 인덱스를 따로 만들 필요가 없다`.

2. 멀티 컬럼 인덱스를 일반 인덱스처럼 활용하지 못하는 경우

1번에서 본 것처럼 `부서`컬럼은 일반 인덱스로 활용할 수 있지만, `이름` 컬럼은 `부서`의 데이터와 묶여서 정렬되어 있기에
일반 인덱스처럼 사용할 수 없다. 즉, 멀티 컬럼 인덱스에서 `일반 인덱스처럼 사용할 수 있는건 처음에 배치된 컬럼`뿐이다.

3. 멀티 컬럼 인덱스를 구성할 때 `대분류 -> 중분류 -> 소분류`순으로 구성하기

멀티 컬럼 인덱스를 만들 때 `순서를 매우 주의`해야한다. 어떤 순서로 인덱스를 구성하느냐에 따라서 인덱스의 성능이 달라질 수 있기 때문이다.
멀티 컬럼 인덱스는 배치한 컬럼의 순서대로 데이터를 탐색하기 때문이다.

멀티 컬럼 인덱스를 구성 시, `데이터 중복도가 높은 컬럼이 앞쪽`으로 설정해야한다.
하지만, 항상 그런건 아니므로 실행 계획과 SQL 실행 속도를 통해 최적의 인덱스를 찾아야한다.

## 커버링 인덱스(Covering index)

SQL문을 실행시킬 때 필요한 모든 컬럼을 갖고 있는 인덱스.

즉, SQL을 실행할 때 실제 테이블에 접근하지 않고 인덱스에만 접근해서 알아 낼 수 있는 경우가 존재한다.
이런 인덱스를 보고 `커버링 인덱스`라고 표현한다.