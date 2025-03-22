# Ch 9. Sinks

## 9.1 Sinks란?

리액티브 스트림즈에서 발생하는 시그널(signal)을 프로그래밍적으로 푸시(push)할 수 있는 기능을 가지고 있는 Publisher의 한 종류로 볼 수 있다.

Reactor에서 프로그래밍 방식으로 시그널을 전송하는 가장 일반적인 방법은 generate나 create 연산자 등을 사용하는 것인데,
이는 싱글스레드 기반에서 시그널을 전송한다. 반면, Sinks는 멀티스레드 방식으로 시그널을 전송해도 `스레드 안전성을 보장`하기 때문에
동시성 환경에서 데이터를 안전하게 처리할 수 있게 해준다.

## 9.2 Sinks 종류 및 특징

### 9.2.1 Sinks.One

Sinks.one() 메서드를 사용해서 `한 건의 데이터를 전송하는 방법을 정의해 둔 기능 명세`이다.

- 단일 데이터 전송
  - Sinks.One으로 아무리 많은 수의 데이터를 emit한다 하더라도 처음 emit한 데이터는 정상적으로 emit되지만 나머지 데이터들은 Drop
- Mono 변환
  - asMono() 메서드를 통해 Sinks.One에서 Mono 객체로 변환 가능
  - Reactor API 문서에서는 이를 'Mono의 의미 체계를 가진다(with Mono semantics)'라고 표현
- 에러 처리
  - 두번 째 파라미터를 통해 emit 도중 에러가 발생할 경우 어떻게 처리할 것인지를 지정
  - FAIL_FAST는 에러가 발생했을 때 재시도를 하지 않고 즉시 실패 처리를 한다는 의미

```java
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

public class SinksOneExample {
    private static final Logger log = LoggerFactory.getLogger(SinksOneExample.class);

    public static void main(String[] args) throws InterruptedException {
        // Sinks.One 생성
        Sinks.One<String> sinkOne = Sinks.one();

        // 첫 번째 데이터 emit - 정상적으로 처리됨
        log.info("첫 번째 데이터 emit: Hello Reactor");
        sinkOne.emitValue("Hello Reactor", FAIL_FAST);
        
        // 두 번째 데이터 emit - 무시됨 (Drop)
        log.info("두 번째 데이터 emit: Second Value (무시될 예정)");
        sinkOne.emitValue("Second Value", FAIL_FAST);
        
        // null 데이터 emit 시도 - 무시됨 (Drop)
        log.info("null 데이터 emit 시도 (무시될 예정)");
        sinkOne.emitValue(null, FAIL_FAST);

        // 첫 번째 구독자
        log.info("첫 번째 구독자 등록");
        mono.subscribe(data -> log.info("# Subscriber1: {}", data));
        
        // 두 번째 구독자 - 동일한 데이터를 받음
        log.info("두 번째 구독자 등록");
        mono.subscribe(data -> log.info("# Subscriber2: {}", data));
        
        // 완료 신호 보내기
        log.info("완료 신호 전송");
        sinkOne.emitComplete(FAIL_FAST);
        
        // 에러 발생 시나리오 시연
        log.info("\n에러 처리 시나리오");
        Sinks.One<String> errorSink = Sinks.one();
        Mono<String> errorMono = errorSink.asMono();
        
        // 에러 신호 전송
        log.info("에러 신호 전송");
        errorSink.emitError(new RuntimeException("의도적인 에러 발생"), FAIL_FAST);
        
        // 에러 처리 구독
        errorMono.subscribe(
            data -> log.info("# 데이터: {}", data),
            error -> log.error("# 에러 발생: {}", error.getMessage())
        );
        
        // 스레드가 종료되지 않도록 잠시 대기
        Thread.sleep(100);
    }
}
```

### 9.2.2 Sinks.Many

Sinks.many() 메서드를 사용해서 `여러 건의 데이터를 여러 가지 방식으로 전송하는 기능을 정의해 둔 기능 명세`이다.
Sinks.Many의 경우 데이터 emit을 위한 여러 가지 기능이 정의된 `ManySpec을 리턴`한다.

#### 1. UnicastSpec

UnicastSpec의 기능은 단 하나의 Subscriber에게만 데이터를 emit하는 것으로, 두 번째 Subscriber가 구독을 시도하면 에러가 발생한다. 
이는 `One-to-One 방식의 통신`을 구현할 때 유용하다.

```java
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many의 UnicastSpec 예제
 * - unicast()를 통해 단 하나의 Subscriber만 데이터를 전달 받을 수 있다
 */
public class SinksManyUnicastExample {
    private static final Logger log = LoggerFactory.getLogger(SinksManyUnicastExample.class);

    public static void main(String[] args) throws InterruptedException {
        // Unicast Sink 생성 - 단 하나의 Subscriber만 허용
        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        
        // Flux로 변환
        Flux<Integer> fluxView = unicastSink.asFlux();

        // 데이터 emit
        log.info("데이터 1, 2 emit");
        unicastSink.emitNext(1, FAIL_FAST);
        unicastSink.emitNext(2, FAIL_FAST);

        // 첫 번째 구독자 등록 - 1, 2를 받음
        log.info("첫 번째 구독자 등록");
        fluxView.subscribe(data -> log.info("# Subscriber1: {}", data));

        // 추가 데이터 emit
        log.info("데이터 3 emit");
        unicastSink.emitNext(3, FAIL_FAST);

        try {
            // 두 번째 구독자 등록 시도 - 에러 발생
            log.info("두 번째 구독자 등록 시도 (에러 발생 예정)");
            fluxView.subscribe(data -> log.info("# Subscriber2: {}", data));
        } catch (Exception e) {
            log.error("예상된 에러 발생: {}", e.getMessage());
        }

        // 완료 신호 전송
        log.info("완료 신호 전송");
        unicastSink.emitComplete(FAIL_FAST);
        
        // 스레드가 종료되지 않도록 잠시 대기
        Thread.sleep(100);
    }
}
```

#### 2. MulticastSpec

MulticastSpec의 기능은 `하나 이상의 Subscriber`에게 데이터를 emit하며, 이는 `One-to-Many 방식의 통신`을 구현할 때 유용하다.
Sinks가 Publisher 역할을 하는 경우 `Hot publisher로 동작`하기에, 구독 시점에 따라 Subscriber가 받는 데이터가 달라질 수 있다.

```java
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many의 MulticastSpec 예제
 * - multicast()를 사용해서 하나 이상의 Subscriber에게 데이터를 emit하는 예제
 */
public class SinksManyMulticastExample {
    private static final Logger log = LoggerFactory.getLogger(SinksManyMulticastExample.class);

    public static void main(String[] args) throws InterruptedException {
        // Multicast Sink 생성 - 여러 Subscriber 허용
        Sinks.Many<Integer> multicastSink = Sinks.many().multicast().onBackpressureBuffer();
        
        // Flux로 변환
        Flux<Integer> fluxView = multicastSink.asFlux();

        // 데이터 emit
        log.info("데이터 1, 2 emit");
        multicastSink.emitNext(1, FAIL_FAST);
        multicastSink.emitNext(2, FAIL_FAST);

        // 첫 번째 구독자 등록 - 구독 시점 이후의 데이터만 받음
        log.info("첫 번째 구독자 등록");
        fluxView.subscribe(data -> log.info("# Subscriber1: {}", data));
        
        // 두 번째 구독자 등록 - 구독 시점 이후의 데이터만 받음
        log.info("두 번째 구독자 등록");
        fluxView.subscribe(data -> log.info("# Subscriber2: {}", data));

        // 추가 데이터 emit - 두 구독자 모두 받음
        log.info("데이터 3 emit - 두 구독자 모두 받음");
        multicastSink.emitNext(3, FAIL_FAST);
        
        // 세 번째 구독자 등록 - 구독 시점 이후의 데이터만 받음
        log.info("세 번째 구독자 등록");
        fluxView.subscribe(data -> log.info("# Subscriber3: {}", data));
        
        // 추가 데이터 emit - 세 구독자 모두 받음
        log.info("데이터 4 emit - 세 구독자 모두 받음");
        multicastSink.emitNext(4, FAIL_FAST);

        // 완료 신호 전송
        log.info("완료 신호 전송");
        multicastSink.emitComplete(FAIL_FAST);
        
        // 스레드가 종료되지 않도록 잠시 대기
        Thread.sleep(100);
    }
}
```

#### 3. MulticastReplaySpec

MulticastReplaySpec에는 emit된 데이터를 다시 Replay해서 `구독 전에 이미 emit된 데이터라도 Subscriber가 전달받을 수 있게` 하는 메서드들이 정의되어 있다.

- all()
  - 구독 전에 이미 emit된 데이터가 있더라도 처음 emit된 데이터부터 모든 데이터들이 Subscriber에게 전달됩니다.
- limit()
  - emit된 데이터 중 파라미터로 입력한 개수만큼 나중에 emit된 데이터부터 Subscriber에게 전달됩니다.


```java
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many의 MulticastReplaySpec 예제
 * - replay()를 사용하여 이미 emit된 데이터를 새로운 구독자에게도 전달하는 예제
 */
public class SinksManyReplayExample {
    private static final Logger log = LoggerFactory.getLogger(SinksManyReplayExample.class);

    public static void main(String[] args) throws InterruptedException {
        // 1. all() 메서드를 사용한 예제 - 모든 이전 데이터를 새 구독자에게 전달
        log.info("===== all() 메서드 예제 - 모든 이전 데이터 재생 =====");
        Sinks.Many<Integer> replayAllSink = Sinks.many().replay().all();
        Flux<Integer> replayAllFlux = replayAllSink.asFlux();

        // 데이터 emit
        log.info("데이터 1, 2, 3 emit");
        replayAllSink.emitNext(1, FAIL_FAST);
        replayAllSink.emitNext(2, FAIL_FAST);
        replayAllSink.emitNext(3, FAIL_FAST);

        // 첫 번째 구독자 등록 - 이전에 emit된 모든 데이터(1,2,3)를 받음
        log.info("첫 번째 구독자 등록 - 이전 데이터 모두 받음");
        replayAllFlux.subscribe(data -> log.info("# ReplayAll Subscriber1: {}", data));

        // 추가 데이터 emit
        log.info("데이터 4 emit");
        replayAllSink.emitNext(4, FAIL_FAST);

        // 두 번째 구독자 등록 - 이전에 emit된 모든 데이터(1,2,3,4)를 받음
        log.info("두 번째 구독자 등록 - 이전 데이터 모두 받음");
        replayAllFlux.subscribe(data -> log.info("# ReplayAll Subscriber2: {}", data));

        // 완료 신호 전송
        log.info("완료 신호 전송");
        replayAllSink.emitComplete(FAIL_FAST);
        
        Thread.sleep(100);

        // 2. limit() 메서드를 사용한 예제 - 지정된 개수의 최신 데이터만 새 구독자에게 전달
        log.info("\n===== limit() 메서드 예제 - 최신 데이터 2개만 재생 =====");
        Sinks.Many<Integer> replayLimitSink = Sinks.many().replay().limit(2); // 최신 2개만 유지
        Flux<Integer> replayLimitFlux = replayLimitSink.asFlux();

        // 데이터 emit
        log.info("데이터 10, 20, 30 emit");
        replayLimitSink.emitNext(10, FAIL_FAST);
        replayLimitSink.emitNext(20, FAIL_FAST);
        replayLimitSink.emitNext(30, FAIL_FAST);

        // 첫 번째 구독자 등록 - 최신 2개 데이터(20,30)만 받음
        log.info("첫 번째 구독자 등록 - 최신 2개 데이터만 받음");
        replayLimitFlux.subscribe(data -> log.info("# ReplayLimit Subscriber1: {}", data));

        // 추가 데이터 emit
        log.info("데이터 40 emit");
        replayLimitSink.emitNext(40, FAIL_FAST);

        // 두 번째 구독자 등록 - 최신 2개 데이터(30,40)만 받음
        log.info("두 번째 구독자 등록 - 최신 2개 데이터만 받음");
        replayLimitFlux.subscribe(data -> log.info("# ReplayLimit Subscriber2: {}", data));

        // 완료 신호 전송
        log.info("완료 신호 전송");
        replayLimitSink.emitComplete(FAIL_FAST);
        
        // 스레드가 종료되지 않도록 잠시 대기
        Thread.sleep(100);
    }
}
```