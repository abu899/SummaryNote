# 2장. 동작 파라미터화 코드 전달하기

동작 파라미터화는 자주 바뀌는 요구사항을 효과적으로 대응할 수 있는 방법이다.
동작 파라미터화란` 아직 어떻게 실행할 것인지 결정되지 않은` 코드 블록이다. 결과적으로 코드 블록에
따라 메서드의 동작이 파라미터화 된다.

- 리스트의 모든 요소에 대해서` 어떤 동작`을 수행할 수 있음
- 리스트 관련 작업을 끝낸 다음 `어떤 다른 동작`을 수행할 수 있음
- 에러가 발생하면 `정해진 어떤 다른 동작`을 수행할 수 있음

## 2.1 변화하는 요구 사항에 대응하기

### 2.1.1 첫 번째 시도: 녹색사과 필터링

```
public static List<Apple> filterGreenApples(List<Apple> inventory) {
 List<Apple> result = new ArrayList<>();
 for(Apple apple: inventory) {
    if( GREEN.equals(apple.getColor() ) {
        result.add(apple);
    }
 }
 return result;
}
```
사과 색을 정의하는 Enum 을 이용해 필터링을 구현할 수 있다. 하지만, 다양한 색의 변화에는 적절하게 대응할 수 없다.
이런 상황에서는 다음과 같은 좋은 규칙이 있다.

> 거의 비슷한 코드가 반복 존재한다면 그 코드를 추상화 한다

### 2.1.2 두 번째 시도: 색을 파라미터화

``` 
public static List<Apple> filterApplesByColor(List<Apple> inventory,
Color color) {
 List<Apple> result = new ArrayList<>();
 for (Apple apple: inventory) {
    if ( apple.getColor().equals(color) ) {
        result.add(apple);
    }
 }
 return result;
}
```

Color 를 파라미터로 전달하여 다양한 색에도 필터링이 가능해졌다.
하지만 만약 무게에 대한 필터링이 추가된다면 어떻게 될까? 새로운 메서드를 만들고 무게를 파라미터로 전달하면 될까?
그렇게도 가능하지만 그렇게 되면 색을 필터링 하는 코드와 중복되는 문제가 발생한다.

### 2.1.3 세번째 시도: 가능한 모든 속성으로 필터링

```
public static List<Apple> filterApples(List<Apple> inventory, Color color,
 int weight, boolean flag) {
 List<Apple> result = new ArrayList<>();
 for (Apple apple: inventory) {
    if ( (flag && apple.getColor().equals(color)) ||
       (!flag && apple.getWeight() > weight) ){
        result.add(apple);
    }
 }
 return result;
}
```

필터링 해야하는 모든 조건을 파라미터하는 것은 어떨까?
이것은 가장 좋지 않은 모습이다. flag가 뭘 의미하는지도 명확하지 않고 추가되는 요구 사항에 대해서도 유연하게 대응할 수 없다.

## 2.2 동작 파라미터화

참 또는 거짓을 반환하는 함수를 Predicate라고 한다. 이를 이용하기 위해 선택 조건을 결정하는
인터페이스를 정의할 수 있다

```
public interface ApplePredicate{
 boolean test (Apple apple);
}

public class AppleHeavyWeightPredicate implements ApplePredicate {
 public boolean test(Apple apple) {
 return apple.getWeight() > 150;
 }
}
public class AppleGreenColorPredicate implements ApplePredicate {
 public boolean test(Apple apple) {
 return GREEN.equals(apple.getColor());
 }
}
```

인터페이스를 구현하여 여러 필터 조건들을 정의할 수 있게된다.
동작을 파라미터화, 즉 메서드가 `다양한 동작을 받아서` 내부적으로 다양한 동작을 수행할 수 있다.
Predicate를 통해 `컬렉션의 반복 로직과 컬렉션의 요소에 적용할 동작을 분리`할 수 있는 점이 중요하다.

### 2.2.1 네 번째 시도: 추상적 조건으로 필터링

```
public static List<Apple> filterApples(List<Apple> inventory,
 ApplePredicate p) {
 List<Apple> result = new ArrayList<>();
 for(Apple apple: inventory) {
    if(p.test(apple)) {
        result.add(apple);
    }
 }
 return result;
}
```

컬렉션 탐색 로직과 적용할 동작을 분리할 수 있는 것은 동작 파라미터화의 장점이다.
그렇기에 유연한 API를 만들 때 동작 파라미터화가 중요한 역할을 한다

## 2.3 복잡한 과정 간소화

Predicate 인터페이스를 만들고 구현체를 통해 필터링 하는 것은 효과적인 방법이다.
하지만, 새로운 동작을 만들기 위해서는 언제나 새로운 구현체가 필요하게 되며 Predicate를 전달하기 위해 항상 인스턴스화하여 전달해야된다.
이는 로직과 관련 없는 코드가 추가됨을 의미한다.

자바는 클래스의 선언과 인스턴스화를 동시에 수행할 수 있도록 익명 클래스(anonymous class)라는 기법을 제공한다.

### 2.3.1 익명 클래스

익명 클래스는 자바의 지역 클래스(local class)와 비슷한 개념이다.
익명 클래스는 클래스 선언과 인스턴스화를 동시에 하는 것으로, 즉석으로 필요한 구현을 만들어 사용하는 것이다.

### 2.3.2 다섯 번째 시도: 익명 클래스 사용

```
List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
 public boolean test(Apple apple){
    return RED.equals(apple.getColor());
 }
});
```

익명 클래스를 통해 메서드 동작을 파라미터화 할 수 있다. 하지만 익명 클래스는 두 가지 문제가 있다.
첫째로, 익명 클래스는 여전히 많은 공간을 차지하며 둘째로는 개발자가 익명 클래스 사용에 익숙하지 않다는 것이다.

코드가 장황해지는 것은 구현과 유지보수 관점에서 좋지 않다. 따라서, 익명 클래스로 인터페이스를 구현하는
여러 클래스를 선언하는 과정을 줄일 수 있지만 아직 불만족스러울 수 있다.

### 2.3.3 여섯 번째 시도: 람다 표현식 사용

```
List<Apple> result =
 filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```

람다 표현식을 사용하면 코드가 훨씬 간결해진다.

## 결론

- 동작 파라미터화에서는 메서드 내부적으로 다양한 동작을 수행할 수 있도록 `코드를 메서드 파라미터로 전달`한다.
- 동작 파라미터화를 이용하면 변화하는 요구사항에 더 잘 대응할 수 있다
- 코드 전달 기법을 이용하면 동작을 메서드의 파라미터로 전달할 수 있다

