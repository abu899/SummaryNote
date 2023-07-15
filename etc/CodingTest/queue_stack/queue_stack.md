# Queue(Deque)

- First In First Out(FIFO)
  - 시간 순서상 먼저 저장한 데이터가 먼저 출력되는 `선입선출` 구조
  - front 와 rear 구조
- Array List 를 이용한 방법
  - Circular queue 와 Dynamic array 를 이용해 만들 수 있음
  - 하지만, Array list 를 사용하게 되면 queue 를 사용하는 장점이 사라짐
    - 시간 복잡도 문제
- Linked list based

# Stack

- Last In First Out(LIFO)
  - 시간 순서상 마지막 데이터가 가장 먼저 나오는 `후입선출` 구조
  - top 이 존재하는 구조
- Array list based
  - push, pop 의 시간복잡도가 O(1) 이기에 사용해도 무방
- Linked list based

## 코테 적용 방법

- Stack 의 다양한 활용
  1. LIFO 특성을 활용한 문제
    - https://leetcode.com/problems/valid-parentheses/
    - https://leetcode.com/problems/daily-temperatures/
  2. DFS(깊이 우선 탐색)에 사용