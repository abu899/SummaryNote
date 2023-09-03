# Dynamic Programming

문제에 대한 정답이 될 가능성이 있는 모든 해결책을 탐색하는 풀이법.

- 완전 탐색과 달리 체계적이고 효율적인 방법으로 탐색
- 어떻게?
  - 크고 복잡한 문제를 작은 문제를 작은 문제로 나눈다
  - 하위 문제의 답을 계산
    - 중복 계산해야 하는 하위 문제 -> `overlapping sub-problem`(중복 하위문제)
    - 계산 결과를 메모리에 저장하여 재계산하지 않도록
  - 하위 문제에 대한 답을 통해 원래 문제에 대한 답을 구한다
    - 최적 구분 구조(optimal sub-structure)
    - 하위 부분 문제에서 구한 최적의 답이 합쳐져 큰 문제의 최적 답을 구할 수 있는 구조
- 피보나치 수열
  - 완전 탐색(재귀) -> O(2^n)
  - DP -> O(n)

## DP 문제

- https://leetcode.com/problems/climbing-stairs/
- https://leetcode.com/problems/min-cost-climbing-stairs/
- https://leetcode.com/problems/unique-paths/

## 정리

- DP는 크고 복잡한 문제를 작게 나누고, 중복되는 문제를 저장하여 재사용함으로써 문제를 효율적으로 해결
- DP 의 핵심은 아래 두가지
  - Overlapping sub-problem
    - 작은 문제로 분해
    - sub-problem의 계산값을 재사용
    - `주로 optimum value(최소, 최대), 방법의 갯수 등을 구할 때`
  - Optimal sub-structure
    - 작은 문제를 해결하면 전체 문제에 대한 결과와 일치해야 함
    - 항상 일치한다고 보장할 순 없음
    - `미래의 계산이 앞선 계산 결과에 영향을 받을 때`
- Top-down vs Bottom-up
  - Top-down
    - 재귀 사용 -> 구현시간이 빠름
    - 단점
      - 상대적으로 실행속도가 느림
      - 너무 많은 재귀호출은 메모리 공간 부족으로 StackOverflow
  - Bottom-up 
    - 반복문 사용 -> 실행시간이 빠르다
- 접근 방법
  - 완전 탐색 알고리즘으로 접근 시작
  - 시간복잡도가 높다면 DP 적용이 가능한지 확인
  - Top-down을 먼저 적용해보고 메모리 초과가 난다면 bottom-up 방식으로 전환