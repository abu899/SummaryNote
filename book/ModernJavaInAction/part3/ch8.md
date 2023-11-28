# 컬렉션 API 개선

## 8.1 컬렉션 팩토리

자바에서는 적은 요소를 포함하는 리스트를 어떻게 만들까?

기본적으로 new ArraList<>()를 사용하거나 Arrays.asLit() 팩토리 메서드를 이용해 
생성할 수 있다.

```text
List<String> friends = new ArrayList<>();

List<String> friends = Arrays.asList("Raphael", "Olivia");
```

하지만 asList()를 이용하면 고정 크기의 리스트를 만들었기에, 갱신은 가능하지만 요소를 추가하거나 삭제할 수 없다.
요소의 삭제나 추가하는 경우 `UnsupportedOperationException`이 발생한다.
내부적으로 고정된 크기의 배열을 사용하기 때문에 요소를 추가하거나 삭제할 수 없다.

집합의 경우 리스트를 파라미터로 받는 HashSet 생성자를 이용해 만들거나 스트림 API를 사용해 만들 수 있다.

```text
Set<String> friends = new HashSet<>(Arrays.asList("Raphael", "Olivia"));

Set<String> friends = Stream.of("Raphael", "Olivia").collect(Collectors.toSet());
```

두 방법 모두 내부적으로 불필요한 객체 할당을 필요로 하게된다.

### 8.1.1 리스트 팩토리

List.of 팩토리 메서드를 이용해 리스트를 만들 수 있다.

```text
List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
```

하지만 List.of() 또한 요소를 추가하거나 삭제하는 경우 UnsupportedOperationException이 발생한다.
이는 컬렉션이 의도치 않게 변하는 것을 막을 수 있도록 만들어졌다. 또한 null 요소는 금지하므로
의도치 않는 버그를 방지하고 간결한 내부 구현을 가능하게 한다.

데이터 처리 형식을 설정하거나 데이터를 변환할 필요가 없다면 간편한 팩토리 메서드를 이용할 것을 권장한다.
만약 리스트를 바꿔야하는 상황이라면 직접 리스트를 만들어 사용하자.

#### 오버로딩 vs 가변인수

List.of()는 오버로딩된 메서드를 사용한다. 이는 가변인수를 사용하는 메서드보다 더 우선순위가 높다.

왜그럴까?

내부적으로 가변 인수 버전은 추가 배열을 할당해서 리스트로 감싸게된다. 이는 배열을 할당하고 초기화하며
나중에 가비지 컬렉션을 하는 비용을 지불해야한다.
따라서 List.of로 열 개 이상의 파라미터를 가진 경우에는 가변인수를 사용하된다.

### 8.1.2 집합 팩토리

List.of와 비슷한 방법으로 Set을 만들 수 있다.
만약 중복된 요소가 파리미터로 들어온다면 IllegalArgumentException이 발생한다.

```text
Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
```

### 8.1.3 맵 팩토리

자바 9에서는 두 가지 방법으로 바꿀 수 없는 맵을 초기화할 수 있다. Map.of 팩토리 메서드는 키와 값을 번갈아 입력하는 방법으로 맵을 만들 수 있다.
열개 이하의 키와 값 쌍을 가진 작은 맵의 경우 이 메서드가 유용하다.

```text
Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
```

하지만 그 이상의 맵에서는 Map.Entry<K, V> 객체를 파라미터로 받는 가변인수로 구현된
Map.ofEntires 팩토리 메서드를 이용하는 것이 좋다. 이 메서드는 List.of와 비슷하게 키와 값을 감쌀 추가 객체 할당이 필요하다.

```text
Map<String, Integer> ageOfFriends = Map.ofEntries(
    entry("Raphael", 30),
    entry("Olivia", 25),
    entry("Thibaut", 26)
);
```

entry는 Map.Entry 객체를 만드는 새로운 팩토리 메서드이다.

---

## 8.2 리스트와 집합 처리

자바 8에 List와 Set 인터페이스에 추가된 메서드를 살펴보자.

- removeIf
- replaceAll
- sort

이들 메서드는 호출한 컬렉션 자체를 바꾸게 된다. `보통 스트림 동작은 새로운 결과`를 만들지
컬렉션 자체를 바꾸지 않는데 왜 이런 메서드가 추가됬을까? 컬렉션을 바꾸는 동작은 에러를 유발하고 복잡함을 더함에도 불구하고 말이다.

하지만, 그렇기 때문에 removeIf와 replaceAll이 추가되었다.

### 8.2.1 removeIf 메서드

```text
for(Transaction transaction : transactions) {
    if(transaction.getType() == Transaction.GROCERY) {
        transactions.remove(transaction);
    }
}
```

위 코드는 컬렉션을 순회하면서 특정 조건에 맞는 요소를 삭제하는 코드이다.
하지만 이 코드는 `ConcurrentModificationException`을 발생시킨다.
for-each는 내부적으로 Iterator 객체를 사용하며 다음과 같은 코드와 동일하다.

```text
for(Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext();) {
    Transaction transaction = iterator.next();
    if(transaction.getType() == Transaction.GROCERY) {
        transactions.remove(transaction);
    }
}
```

두 개의 개별 객체가 컬렉션을 관리한다

- Iterator 객체, next(), hasNext()를 이용해 소스를 질의한다
- Collection 객체 자체, remove를 통해 소스를 수정한다

반복자의 상태와 컬렉션의 상태는 서로 동기화 되어있지 않기에 한쪽에서 이를 수정하는 경우 문제가 발생할 수 있다.
따라서 객체의 Iterator 객체가 가진 remove를 통해 이를 해결할 수 있다.

자바 8에서는 removeIf를 통해 같은 동작을 수행할 수 있다. 이를 통해 코드가 단순해지고 버그 또한 예방 가능하다.

```text
transactions.removeIf(transaction -> transaction.getType() == Transaction.GROCERY);
```

### 8.2.2 replaceAll 메서드

replaceAll 메서드는 리스트의 각 요소를 새로운 요소로 변경가능하다.
물론 스트림 API를 이용하면 동일한 결과를 얻을 수 있지만 이경우 새로운 문제열 컬렉션을 만들게 된다.

기존 컬렉션을 바꾸지 않고 이를 수행하게 되면 이전에 언급했듯 Iterator 객체와 컬렉션 객체에 대한
동시 변경 문제로 문제를 일으키기 쉽다. 그렇기에 replaceAll 메서드는 이를 보완하기 위해 추가되었다.

```text
referenceCodes.replaceAll(code -> code.toLowerCase());
```

---

## 8.3 맵 처리

### 8.3.1 forEach 메서드

자바 8부터는 Map 인터페이스는 BiConsumer를 파라미터로 받는 forEach 메서드를 제공한다.
이를 통해 기존 entrySet()을 이용한 반복을 통해 키와 값을 처리하는 코드를 간결하게 만들 수 있다.

```text
ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));
```

### 8.3.2 정렬 메서드

자바 8에서는 맵의 항목을 값 또는 키를 기준으로 정렬할 수 있다.

- Entry.comparingByValue
- Entry.comparingByKey

```text
favoriteMovies.entrySet().stream()
    .sorted(Map.Entry.comparingByKey())
    .forEachOrdered(System.out::println);
```

#### HashMap 성능

자바 8에서 HashMap 내부 구조를 변경해 성능을 개선했다.

- 기존
  - 버킷이 하나의 LinkedList로 구현되어있었다.
  - 많은 키가 같은 해시코드를 가지는 경우 O(n)의 성능을 보여준다.
- 최근
  - 버킷이 너무 커질 경우 O(log(n)) 성능의 정렬된 트리를 이용해 동적으로 치환
  - 다만 키가 String, Number 같은 Comparable을 구현한 경우에만 가능하다.

### 8.3.3 getOrDefault 메서드

기존 맵에서 키가 존재하지 않는 경우 null이 반환되기에 NPE를 방지하기 위한 검증이 필요했다.
하지만 자바 8의 기본값을 반환하는 방식으로 이 문제를 해결할 수 있다. 물론 키가 존재하지만 값이 null인 경우에는 null을 리턴한다.

```text
favoriteMovies.getOrDefault("Olivia", "Matrix");
```

### 8.3.4 계산 패턴

맵에 키가 존재하는지에 따라 동작을 실행하고 결과를 저장하는 경우가 필요할 수 있다.
대표적으로 캐싱을 위해 연산 후 키를 이용해 값을 저장하는 것이다. 이 경우 세 가지 연산이 도움이 된다.

- computeIfAbsent
  - 키가 존재하지 않는 경우 연산을 실행하고 결과를 저장한다.
- computeIfPresent
  - 키가 존재하는 경우 연산을 실행하고 결과를 저장한다.
- compute

캐싱의 경우 computeIfAbsent를 활용 가능하다.

```text
lines.forEach(line -> {
    dataToHash.computeIfAbsent(line, this::computeExpensiveValue);
}
```

### 8.3.5 삭제 패턴

자바 8에서는 키가 특정한 값과 연관되었을 때 특정 항목을 제거하는 메서드를 제공한다.
기존에는 키가 존재하는지 확인하고 그 값이 특정 값과 일치하는지 확인한 후 삭제하는 방식으로 구현했다.
하지만 새롭게 오버로드 된 메서드는 보다 간결하다

```text
favoriteMovies.remove("Raphael", "Star Wars");
```

### 8.3.6 교체 패턴

맵의 항목을 바꾸는데 사용되는 메서드도 추가되었다.

- replaceAll
  - BiFunction을 적용하여 각 항목의 값을 교체
  - List의 replaceAll과 비슷
- replace
  - 키가 존재하면 맵의 값을 교체
  - 키가 특정 값으로 매핑되었을 때만 교체하는 오버로딩 버젼도 존재

### 8.3.8 병합

기본적으로 두개의 맵을 합친다고 했을 때 putAll()을 통해 합칠 수 있다.
다만 값을 보다 유연하게 합치고 싶다면 merge()를 사용할 수 있으며, 중복된 키에 대한 처리를
BiFunction을 통해 처리할 수 있다.

```text
Map<String, String> everyone = new HashMap<>(family);
friends.forEach((k, v) -> everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
```

또한 merge 메서드는 null 값과 관련하여 지정된 키와 연관된 값이 없거나 null인 경우,
merge는 키를 null이 아닌 값과 연결한다. 아니면, 연결된 값과 주어진 매핑 함수의 결과로 대치하거나 그 결과가 null 이라면 항목을 제거한다.

이를 통해 초기화 검사를 구현할 수 있다.

```text
moviesToCount.merge(movie, 1, (key, count) -> count + 1);
```

두 번째 파라미터가 1L인데, 키가 연관된 값이 없거나 null 인 경우 사용된다.

---

## 8.4 개선된 ConcurrentHashMap

CouncurrentHashMap은 내부 자료구조의 특정 부분을 잠궈 동시 추가, 갱신작업을 허용하는 동시성 친화적인 Map이다.
따라서 일반적인 HashMap의 동기화를 시킨 것 보다 성능이 좋다.

### 8.4.1 리듀스와 검색

- forEach
- reduce
- search
  - 널이 아닌 값을 반환할 때 가지 모든 키-값 쌍을 순회한다.

위 항목처럼 key, value로 연산하는 것과 더불어 key, value, Map.Entry 객체로 연산하는 형태도 지원한다

- 키, 값으로 연산
  - forEach, reduce, search
- 키로 연산
  - forEachKey, reduceKeys, searchKeys
- 값으로 연산
  - forEachValue, reduceValues, searchValues
- Map.Entry로 연산
  - forEachEntry, reduceEntries, searchEntries

이 연산들은 ConcurrentHashMap의 상태를 잠그지 않고 진행되기에, 연산을 제공한 함수는 계산이 진행되는 동안 바뀔 수 있다는 점을 명심해야한다.
따라서, 객체, 값, 순서 등에 의존하지 않아야한다.

또한 이들 연산에 threshold 값을 지정하여 맵의 크기가 주어진 값보다 작으면 순차적으로 연산을 실행한다.
만약 1을 지정하면 병렬성을 극대화한다.

### 8.4.2 계수

맵의 매핑 개수를 반환하는 mappingCount 메서드는 int를 반환한다.

### 8.4.3 집합 뷰

ConcurrentHashMap은 키와 값에 대한 집합 뷰를 keySet이라는 형태로 제공한다.
하지만 맵이 바뀌면 keySet도 바뀌고 keySet이 바뀌면 맵도 영향을 받는다.
이 경우 newKeySet()을 이용하면 독립적인 뷰를 만들 수 있다.

---

## 결론

- List.of, Set.of, Map.of, Map.ofEntries 팩토리 메서드를 통해 쉽게 컬렉션을 만들 수 있다
  - 이들 컬렉션은 객체가 만들어진 이후에 변결할 수 없는 불변 컬렉션이다
- List 인터페이스는 removeIf, replaceAll, sort 디폴트 메서드를 지원한다
- Set 인터페이스는 removeIf 디폴트 메서드를 지원한다
- Map 인터페이스는 자주 사용하는 패턴과 함께 오류를 방지할 수 있는 디폴트 메서드를 지원한다
- ConcurrentHashMap은 리듀스, 검색, 집합 뷰를 지원함과 동시에 스레드 안정성도 제공한다
