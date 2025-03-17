# Ch 3: Blocking I/O와 Non-Blocking I/O

I/O는 일반적으로 컴퓨터 시스템이 외부의 입출력 장치들과 데이터를 주고 받는 것을 의미한다.

## 3.1 Blocking I/O

웹 어플리케이션 측면에서 데이터베이스에 데이터를 조회하거나 추가하는 작업 역시 I/O이며, DB I/O라고 한다.
네트워크 통신을 한다면 네트워크 I/O가 발생하고, 웹 어플리케이션은 동적인 데이터를 처리하기 위해 어떤 식으로든 I/O가 발생한다.

Blocking I/O는 하나의 스레드가 I/O에 의해서 차단되어 대기하는 방식이며, 이 방식에서는 I/O 작업이 완료될 때까지
해당 스레드는 다른 작업을 수행할 수 없다.

멀티스레딩 기법을 사용하면 차단된 시간을 효율적으로 활용할 수 있지만, 다음과 같은 문제점이 존재한다.

### 컨텍스트 스위칭으로 인한 스레드 전환 비용
- 프로세스 정보를 PCB(Process Control Block)에 저장하고 다시 로드하는 시간 동안 CPU가 다른 작업을 하지 못하고 대기
- 컨텍스트 스위칭이 많아지면 CPU의 전체 대기 시간이 길어져 성능이 저하

### 과다한 메모리 사용으로 인한 오버헤드
- 일반적인 Java 웹 애플리케이션은 요청당 하나의 스레드를 할당
- 스레드 내부에서 또 다른 스레드를 추가로 할당하면, 시스템이 감당하기 힘들 정도로 메모리 사용량이 증가

### 스레드 풀에서 응답 지연
- 대량의 요청으로 스레드 풀에 유휴 스레드가 없을 경우, 사용 가능한 스레드가 확보되기 전까지 응답 지연이 발생

## 3.2 Non-Blocking I/O

Non-Blocking I/O는 작업 스레드의 종료 여부와 관계없이 요청한 스레드가 차단되지 않는 방식이며, 이 방식에서는 하나의 스레드로 많은 수의 요청을 처리할 수 있다.

### Non-Blocking I/O의 장점
- Blocking I/O 방식보다 더 적은 수의 스레드를 사용하기 때문에, 앞서 언급한 멀티스레딩 관련 문제들을 극복 가능
- 하나의 스레드로 많은 수의 요청을 처리할 수 있어 효율적

### Non-Blocking I/O의 제한점
- CPU를 많이 사용하는 작업이 포함된 경우에는 성능에 악영향
  - CPU가 특정 작업에 집중해야 하는데, Non-Blocking I/O라면 계속 들어오는 요청에 대응해야 해서 특정 작업에 집중하지 못할 수 있음
- 사용자의 요청에서 응답까지 전체 과정에 Blocking I/O 요소가 포함된다면 Non-Blocking I/O의 장점을 활용하기 어려움

## 3.3 Spring Framework에서의 Blocking I/O와 Non-Blocking I/O

### Spring MVC
- Blocking I/O 방식을 사용
- RestTemplate을 사용하여 작업자를 호출
- 호출 스레드가 매 호출마다 차단

**코드 예시:**
```java
@ResponseStatus(HttpStatus.OK)
@GetMapping("/{book-id}")
public ResponseEntity<Book> getBook(@PathVariable("book-id") long bookId) {
    URI getBookUri = UriComponentsBuilder.fromUri(baseUri)
            .path("/{book-id}")
            .build()
            .expand(bookId)
            .encode()
            .toUri();
    
    ResponseEntity<Book> response = restTemplate.getForEntity(getBookUri, Book.class);
    Book book = response.getBody();
    
    return ResponseEntity.ok(book);
}
```

### Spring WebFlux
- Non-Blocking I/O 방식을 사용
- WebClient를 사용하여 작업자를 호출
- 호출 스레드가 차단되지 않음

**코드 예시:**
```java
@ResponseStatus(HttpStatus.OK)
@GetMapping("/{book-id}")
public Mono<Book> getBook(@PathVariable("book-id") long bookId) {
    URI getBookUri = UriComponentsBuilder.fromUri(baseUri)
            .path("/{book-id}")
            .build()
            .expand(bookId)
            .encode()
            .toUri();
    
    return WebClient.create()
            .get()
            .uri(getBookUri)
            .retrieve()
            .bodyToMono(Book.class);
}
```

## 3.4 Non-Blocking I/O 방식의 통신이 적합한 시스템

Spring WebFlux를 도입하기 위해서는 고려할 사항은 다음과 같다.
- 학습 난이도
- 리액티브 프로그래밍 경험이 있는 개발 인력 확보

### 대량의 요청 트래픽이 발생하는 시스템
- 요청 트래픽이 충분히 감당할 수준이라면 서블릿 기반 Blocking I/O 방식 어플리케이션으로 충분
- Spring WebFlux는 상대적으로 적은 컴퓨팅 파워를 사용함으로써 저비용으로 고수준의 성능을 이끌어낼 수 있다

### 마이크로 서비스 기반 시스템
- 서비스들 간에 많은 수의 I/O가 발생하는 시스템에 적합하다
- MSA 특성상 Blocking으로 인한 응답 지연이 발생하면, 전체 서비스에 영향을 미칠 수 있다

### 스트리밍 또는 실시간 시스템
- 리액티브 프로그래밍은 일회성 연결뿐만 아니라, 끊임없이 들어오는 무한한 데이터 스트림을 효율적으로 처리 가능하다
