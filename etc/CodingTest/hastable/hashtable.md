# HashTable

- 빠른 탐색을 위한 자료 구조로 `Key-Value` 쌍의 데이터를 입력 받음
- 해쉬 함수 h 에 key 값을 입력으로 넣고 나온 해시 값 h(k)의 위치로 지정하여 키-값을 저장
- 해시 테이블은 사용은 간단하지만 `어떻게, 어느 상황에서 사용`하냐가 가장 중요
- 구현 방법
  - Array list based
    - 파이썬의 경우 Dictionary 가 이 구조로 구성되어 있음
    - Open addressing
  - Array list + Linked list based
    - Separate chaining
- 시간 복잡도
  - 저장, 삭제, 검색의 시간복잡도는 O(1)

### Direct Address Table

```
student[0] = "a"
student[1] = "b"
student[2] = "c"
student[3] = "d"
```

- 위 와 같이 직접 주소를 지정한 것이 Direct Address Table
- 단점
  - index 값이 주민등록번호 같은 큰 값일 경우 할당에 대해 메모리 낭비가 생길 수 있음
  - 키값이 string 형태인 경우 사용할 수 없음

### Collision(충돌)

- Open Addressing
  - 충돌이 되지 않을 때 까지 다음 인덱스로 넘기거나 re-hasing 하거나 여러가지 방법으로 다른 주소로 할당

## 코테 적용 방법

- 해시 테이블 활용
  - 메모리를 사용해서 시간복잡도를 줄일 때 사용
  - `key in {}` 가 핵심 -> O(1)
    - array 에서 in 연산자를 사용하여 값을 찾을 때 O(n)
- https://leetcode.com/problems/longest-consecutive-sequence/