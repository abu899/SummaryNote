# 5장. 가독성 높은 코드를 작성하라

## 이번 장에서 다루는 내용

- 코드가 그 자체로 설명이 되도록 하기 위한 방법
- 다른 사람들에게 코드의 세부적 내용을 명확하게 함
- 언어의 기능을 사용할 때 그에 합당한 이유를 가져야 함

가독성은 본질적으로 주관적인 것이며, 핵심은 개발자가 `코드의 기능을 빠르고 정확하게 이해`할 수 있도록 하는 것이다.
코드를 보다 쉽게 읽을 수 있도록 하기 위한 가장 일반적이고 효과적인 기법의 확실한 토대를 학습한다.

## 5.1 서술형 명칭 사용

클래스, 함수, 변수와 같은 것들을 고유하게 식별하기 위한 이름이 필요하다. 이름을 붙이는 것은
읽기 쉬운 코드 작성을 위한 기회이다.

### 5.1.1 서술적이지 않은 이름은 코드를 읽기 어렵게 만든다

### 5.1.2 주석문으로 서술적인 이름을 대체할 수 없다

서술적이지 않은 이름을 대체할 한 가지 방법은 주석문과 문서를 추가하는 것이다.
하지만 이는 다음과 같은 문제를 만든다.

- 코드가 훨씬 더 복잡하게 보인다
  - 코드뿐만 아니라 주석문과 문서도 유지보수 해야한다
- 코드를 이해하기 위해 파일을 계속 위아래로 스크롤해야 한다

파라미터 및 리턴 타입을 주석문으로 설명하는 것은 코드 사용 방법을 이해하는데 도움이 될 수 있다.
하지만 서술적인 이름을 붙이는 대신 주석문을 사용하면 안된다.

### 5.1.3 해결책: 서술적인 이름 짓기

서술적인 이름은 변수, 함수 및 클래스가 별도로 설명할 필요없이 직관적으로 바뀐다.
주석문을 사용한 경우보다 덜 지저분하고 개발자가 주석문까지 관리할 필요없이 코드에 집중할 수 있다.

## 5.2 주석문의 적절한 사용

주석문이나 문서화는 다음과 같은 목적으로 사용될 수 있다.

- 코드가 `무엇`을 하는지 설명
- 코드가 `왜` 그 일을 하는지 설명
- 사용 지침등 기타 정보 제공

클래스와 같이 큰 단위의 코드가 무엇을 하는지 요약하는 `높은 수준에서의 주석문`은 유용하다
그러나 하위 수준에서 한줄 한줄 설명하는 주석문은 가독성을 높이기 위한 효과적인 방법이 아니다.

낮은 층위에서의 주석문은 가독성을 떨어뜨리지만, 코드가 왜 그 일을 하는지에 대한 `이유나 배경을 설명하는 주석문`은 유용할 수 있다.

### 5.2.1 중복된 주석문은 유해할 수 있다

```java
class Temp {
    String generateId(String firstName, String lastName) {
        // 이름.성의 형태로 id를 생성한다
        return firstName + "." + lastName;
    }
}
```

위의 불필요한 주석문은 쓸모없는 것 이상으로 더 나쁠 수 있다

- 개발자는 주석문을 유지보수해야 한다
  - 코드를 변경하면 주석문 역시 수정해야 한다
- 코드를 지저분하게 만든다

### 5.2.2 주석문으로 가독성 높은 코드를 대체할 수 없다

코드 자체가 가독성이 높지 않으면 주석문이 필요할 수 있지만, 더 나은 접근법은 가독성이 좋은 코드를 잓어하는 것이다.
코드 자체로 설명이 되도록 코드를 작성하면 유지 및 관리의 과도한 비용을 줄이고 주석문의 내용이
잘못될 가능성을 없애주기 때문에 주석문을 사용하는 것보다 더 선호될 수 있다.

### 5.2.3 주석문은 코드의 이유를 설명하는 데 유용하다

코드가 그 일을 왜 수행하는지는 배경 상황이나 지식과 고나련이 있을 수 있기 때문에 코드 자체로는 설명하기 어렵다.
다음과 같은 경우에는 주석문을 사용해 코드가 존재하는 이유를 설명하면 좋다.

- 제품 또는 비지니스 의사 결정
- 이상하고 명확하지 않은 버그에 대한 해결책
- 의존하는 코드의 예상을 벗어나는 동작에 대처

### 5.2.4 주석문은 유용한 상위 수준의 요약 정보를 제공할 수 있다

코드 기능에 대한 상위 수준에서의 개략적인 문서는 다음ㅘ 같은 경우 유용하다.

- 클래스가 수행하는 작업 및 다른 개발자가 알고 있어야 할 세부 사항에 대한 개괄적인 설명
- 함수에 대한 입력 파라미터 또는 기능을 설명하는 문서
- 함수의 반환값이 무엇을 나타내는지 설명하는 문서

단점으로는 이런 주석문과 문서도 유지 보수가 필요하고 업데이트가 되지 않으면 코드와 일치하지 않게되고 지저분해질 수 있다.

## 5.3 코드 줄 수를 고정하지 말라

일반적으로 코드 줄 수가 많다는 것은 코드가 지나치게 복잡하거나, 기존 코드를 재사용하지 않고 있다는 신호일 수 있다.

때로는 코드의 줄 수를 최소화하는 것이 코드 품질에 중요하다는 주장이 있다. 그러나
코드 줄 수는 우리가 실제로 신경 쓰는 것들을 `간접적으로 측정`해줄 뿐이다.
유용한 지침 원칙이긴 하지만 반드시 지켜야하는 규칙은 아니라는 것이다. 정말 신경써야 하는 것은 다음과 같은 사항이다.

- 이해하기 쉽다
- 오해하기 어렵다
- 실수로 작동이 안 되게 만들기가 어렵다

### 5.3.1 간결하지만 이해하기 어려운 코드는 피하라

모든 세부 사항과 가정을 매우 간결한 단 한줄의 코드로 압축하게되면 다음과 같은 문제가 있다.

- 다른 개발자는 단 한줄의 코드에서 모든 세부 사항과 가정을 도출하기 위해 노력을 기울여야 한다
- 이로인한 시간이 낭비되고 코드를 오해할 수 있다

코드가 전제하고 있는 명확하지 않고 문서화되지 않은 많은 가정으로 인해 코드 수정에 취약해지고 수정된 코드가
제대로 동작하지 않을 수 있다.

### 5.3.2 해결책: 더 많은 줄이 필요하더라도 가독성 높은 코드를 작성하라

가정과 세부 사항이 코드를 읽는 누구에게나 명백해진다면 그렇게하는 것이 훨씬 더 좋다.
중요한 것은 코드가 이해하기 쉬어야하고, 어떤 상황에서도 잘 동작하며, 문제가 되는 동작을
할 가능성이 없는지 확인하는 것이다.

## 5.4 일관된 코딩 스타일을 고수하라

### 5.4.1 일관적이지 않은 코딩 스타일은 혼동을 일으킬 수 있다
### 5.4.2 해결책: 스타일 가이드를 채택하고 따르라

일관된 코딩 스타일을 따라 코드를 작성하면 가독성이 좋아지고 버그를 예방하는데 도움이 된다.
코딩 스타일은 명명법 이상의 많은 측면에을 다룬다.

- 언어의 특정 기능 사용
- 코드 들여쓰기
- 패키지 및 디렉터리 구조화
- 코드 문서화 방법

통일된 코딩 스타일은 서로 오해할 위험이 줄어들기 때문에 버그가 줄어들고, 혼란스러운 코드를
이해하는데 낭비하는 시간을 줄일 수 있다.

## 5.5 깊이 중첩된 코드를 피하라

코드는 다음과 같이 서로 중첩되는 블록으로 구성된다.

- 함수가 호출되면 그 함수가 실행되는 코드가 하나의 블록이 된다
- if 문의 조건이 참일때 실행되는 코드는 하나의 블록이 된다
- for 루프의 각 반복 시 실행되는 코드는 하나의 블록이 된다

### 5.5.1 깊이 중첩도니 코드는 읽기 어려울 수 있다

인간의 눈은 각 코드 라인의 중첩 수준이 정확히 어느 정도인지 추적하는데 능숙하지 않다.
따라서, 다른 논리가 실행되는 때를 정확히 이해하기 어렵다.
`중첩을 최소화`하도록 코드를 구성하는 것이 바람직하다.

### 5.5.2 해결책: 중첩을 최소화하기 위한 구조 변경

중첩된 모든 블록에 반환문이 있을 떄는 중첩을 피하기 위해 논리를 재배치하는 것이 일반적으로 쉽다.
하지만, 블록에 반환문이 없다면 대개 함수가 너무 많은 일을 하고 있다는 신호이다.

### 5.5.3 중첩은 너무 많은 일을 한 결과물이다

중첩이 복잡한 것은 함수가 너무 많은 일을 한다는 것일 수 있다. 함수를 더 작은 함수로 나누면
문제를 해결할 수 있다.

### 5.5.4 해결책: 더 작은 함수로 분리

앞서 2장에서 살펴본 내용처럼 하나의 함수가 너무 많은 일을 하면 추상화 계층이 나빠진다.
글허기에 중첩이 없더라도 한꺼번에 많은 일을 하는 함수는 더 작은 단위로 나누는 것이 바람직하다.

## 5.6 함수 호출도 가독성이 있어야 한다

함수나 생성자가 많은 파라미터를 가지고 있다면 이것은 보다 근본적인 문제를 나타내는 것일 수 있다.

### 5.6.1 파라미터는 이해하기 어려울 수 있다

함수 호출 시 각 파라미터 값이 무엇을 의미하는지 알려면 함수 정의를 확인해야한다.
주어진 코드가 무엇을 하는지 알아내기 위해 여러 파일이나 라인을 확인해야 한다면, 그 코드는 가독성이
떨어진다.

```text
void sendMessage(String message, int priority, boolean isUrgent);

sendMessage("hello", 1, true);
```

### 5.6.2 해결책: 명명된 매개변수 사용

명명된 매개변수는 최근에 나온 언어에서 지원되는 기능이다.
파라미터의 위치가 아닌 이름으로 일치하는 매개변수를 찾아 실행한다.

### 5.6.3 해결책: 서술적 유형 사용

함수를 정의할 때 좀 더 서술적인 유형을 사용하는 것이 바람직하다.

```text
sendMessage("hello", Priority.HIGH, RetryPolicy.NO_RETRY);
```

### 5.6.4 때로는 훌륭한 해결책이 없다

함수를 호출하는 라인의 가독성을 높여주는 특별한 방법이 없을때도 존재할 수 있다.

여기서 최선의 방법은 생성자를 호출할 때 각 파라미터가 무엇인지 설명하기 위해 `인라인 주석문`을 사용하는 것이다.
이런 주석문을 최신 상태로 유지해야 하므로 만족스러운 해결책은 아니다.
Setter 함수를 추가하거나 빌더 패턴이 대안이 될 수 있지만, 값이 누락된 채 인스턴스가 만들어 질 수 있다는 단점이 있다.

## 5.7 설명되지 않은 값을 사용하지 말라

하드 코드로 작성된 값이 필요한 경우가 존재할 수 있으며 해당 값은 두가지 중요한 정보가 있다.

- 값이 무엇인지
  - 코들르 실행할 때 해당 값을 알아야 한다
- 값이 무엇을 의미하는지
  - 코드를 이해하기 위해선 해당 값이 무엇을 의미하는지 알아야한다

그 값이 실제로 무엇을 의미하는지 다른 개발자들이 명확하게 이해하도록 해야한다.

### 5.7.1 설명되지 않은 값은 혼란스러울 수 있다

코드에 설명되지 않은 하드코딩 값이 있다면 그 값이 무엇을 의미하는지 다른 개발자들에게
명확히 알려주는 것이 중요하다.

### 5.7.2 해결책: 잘 명명된 상수를 사용하라
### 5.7.3 해결책: 잘 명명된 함수를 사용하라

잘 명명된 상수를 사용하는 것의 대안으로 잘 명명된 함수를 사용할 수 있으며 두가지 방법이 있다.

- 상수를 반환하는 공급자 함수(providing function)
- 변환을 수행하는 헬퍼 함수(helper function)

#### 공급자 함수

개념적으로 상수를 사용하는 것과 거의 동일하다.

#### 헬퍼 함수

산술식을 하위 문제로 만들어 기능을 전문적으로 수행하는 함수로 세분화하는 것이다.

## 5.8 익명 함수를 적절하게 사용하라

익명 함수는 이름이 없는 함수로, 일반적으로 코드 내의 필요한 지점에 인라인으로 정의된다.
`간단하고 자명한 것`에 익명 함수를 사용하면 코드의 가독성을 높여주지만, 복잡하거나 자명하지 않은 것
혹은 재사용해야 하는 것에 사용하면 문제가 될 수 있다.

### 5.8.1 익명 함수는 간단한 로직에 좋다

해결하려는 문제가 간단한 경우 익명 함수로 표현하기 괜찮다.
다만, `간단한 논리라도` 명명 함수를 정의하는 것이 `코드 재사용성 관점`에서 유용할 수 있따.

### 5.8.2 익명 함수는 가독성이 떨어질 수 있다

익명 함수는 말그대로 이름이 없기 때문에 코드를 읽는 사람에게 어떠한 것도 제공하지 않는다.
따라서 해당 함수의 내용이 명확하지 않다면 코드의 가독성을 떨어질 수 있다.

```text
List<UInt16> getValidIds(List<UInt16> ids) {
    return ids
        .filter(id -> id != 0)
        .filter(id -> countSetBits(id & 0x7FFF) %2 == ((id & 0x8000) > 15));
}
```

### 5.8.3 해결책: 대신 명명 함수를 사용하라

익명 함수 논리가 명확하지 않다면 구현 세부 사항을 별도의 명명 함수로 빼는 것이 좋다.
또한 명명 함수는 논리를 재사용하기 쉽다는 장점이 있다.

익명 함수는 코드를 줄이는데는 뛰어나지만 이름이 없다는 단점으로 인해, 복잡한 논리는 명명 함수를 사용하는 것이 이점이 많다.

### 5.8.4 익명 함수가 길면 문제가 될 수 있다

함수형 스타일 프로그래밍을 한다고 해서 반드시 인라인 익명 함수를 사용해야하는 것은 아니다.
익명 함수가 두세줄 이상으로 늘어나기 시작한다면, 여러 개의 명명 함수로 분리하면 코드의 가독성이 좋아진다.

### 5.8.5 해결책: 긴 익명 함수를 여러 개의 명명 함수로 나누라

한꺼번에 너무 많은 일을 하는 큰 함수를 분리하는 것은 코드의 가독성을 개선하기 위한 좋은 방법이다.
함수형 스타일의 코드를 작성할 때 이점을 잊지 말아야하며, 로직을 더 작은 단위의 명명 함수로 작성해야한다.

## 5.9 프로그래밍 언어의 새로운 기능을 적절하게 사용하라

### 5.9.1 새 기능은 코드를 개선할 수 있다

일반적으로 코드의 품질을 개선한다면 언어가 제공하는 기능을 사용하는 것이 좋다.

### 5.9.2 불분명한 기능은 혼동을 일으킬 수 있다

언에에서 새로운 기능을 제공하더라도 해당 기능이 다른 개발자에게 얼마나 잘 알려져 있는지 고려할 필요가 있다.

일반적으로 코드 품질을 개선한다면 언어가 제공하는 새로운 기능을 사용하는 것이 좋지만,
주변 개발자가 그 기능에 익숙하지 않다면 사용하지 않는 것이 좋을 때도 있다.

### 5.9.3 작업에 가장 적합한 도구를 사용하라

프로그래밍 언어에서 새로운 기능은 이유가 있기에 추가된다. 해당 기능이 단지 새로워서가 아니라
작업에 적합한 도구이기 때문에 사용한다는 점을 분명히 해야한다.

## 요약

- 코드의 가독성이 떨어져서 이해하기 어려울 때 다음과 같은 문제가 발생할 수 있다
  - 다른 개발자가 코드를 이해하느라 시간이 낭비됨
  - 버그를 유발하는 오해
  - 잘 작동하던 코드가 다른 개발자가 수정한 후 작동하지 않음
- 코드의 가독성을 높이다 보면 때로 코드가 더 길어질 수 있지만 이는 가치있는 절충이다
- 코드의 가독성을 높이려면 다른 개발자가 코드를 읽을 때 혼란스러움을 줄여야한다