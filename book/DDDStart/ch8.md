# 애그리거트 트랜잭션 관리

설명을 위해 다음 상황을 가정한다

- 운영자는 고객의 배송지 주소를 확인하고 배송 상태로 변경한다
- 고객은 배송지를 변경한다

## 애그리거트와 트랜잭션

위 상황이 동시에 발생하는 경우 일관성이 깨지는 상황이 발생할 수 있고 이를 막기 위해선 두 가지 중 하나를 해야한다.

- 운영자가 배송지 정보를 조회하고 상태를 변경하는 동안, 고객이 애그리거트를 수정하지 못하게 한다
- 운영자가 배송지 정보를 조회한 이후 고객이 정보를 변경하면, 운영자가 애그리거트를 다시 조회한 뒤 수정하도록 한다

이것은 트랜잭션과 관련이있으며 이를 해결하기 위해선 선점(Pessimistic)잠금과 비선점(Optimistic)잠금을 사용할 수 있다.

## 선점 잠금

Pessimistic Lock은 먼저애그리거트를 구한 스레드가 애그리거트 사용이 끝날 때 까지
다른 스레드가 해당 애그리거트를 수정하지 못하게 막는 방식이다.

선점 잠금은 보통 DBMS가 제공하는 행단위 잠금을 사용해서 구현하며, 특정 레코드에 한 커넥션만 접근할 수 있는 잠금 장치를 제공한다.

### 선점 잠금과 교착 상태

선점 잠금 기능을 사용할 때는 잠금 순서에 따른 교착상태가 발생하지 않도록 주의해야한다.

선점 잠금에 따른 교착 상태는 상대적으로 사용자 수가 많을 때 발생할 가능성이 높고,
교착 상태에 빠지는 스레드는 더 빠르게 증가하게 된다.이런 문제가 발생하지 않도록 잠금을 구할 때 최대 대기 시간을 지정해야한다.
JPA의 @QueryHints 를 통해 지정 가능하다.

```text
@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")
```

## 비선점 잠금

선점 잠금으로 모든 트랜잭션 충돌 문제가 해결되는 것은 아니다.
운영자가 이미 주문 정보를 조회 한 후 고객이 수정하는 경우, 운영자는 변경되지 않은 주문 정보로
배송 상태를 변경할 수 있는 문제가 발생한다.

비선점 잠금은 동시에 접근하는 것을 막는 대신, 변경한 데이터를 실제 DBMS에 반영하는 시점에
변경 가능 여부를 확인하는 방식이다. 비선점 잠금의 실행 결과가 수정된 행의 갯수가 0이면
이미 누군가 데이터를 수정한 것이다. 이는 트랜잭션 충돌이며 트랜잭션 종료 시점에 익셉션이 발생한다.

비선점 잠금과 관련해 두가지 익셉션이 발생할 수 있다.
- OptimisticLockingFailureException
  - 스프링 프레임워크가 발생시킴
  - 누군가 동시에 애그리거트를 수정했음
- VersionConflictException
  - 응용 서비스 코드에서 발생시킴
  - 이미 누군가가 애그리거트를 수정했음
위 두가지 익셉션으로 개발자는 트랜잭션 충돌이 발생한 시점을 명확하게 구분 가능하다.

### 강제 버전 증가

애그리거트 루트 외에 다른 엔티티가 수정되는 경우 JPA는 루트 엔티티 버전 값을 증가시키지 않는다.
JPA의 특징은 애그리거트 관점에서 문제가 될 수 있다. 애그리거트의 일부 값이 바뀌면 논리적으로 애그리거트가 바뀐 것이기 때문이다.

이 문제는 강제로 버전 값을 증가시키는 잠금 모드를 통해 해결 가능하다.(OPTIMISTIC_FORCE_INCREMENT)

