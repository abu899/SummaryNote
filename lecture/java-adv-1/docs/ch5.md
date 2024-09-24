# 동기화 - synchronized

멀티스레딩에서 가장 주의할 점은 같은 자원에 여러 스레드가 동시 접근할 때 발생하는 `동시성 문제`이다.
여러 스레드가 접근하는 자원을 `공유 자원`이라고 하는데, 이를 제어하기 위해 `동기화`가 필요하다.

## 임계 영역

동기화 문제가 발생하는 근본 원인은 여러 스레드가 함께 사용하는 공유 자원을 여러 단계로 나누어 사용하기 떄문이다.
[소스코드](../src/main/java/org/example/thread/sync/BankAccountV1.java)

1. 검증 단계
2. 출금 단계

위 로직은 스레드 하나 관점에서 봤을 때 `출금 단계`에서 계산이 끝날 떄 까지 같은 금액으로 유지되어야 한다는 가정이 필요하다.
따라서 중간에 다른 스레드가 잔액의 값을 변경한다면 문제가 발생한다.

### 한 번에 하나의 스레드만 실행

만약 출금 메서드를 한번에 하나의 스레드만 실행할 수 있게 제한한다면 공유 자원인 잔액이 한번에 하나의 스레드만 변경할 수 있다.
따라서 다른 스레드가 잔액의 값을 변경하는 부분을 걱정하지 않아도 된다.

### 임계 영역(Critical Section)

- 여러 스레드가 동시에 접근하면 데이터 불일치나 예상치 못한 동작이 발생할 수 있는 코드 섹션
- 여러 스레드가 동시에 접근해서 안되는 공유자원에 접근하거나 수정하는 부분

위 소스코드에서 withdraw() 메서드가 임계 영역에 해당한다.

---

## synchronized 메서드

자바에서는 `synchronized` 키워드를 사용하면 한 번에 하나의 스레드만 실행할 수 있는 코드 섹션을 만들 수 있다.
[소스코드](../src/main/java/org/example/thread/sync/BankAccountV2.java)

### synchronized 분석

- 모든 객체(인스턴스)는 내부에 `자신만의 락`을 가지고 있다
    - 모니터 락(Monitor Lock)
    - 객체 내부에 있고 우리가 확인은 어려움
- 스레드가 synchronized 메서드에 진입하면 해당 객체의 모니터 락을 획득해야 함
    - 먼저 접근하는 스레드가 해당 객체(BankAccount)의 락을 획득
    - 다른 메서드는 락을 획득하기 전까지 `BLOCKED 상태로 무한정 대기`
        - BLOCKED 상태가 되면 `CPU 실행 스케쥴링에 들어가지 않음`

참고로 volatile을 사용하지 않더라도 synchronized를 사용하면 메인 메모리에 직접 접근할 수 있기 때문에 메모리 `가시성 문제는 해결`된다(모니터 락 규칙)

### synchronized 코드 블럭

syncronized 메서드는 메서드 전체에 락을 걸기 때문에 메서드 전체에 하나의 스레드만 접근할 수 있다. 따라서 여러 스레드가
동시에 실행되지 못하기에 성능상의 문제가 발생할 수 있고, 이를 해결하기 위해 꼭 필요한 부분에 `synchronized 코드 블럭`을 사용할 수 있다.
[소스코드](../src/main/java/org/example/thread/sync/BankAccountV3.java)

### 정리

동기화를 사용하면 다음 문제를 해결할 수 있다.

- 경합 조건(race condition)
  - 두 개 이상의 스레드가 경쟁적으로 동일한 자원을 수정할 때
- 데이터 일관성
  - 여러 스레드가 동시에 읽고 쓰는 데이터 일관성 유지

동기화는 멀티스레딩에서 반드시 필요한 부분이지만, 과도하게 사용되는 경우 성능 저하를 초래할 수 있으니 적절히 사용해야 한다.