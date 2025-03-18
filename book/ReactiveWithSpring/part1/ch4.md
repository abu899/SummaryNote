# Ch 4. 리액티브 프로그래밍을 위한 사전 지식

## 4.1 함수형 인터페이스(Functional Interface)

함수형 인터페이스는 리액티브 프로그래밍의 핵심 요소 중 하나로, 다음과 같은 특징을 가진다.

- 단 하나의 추상 메서드만 정의된 인터페이스
- 함수를 일급시민으로 취급하여 함수 자체를 파라미터로 전달 가능
  - 여기서 일급시민이란 함수를 값으로 취급한다는 의미
- `@FunctionalInterface` 어노테이션으로 명시적 선언 가능
  - 필수는 아니지만 권장
- default 메서드나 static 메서드는 여러 개 있어도 함수형 인터페이스로 인정됨

예시로 `Comparator` 인터페이스를 들 수 있다.

```java
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2); // 단 하나의 추상 메서드
    
    // 디폴트 메서드는 여러 개 가질 수 있음
    default Comparator<T> reversed() {
        return Collections.reverseOrder(this);
    }
    
    default Comparator<T> thenComparing(Comparator<? super T> other) {
        Objects.requireNonNull(other);
        return (Comparator<T> & Serializable) (c1, c2) -> {
            int res = compare(c1, c2);
            return (res != 0) ? res : other.compare(c1, c2);
        };
    }
}
```

## 4.2 람다 표현식(Lambda Expression)

람다 표현식은 함수형 인터페이스를 구현한 클래스의 메서드를 간소화한 표현식이다.

- 함수형 인터페이스(Functional Interface)를 구현하는 익명 클래스의 인스턴스를 생성하는 표현식
- 코드를 간결하게 작성할 수 있어 가독성이 향상됨
- 불필요한 코드를 줄이고 개발자의 의도를 명확히 드러냄

람다 표현식의 기본 구조:
```
(매개변수 리스트) -> { 함수 바디 }
```

**인자 리스트 작성 방법**:
- 인자가 없을 경우: `()`
- 인자가 하나인 경우: `(a)` 또는 `a`
- 인자가 여러 개인 경우: `(a, b, c)`
- 인자의 타입은 컴파일러가 추론하므로 생략 가능 (명시할 수도 있음)

**바디 작성 방법**:
- 여러 줄인 경우: `{}` 중괄호로 묶어서 사용
- 한 줄인 경우: `{}` 생략 가능, return도 생략 가능

**람다 표현식의 특징**:
- 람다식 내에서 사용되는 지역변수는 final이나 effectively final이어야 함
- 람다식으로 선언된 변수명은 다른 변수명과 중복될 수 없음

```java
// 람다 표현식
(Car car) -> car.getCarName();
```

## 4.3 메서드 레퍼런스(Method Reference)

메서드 레퍼런스는 람다 표현식을 더 간결하게 작성하는 방법이다.

- 기존 메서드를 참조하여 사용
- 콜론 두 개(`::`)를 사용하여 표현

메서드 레퍼런스의 유형:
- **스태틱 메서드 참조**: `Type::staticMethod`
- **특정 객체의 인스턴스 메서드 참조**: `객체 레퍼런스::instanceMethod`
- **임의 객체의 인스턴스 메서드 참조**: `Type::instanceMethod`
- **생성자 참조**: `Type::new`

예시:
```java
// 람다 표현식
(Car car) -> car.getCarName();

// 메서드 레퍼런스
Car::getCarName;
```

## 4.4 함수 디스크립터(Function Descriptor)

함수 디스크립터는 함수형 인터페이스의 어떤 파라미터를 가지고 어떤 값을 리턴하는지 설명하는 역할을 한다.

### 자바에서 제공하는 주요 함수형 인터페이스들

| 종류 | 입력 | 반환 | 시그니처와 함수 | 설명 |
|------|------|------|----------------|------|
| Runnable | - | - | void run() | 기본적인 형태의 인터페이스, 인자와 반환값 모두 없음 |
| Supplier<T> | - | <T> | T get() | 인자가 없이 반환값만 있는 인터페이스, 항상 같은 값을 반환 |
| Consumer<T> | <T> | - | void accept(T t) | 인자만 있고 반환값이 없는 인터페이스 |
| Predicate<T> | <T> | Boolean | Boolean test(T t) | 인자와 Boolean 타입의 반환값을 가지는 인터페이스 |
| Function<T, R> | <T> | <R> | R apply(T t) | 제네릭 타입의 인자와 다른 제네릭 타입의 반환값이 같이 있는 인터페이스 |
| UnaryOperator<T> | <T> | <T> | T apply(T t) | 같은 타입의 인자와 반환값을 가지고 있는 인터페이스 |
| BinaryOperator<T> | <T, T> | <T> | T apply(T t1, T t2) | 같은 타입의 인자 2개를 받고, 반환값을 가지고 있는 인터페이스 |
| BiFunction<T, U, R> | <T, U> | <R> | R apply(T t, U u) | 서로 다른 타입의 인자 2개를 받고 다른 타입의 값을 반환하는 인터페이스 |