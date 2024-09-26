# 고급 동기화 - concurrent.Lock

synchronized의 문제점

- 무한 대기
- 공정성(starvation)

## Lock Support

대표적인 기능

- park()
  - 스레드를 WAITING 상태로 변경
- parkNanos()
  - 지정된 나노 시간만큼 스레드를 TIMED_WAITING 상태로 변경
  - [소스코드](../src/main/java/org/example/thread/sync/lock/LockSupportMainV2.java)
- unpark()
  - park()로 WAITING 상태에 있는 스레드를 RUNNABLE 상태로 변경
  - [소스코드](../src/main/java/org/example/thread/sync/lock/LockSupportMainV1.java)

### BLOCKED vs WAITING

- 인터럽트
  - BLOCKED 는 인터럽트가 걸려도 대기 상태를 벗어나지 못함
  - WATING, TIMED_WAITING 상태는 인터럽트가 걸리면 대기 상태를 벗어남

BLOCKED, WAITING, TIMED_WAITING 모두 스레드가 대기하고 실행 스케쥴링에 들어가지 않기 떄문에,
CPU 입장에서 보면 실행하지 않는 비슷한 상태이다.

- BLOCKED는 synchronized에서만 사용하는 특별한 대기 상태
- WAITING, TIMED_WAITING은 다양한 상황에서 사용할 수 있는 대기 상태

## ReentrantLock

java 1.5부터 synchronized를 한계를 극복하기 위해 Lock 인터페이스와 reentrantLock 클래스를 제공한다.
[소스코드](../src/main/java/org/example/thread/sync/BankAccountV4.java)

### Lock 인터페이스

동시성 프로그래밍에서 쓰이는 안전한 임계 영역을 위한 락을 구현하는데 사용된다.

- lock()
  - 락을 획득
  - 다른 스레드가 락을 획득했다면 WAITING 상태가 됨
  - 단, 인터럽트에 응하지 않음
    - WAITING이기에 인터럽트 발생 시 RUNNABLE로 변경되지만, 다시 WAITING 상태로 강제로 변경
- lockInterruptibly()
  - lock과 동일한 기능을 제공
  - 단, 다른 스레드가 인터럽트를 할 수 있음
- tryLock()
  - 락을 획득하려고 시도하고 `즉시 성공 여부를 반환`
  - 성공하면 true, 실패하면 false
- tryLock(long time, TimeUnit unit)
  - 지정된 시간 동안 락을 획득하려고 시도
  - 성공하면 true, 실패하면 false
  - 해당 메서드는 대기 중 인터럽트가 발생하면 예외 발생
- unlock()
  - 락을 해제
  - 락을 획득한 스레드가 아닌 다른 스레드가 호출한 경우 예외 발생(IllegalMonitorStateException)
- Condition newCondition()
  - 락을 기다리는 스레드를 관리하기 위한 조건 객체를 반환
  - 특정 조건을 기다리거나 신호를 받을 수 있도록 함

### 공정성

- 비공정 모드(Non-fair mode)
  - 성능 우선
  - 선점 가능
  - 기아 현상 가능성
- 공정 모드(Fair mode)
  - 공정성 보장
  - 기아 현상 방지
  - 성능 저하