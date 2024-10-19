# 동시성 컬렉션

## 컬렉션 프레임워크 대부분은 Thread-safe 하지 않다

- 우리가 일반적으로 사용하는 ArrayList, LinkedList, HashMap 등의 컬렉션은 단순한 연산을 제공해 보이지만 내부적으로 수많은 연산을 실행
- 즉, Thread-safe 하지 않다
- 단일 스레드로 실행하는 경우라면 문제가 없지만, 멀티스레딩 환경이라면 일반적인 컬렉션을 사용하면 안됨

## 프록시 도입

- synchronized 키워드를 사용하여 동기화를 구현할 수 있지만, 모든 컬렉션을 상속받아 구현하는 것은 비효율적
    - 구현이 변경되면 인터페이스와 구현 모두 변경되어야 함
- 기존 BasicList를 사용하며 멀티스레드가 필요한 상황에서 프록시를 도입하여 synchronized 기능을 사용할 수
  있음 [소스코드](../src/main/java/org/example/thread/collection/list/SyncProxyList.java)

> 프록시 패턴의 주요 목적
> - 접근 제어
> - 성능 향상
>   - 실제 객체의 생성을 지연하거나 캐싱을 통해 성능 향
> - 부가기능 제공
>   - 실제 객체에 추가적인 기능(로깅, 인증, 동기화등)을 제공

## 동시성 컬렉션 - synchronized

- 처음부터 모든 자료구조에 synchronized 키워드를 사용하여 동기화를 구현하면 어떨까?
- synchronized, Lock, CAS 모두 정도의 차이일 뿐 성능과의 트레이드 오프가 존재
- 미리 동기화를 해둔다면 싱글 스레드에서 동작하는 경우 성능이 저하될 수 있다

따라서 꼭 필요한 경우에만 동기화를 적용하는 것이 좋기에 synchronized를 제공하는 프록시를 제공할 수 있다
[소스코드](../src/main/java/org/example/thread/collection/java/)

### Synchronized 프록시 방식의 단점

- 동기화 오버헤드
  - 메서드 호출 시마다 동기화 비용 추가(락 획득, 락 반납)
- 전체 컬렉션에 대한 잠금이 이뤄지므로 잠금 범위가 넓음
- 정교한 동기화가 불가능(과도한 동기화)

## 동시성 컬렉션

- java.util.concurrent 패키지에서 고성능 멀티스레드 환경을 지원하는 컬렉션을 지원
- 정교한 잠금 메커니즘을 적용
- 종류
  - List
    - CopyOnWriteArrayList
      - ArrayList의 대안
  - Set
    - CopyOnWriteArraySet
      - HashSet의 대안
    - ConcurrentSkipListSet
      - TreeSet의 대안(Comparator 사용 가능)
  - Map
    - ConcurrentHashMap
      - HashMap의 대안
    - ConcurrentSkipListMap
      - TreeMap의 대안(Comparator 사용 가능)
  - Queue
    - ConcurrentLinkedQueue
      - 동시성, Non-blocking Queue
  - Deque
    - ConcurrentLinkedDeque
      - 동시성 데크, Non-blocking Deque
  - LinkedHashSet, LinkedHashMap과 같은 입력 순서를 유지하는 컬렉션은 synchronizedSet, synchronizedMap을 사용하여 구현해야 함
  - BlockingQueue
    - ArrayBlockingQueue
      - 고정 크기
      - 공정 모드 사용 가능하지만 성능 저하 가능성 존재
    - LinkedBlockingQueue
      - 무한 크기
    - PriorityBlockingQueue
      - 우선순위 큐
    - SynchronousQueue
      - 데이터를 저장하지 않는 블로킹 큐
      - 생산자-소비자 간의 핸드오프(hand0-off) 메커니즘 제공
        - 즉, 버퍼 없이 직접 데이터 전달
    - DelayQueue
      - 일정 시간이 지연된 후에 소비 가능한 큐
      - 스케쥴링 작업에서 사용
