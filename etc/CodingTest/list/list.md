# List

- List vs Set
  - Set 은 순서가 내부의 값 들의 순서가 중요하지 않음
  - List 는 순서가 중요한 자료구조
- 리스트는 크게 `Array list`와 `Linked list` 로 나눌 수 있음

---
## Static Array

size를 정하여 해당 size 만큼 연속된 메모리를 할당 받아 저장하는 자료구조

- 고정된 저장공간(fixed-size)
- 순차적인 데이터 저장(order)

### Random Access(Direct Access)

메모리에 저장된 데이터에 접근하려면 주소값을 알아야 한다. 배열 변수는 자신이 할당받은 메모리의 첫 번째 주소를 가리킨다.
따라서, 배열은 순차적이기에 `첫 주소값을 알고 있다면 어떤 index 에도 접근이 가능`하다.

이를 Direct Access 또는 Random Access 라고 부르며, 배열에서 `데이터를 찾는 시간 복잡도는 O(1)`이 된다.
반대로 `Linked list 의 경우 시간 복잡도는 O(n)`

### 한계

- 선언시에 정한 size 보다 많은 데이터를 저장하는 경우 메모리 공간이 부족할 수 있음
- 할당 시 큰 배열을 선언한다면 메모리 비효율 발생

## Dynamic Array

정적배열과 달리 선언 이후에도 size 를 늘릴 수 있는 배열. `Resizing 에는 O(n)`의 시간복잡도가 필요

- 기존에 할당된 size 를 초과하면 size 를 늘린 배열을 새로 선언 후, 새로운 배열에 모든 데이터를 옮김
  - 기존 배열은 메모리 해제
- 일반적으로 2배 큰 크기로 resize
  - doubling
- 코딩테스트에서는 주로 이 동적 배열을 사용하며, 파이썬에서는 `list`를 지원
  - 따라서 연산들의 시간복잡도를 익혀야 함

---

## Linked List

Node 로 이루어져있으며, Node 를 이용해 어떻게 구현하냐에 따라 graph, tree, linked list 가 될 수 있다.
Linked List 의 경우 Node 라는 구조체가 연결되어있고, `데이터 값과 다음 노드의 주소값을 저장`하는 구조이다.
물리적으로는 비연속적으로 구성되어있지만, 각 노드가 다음 노드를 가리키기 때문에 논리적으로는 연속성을 가진다.

- 메모리 사용이 list 보다 자유로움
- 하지만, 다음 노드의 주소를 추가 저장해야 하기에 데이터 하나당 차지하는 메모리는 커지게 됨

---

## 코딩테스트 적용 방법

- 배열의 다양한 활용(https://leetcode.com/problems/two-sum/)
  1. 반복문
  2. Sort & Two Pointer
- Linked List 의 다양한 활용(https://leetcode.com/problems/design-browser-history/)
  1. Linked List 자유자재로 구현
     - 선형 자료구조 + 중간에 데이터 추가,삭제 용이
  2. Tree or Graph 에 활용 