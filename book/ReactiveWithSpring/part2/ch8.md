# Ch 8. Backpressure

## 8.1 Backpressure란?

리액티브 프로그래밍에서 Backpressure(배압)는 Publisher가 끊임없이 emit하는 무수히 많은 데이터를 적절하게 제어하여 
데이터 처리에 과부하가 걸리지 않도록 제어하는 역할이다.

리액티브 시스템에서는 Publisher(생산자)와 Subscriber(소비자) 간의 데이터 처리 속도 차이로 인해 다음과 같은 문제가 생길 수 있다.

- 처리되지 않고 대기 중인 데이터가 지속적으로 쌓이게 됨 
- 메모리 오버플로우 발생 가능성 증가 
- 최악의 경우 시스템이 다운되는 문제 발생

Backpressure는 기본적으로 Subscriber가 처리할 수 있는 데이터의 양을 Publisher에게 알려주는 방식으로 동작한다. 
이를 통해 Publisher는 Subscriber가 처리할 수 있는 만큼만 데이터를 전송하게 된다.
리액티브 스트림즈에서는 이 메커니즘을 `Subscription` 인터페이스를 통해 구현한다.

```java
public interface Subscription {
    void request(long var1);
    void cancel();
}
```

## 8.2 Reactor에서의 Backpressure 처리 방식

Reactor에서는 Backpressure를 처리하기 위한 두 가지 주요 방식을 제공한다.

### 8.2.1 데이터 개수 제어

첫 번째 방법은 Subscriber가 처리할 수 있는 수준의 데이터 개수를 Publisher에게 요청하는 방식으로, `BaseSubscriber` 클래스를 확장하여 구현할 수 있다.


```java
@Slf4j
public class Example8_1 {
    public static void main(String[] args) {
        Flux.range(1, 5)
            .doOnRequest(data -> log.info("# doOnRequest: {}", data))
            .subscribe(new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                @SneakyThrows
                @Override
                protected void hookOnNext(Integer value) {
                    Thread.sleep(2000L);
                    log.info("# hookOnNext: {}", value);
                    request(1);
                }
            });
    }
}
```

이 코드를 설명하자면 다음과 같다.
- BaseSubscriber 사용
  - Reactor에서 Subscriber가 데이터 요청 개수를 직접 제어하기 위해 BaseSubscriber를 사용
- hookOnSubscribe()
  - 구독 시점에 `request()` 메서드를 호출해서 최초 데이터 요청 개수를 제어
- hookOnNext() 메서드
  - Publisher가 emit한 데이터를 전달받아 처리한 후에 Publisher에게 또다시 데이터를 요청

실행 결과는 다음과 같습니다:
```
[main] INFO - # doOnRequest: 1
[main] INFO - # hookOnNext: 1
[main] INFO - # doOnRequest: 1
[main] INFO - # hookOnNext: 2
[main] INFO - # doOnRequest: 1
[main] INFO - # hookOnNext: 3
[main] INFO - # doOnRequest: 1
[main] INFO - # hookOnNext: 4
[main] INFO - # doOnRequest: 1
[main] INFO - # hookOnNext: 5
[main] INFO - # doOnRequest: 1
```

### 8.2.2 Backpressure 전략 사용

두 번째 방법은 Reactor에서 제공하는 Backpressure 전략을 사용하는 것이다.
Reactor는 다양한 Backpressure 전략을 제공하며, 각 전략은 버퍼가 가득 찼을 때 다르게 동작한다.

#### IGNORE 전략

- Downstream에서의 backpressure 요청을 무시하는 전
- IllegalStateException이 발생할 수 있음

#### ERROR 전략

- Downstream의 데이터 처리 속도가 느려서 Upstream의 emit 속도를 따라가지 못할 경우 IllegalStateException을 발생시키는 전략
- Publisher는 Error Signal을 Subscriber에게 전송하고 삭제한 데이터는 폐기합니다.
- `onBackpressureIgnore()` 메서드를 통해 적용

#### DROP 전략

- Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, `버퍼 밖`에서 대기하는 먼저 emit된 데이터부터 Drop시키는 전략
- 즉, 버퍼가 가득 찼을 때 새로운 데이터가 들어오면 버퍼 밖에서 대기하던 가장 오래된 데이터부터 버림
- `onBackpressureDrop()` 메서드를 통해 적용
  - 해당 오퍼레이터는 Drop된 데이터를 파라미터로 전달 받을 수 있기에, 데이터가 폐기되기 전에 추가 작업 수행 가능

#### LATEST 전략

- Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, `버퍼 밖`에서 대기하는 가장 최근 emit된 데이터부터 버퍼에 채우는 전략 
- 즉, 버퍼가 가득 찼을 때는 가장 최신의 데이터만 유지하고 나머지는 버림
- `onBackpressureLatest()` 메서드를 통해 적용

#### BUFFER 전략

- Downstream으로 전달할 데이터가 버퍼에 가득 찰 경우, `버퍼 안`에 있는 데이터부터 Drop시키는 전략
- 즉, 버퍼가 가득 찼을 때 새로운 데이터가 들어오면 버퍼 내의 데이터를 버리고 새로운 데이터를 버퍼에 채움
- `onBackpressureBuffer()` 메서드를 통해 적용할 수 있으며, 버퍼 크기와 오버플로우 시 동작을 지정 가능.

## Backpressure의 중요성

Backpressure는 리액티브 시스템에서 시스템 부하에 적절히 대응하는 핵심 메커니즘입니다. 이를 통해 다음과 같은 이점을 얻을 수 있습니다:

1. **시스템 안정성 향상**: 데이터 처리 과부하로 인한 시스템 다운을 방지합니다.
2. **메모리 효율성**: 불필요한 데이터 버퍼링을 줄여 메모리 사용을 최적화합니다.
3. **처리 속도 최적화**: Subscriber의 처리 능력에 맞게 데이터 흐름을 조절하여 전체 시스템의 처리 속도를 최적화합니다.

리액티브 프로그래밍에서 Backpressure는 단순한 기능이 아닌, 시스템의 안정성과 효율성을 보장하는 핵심 요소입니다. Reactor에서 제공하는 다양한 Backpressure 처리 방식을 상황에 맞게 활용하면 더 안정적이고 효율적인 리액티브 시스템을 구축할 수 있습니다.
