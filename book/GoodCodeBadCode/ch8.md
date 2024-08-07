# 8장. 코드를 모듈화하라

## 이번 장에서 다루는 내용

- 모듈화된 코드의 이점
- 이상적인 코드 모듈화가 되지 않는 일반적인 방식
- 코드를 좀 더 모듈화하기 위한 방법

모듈화의 주된 목적 중 하나는 코드가 향후 어떻게 변경되거나 재구성될지 정확히 알지 못한 상태에서
변경과 재구성이 용이한 코드를 작성하는데 있다. 핵심 목표는 각각의 기능이 코드베이스의 서로 다른 부분에서 구현되고
요구 사항 중 하나가 변경되면, 요구 사항이나 `기능과 관련된 부분만 수정`하는 것이다.

2장에서 논의한 간결한 추상화 계층이라는 개념을 기초로 한다.

## 8.1 의존성 주입의 사용을 고려하라

코드에서 높은 수준의 문제를 하위 문제로 나눠서 해결할 수 있다. 좋은 코드는 하위 문제가 자신의 전용 클래스를
통해 해결 되는 경우가 많다. 하지만 하위 문제의 해결책이 하나만 존재하는 것이 아니기 때문에
`하위 문제를 재구성할 수 있는 방식`으로 코드를 작성하는 것이 유용하다.

### 8.1.1 하드 코드화된 의존성은 문제가 될 수 있따.

특정 구현에 의존해서 코드를 구현하면 다른 구현으로 코드를 재설정할 수 없다.
구현에 의존하게 되면 추상화 계층이 지저분해지고, 코드의 적응성이 한층 더 제한된다.
하드 코드화된 종속성은 바람직하지 않다.

### 8.1.2 해결책: 의존성 주입을 사용하라

인스턴스를 생성할 때 다른 구현체를 사용할 수 있다면 클래스는 더욱 모뮬화되고 다용도로 사용될 수 있다.
생성자의 매개변수를 통해 주입(injection)을 하면 이를 달성할 수 있다.

단점은 생성자가 좀 더 복잡해진다. 하지만 팩토리 함수를 제공하여 이 부분은 개선할 수 있으며,
의존성 주입 프레임워크를 통해 팩토리 함수를 직접 작성하는 것의 대안을 제공할 수 있다.

#### 의존성 주입 프레임워크

의존성 주입 프레임워크를 사용하면 의존성 주입과 관련된 작업을 수동으로 하지 않아서 개발이 쉬워진다.
따라서 반복적인 코드를 작성하는 시간을 줄이고, 모듈화되고 다용도로 사용할 수 있는 코드를 작성할 수 있다.

하지만 프레임워크 사용시 주의를 기울이지 않으면 프레임워크의 어떤 설정이 코드의 어떤 부분에 적용되지는 알기 어렵다.
따라서, 베스트 프랙티스를 참고하는 것이 바람직하다.

### 8.1.3 의존성 주입을 염두에 두고 코드를 설계하라

의존성 주입을 사용할 수 있다는 점을 의식적으로 고려하면, 나중에 의존성 주입을 사용하고 싶어도
사용이 불가능한 상황을 만들지 않는다.

하나의 아주 근본적인 하위 문제라면 관계없지만 `상위 코드 계층에서 하위 문제에 대한 설정을 다르게`하고자 한다면
문제가 발생할 수 있으니 항상 염두에 두자.

의존성 주입은 코드를 모듈화하고 다른 시나리오에도 적용할 수 있게 해주는 훌륭한 방법이다.
하위 문제에 대한 해결책이 여러 개 있는 경우 특별히 중요하다.

#### 정적 매달림(static cling)

정적 함수를 사용해도 의존성 주입을 일부 대체할 수 있다. 하지만 정적 변수에 과도하게 의존하는 경우 잠재적인 문제가 생길 수 있다.
특히 정적 매달림문제는 테스트 더블을 사용할 수 없기 때문에 테스트 코드 작성에 어려움이 존재한다.

## 8.2 인터페이스에 의존하라

클래스의 구현체에 직접 의존하기보다는 인터페이스에 의존하는 것이 일반적으로 더 바람직하다.

### 8.2.1 구현체에 의존하면 적응성이 제한된다

의존성 주입을 사용하지만 구현체에 의존하는 경우 의존성 주입을 사용할 때 얻을 수 있는 이점을 놓칠 수 있다.

### 8.2.2 해결책: 가능한 경우 인터페이스에 의존하라

구현체에 의존하면 인터페이스를 의존할 때 보다 적응성이 제한된다.
하지만, 추상적인 인터페이스에 의존하면 대개의 경우 간결한 추상화 계층과 더 나은 모듈화를 달성할 수 있다.
인터페이스에 의존한다는 것은 다른 개발자가 `해당 인터페이스에 대해 다른 구현을 작성`할 수 있다.

## 8.3 클래스 상속을 주의하라

대부분의 객체 지향 프로그래밍의 핵심 기능 중 하나는 상속으로 클래스 계층이 형성된다.

상속은 확실히 쓸모 있고 확실한 is-a 관계가 성립된다면 상속이 적절하고 유용할 수 있다.
하지만, 상속이 야기할 수 있기 때문에 상속은 항상 주의를 기울여야 한다.

상속 대신 구성(composition)을 사용할 수 도 있다. 클래스를 확장하기보다는 인터페이스를 가지고 다른 클래스로부터 구성하는 방법이다.

### 8.3.1 클래스 상속은 문제가 될 수 있다.

쉼표로 구분된 정수를 가지고 있는 파일을 열어 정수를 하나씩 읽어 들이는 클래스를 통해 상속의 함정을 살펴보자.
해당 클래스는 다음 하위 문제가 존재한다.

- 파일에서 데이터를 읽음
- 쉼표로 구분된 파일의 개별 문자열을 다룸
- 각 문자열을 정수로 변환

```java
interface FilerValueReader {
    String getNextValue();
    void close();
}

interface FileValueWriter {
    void writeValue(String value);
    void close();
}

/**
 * 쉼표로 구분된 값을 파일을 읽거나 쓰기위한 클래스
 */
class CsvFileHandler implements FilerValueReader, FileValueWriter {

    public CsvFileHandler(String fileName) {/*...*/}

    @Override
    public String getNextValue() {/*...*/}

    @Override
    public void close() {/*...*/}

    @Override
    public void writeValue(String value) {/*...*/}
}

class IntFileReader extends CsvFileHandler {
    public IntFileReader(String fileName) {
        super(fileName);
    }

    public int getNextInt() {
        String nextValue = getNextValue();
        return Integer.parseInt(nextValue);
    }
}
```

상속의 주요 특징 중 하나는 `서브클래스가 슈퍼클래스의 모든 기능을 상속`한다는 점이다.

#### 상속은 추상화 계층에 방해가 될 수 있다

슈퍼클래스의 모든 기능을 상속하는 게 유용해 보일 수 있으나, 원하는 것 보다 많은 기능을 노출할 수 도 있다.
이로 인해 추상화 계층이 복잡해지고 구현 세부 정보가 드러날 수 있다.

IntFileReader의 예시의 경우 CsvFileHandler의 모든 기능을 상속받았지만, 실제로 필요한 것은 getNextValue() 메서드 뿐이다.
하지만 추가적으로 writeValue()나 close() 또한 상속이 가능하다.

클래스의 `일부 기능을 외부로 개방하는 경우` 적어도 그 기능을 사용하는 개발자가 있을 것이라고 예상해야한다.

#### 상속은 적응성 높은 코드의 작성을 어렵게 만들 수 있다

요구사항이 변경되어 쉼표 뿐만 아니라 세미콜론으로 구분되는 값도 읽을 수 있어야한다고 가정해보자.
그런데 이미 해당 기능에 대해 SemicolonFileReader라는 클래스를 누군가가 작성했다.
하지만 해당 클래스는 CsvFileHandler를 상속한 것이 아닌, FileValueReader와 FileValueWriter를 상속받아 구현되어 있다.

CsvFileHandler와 같은 내용을 구현했기에 코드를 약간만 수정하면 될 것 같지만,
상속을 사용하는 경우 코드의 변경이 간단하지 않을 수 있다.

### 8.3.2 해결책: 구성을 사용하라

IntFileReader 클래스 입장에서 CsvFileHandler 클래스의 일부 기능을 재사용하는 것이 좋다.
상속은 이를 위한 한 가지 방법이지만 몇 가지 단점이 존재했다.

상속 이외에 구성을 사용하여 클래스를 확장하기 보다는 해당 클래스의 인스턴스를 가지고 하나의 클래스를 다른 클래스로
부터 호출하게 할 수 있다.

구성을 사용하면 코드 재사용의 이점을 얻을 수 있고 상속과 관련한 문제도 피할 수 있다.

#### 적응성이 높은 코드

구성을 사용하여 FileValueReader 인터페이스에 의존한다면 클래스 인스턴스를 생성할 때 주입을 통해
코드 중복 없이 쉼표 또는 세미콜론으로 구분하는 설정을 사용할 수 있다.

실제 개발에서는 클래스가 이보다 많은 기능과 코드를 포함하기에 작은 요구사항에도 변화하는 코드를 작성하지 못한다면,
코드를 유지하는 비용이 증가할 수 밖에 없다.

### 8.3.3 진정한 is-a 관계는 어떤가?

두 클래스가 진정한 is-a 관계일 경우 상속이 좋을 수 있다. 하지만, 진정 is-a 관계일 때 조차 상속이 최선의 방법인지에 대해서는 명확하지 않고 상황에 따라 다를 수 있다.

- 취약한 베이스 클래스 문제
  - 슈퍼클래스가 나중에 수정되면 서브클래스가 작동하지 않을 수 있다
- 다이아몬드 문제
  - 일부 언어는 두 개 이상의 슈퍼클래스를 확장할 수 있는 다중 상속을 지원한다
  - 여러 슈퍼 클래스가 동일한 함수의 각각 다른 버젼을 제공하는 경우 문제가 발생할 수 있다
- 문제가 있는 계층 구조
  - 단일 상속이여도 다른 문제가 발생할 수 있다
  - Car와 Aircraft라는 클래스가 존재하지만, 만약 FlyingCar 클래스가 생긴다면 어떤 계층구조에 들어가햘까?

클래스 상속에 숨어있는 많은 함정을 피하면서 계층 구조를 달성하기 위해서는 다음 해결책이 존재한다.

- 인터페이스를 사용하여 계층 구조를 정의
- 구성을 사용하여 코드를 재사용

## 8.4 클래스는 자신의 기능에만 집중해야 한다

모듈화의 핵심 목표 중 하나는 요구 사항이 변경되면 그 `변경과 직접 관련된 코드만 수정한다는 것`이다.
단일 개념이 단일 클래스 내에 포함된 경우라면 요구 사항이 변경되도 해당 클래스만 수정하면 된다.

반대되는 상황은 하나의 개념이 여러 클래스에 분산되는 경우다.
이 경우 요구 사항이 변경되면 관련된 클래스를 모두 수정해야하며 하나를 잊어버리고 수정하지 않는다면,
버그가 발생할 수 있다. `클래스가 다른 클래스의 세부 사항에 지나치게 연관된 경우` 발생할 수 있다.

### 8.4.1 다른 클래스와 지나치게 연관되어 있으면 문제가 될 수 있다

### 8.4.2 해결책: 자산의 기능에만 충실한 클래스를 만들라

코드 모듈화를 유지하고 한 가지 사항에 대한 변경 사항이 코드 한 부분에만 영향을 미치도록 하기 위해서는
가능한 자신의 기능에만 충실해야한다. 물론 클래스는 서로에 대한 정보가 필요할 때가 있지만,
가능한 이것을 최소화 하는 것이 좋을 떄가 많다.

## 8.5 관련 있는 데이터는 함께 캡슐화하라

너무 많은 것이 한 클래스에 있는 것도 주의해야 하지만, 한 클래스 내에 함께 두는 것이
합리적인 경우 그 이점에 대해서도 고려해야한다.

서로 다른 데이터가 서로 밀접하게 연관되어 그것이 `항상 함께 움직여야하는 경우`가 그러하다.
이런 경우 클래스로 그룹화 하는 것이 합리적이며, 여러 항목의 세부 사항을 다루는 대신
항목들이 묶여있는 클래스를 통해 상위 수준의 개념으로 다루는 것이 좋다.

### 8.5.1 캡슐화되지 않은 데이터는 취급하기 어려울 수 있다.

```java
class TextBox {
    // ...
    
    void renderText(
            String text,
            Font font,
            Double fontSize,
            Double lineHeight,
            Color textColor) {
        /**/
    }
}
```

위 renderText의 설정 옵션들은 캡슐화 되어있지 않다. 만약 한가지 옵션이 더 추가된다면,
파라미터를 추가해야되고 이를 호출하는 함수도 수정해야한다.

### 8.5.2 해결책: 관련된 데이터는 객체 또는 클래스로 그룹화하라

```java
class TextOptions {
    private final Font font;
    private final Double fontSize;
    private final Double lineHeight;
    private final Color textColor;

    public TextOptions(
            Font font,
            Double fontSize,
            Double lineHeight,
            Color textColor) {
        this.font = font;
        this.fontSize = fontSize;
        this.lineHeight = lineHeight;
        this.textColor = textColor;
    }
}
```

위 클래스처럼 관련된 데이터를 그룹화하면, 캡슐화된 데이터를 사용하여 코드를 작성할 수 있고
옵션이 추가되더라도 클래스 내부의 값을 변경하는 것으로 충분하다.

여러 데이터가 따로 떨어져서 별 의미가 없는 경우나 캡슐화된 데이터 중에 일부만 원하는 경우가 아니라면 캡슐화 하는 것이 좋다.

## 8.6 반환 유형에 구현 세부 정보가 유출되지 않도록 주의하라

간결한 추상화 계층을 가지려면 각 계층의 구현 세부 정보가 유출되지 않아야 한다.
코드에서 구현 세부 정보를 유출하는 일반적인 형태 중 하나는 해당 세부 정보와 연관된 유형을 반환하는 것이다.

### 8.6.1 반환 형식에 구현 세부 사항이 유출될 경우 문제가 될 수 있다

프로필 사진을 조회하기 위한 코드인 ProfilePictureService라는 클래스는 서버에서
사진을 가져오는 HttpFetcher를 사용한다고 가정해보자. 해당 서비스의 결과는
ProfilePictureResult 클래스로 HttpResponse.Status와 HttpResponse.Payload를 사용한다.
이 두 가지 모두 HTTP 연결을 사용하는 사실을 유출한다고 볼 수 있다.

이게 왜 문제가 될 수 있을까?

- 다른 개발자가 해당 서비스를 사용하면 HttpResponse와 관련된 개념을 처리해야한다
  - 요청의 성공, 실패 여부를 이해하기 위해 HttpResponse.Status 의 Enum을 확인해야한다.
  - 상태 코드에 대한 지식이 필요하며 실제 서버가 어떤 상태 코드를 반환하는지도 알아야한다.
- 해당 서비스의 구현을 변경하는 것이 어렵다
  - 해당 서비스를 호출하는 모든 코드는 Status와 Payload를 다뤄야한다
  - 만약 웹소켓 연결을 사용해야되면 이를 지원하기 위해 해당 유형을 다루는 코드들을 모두 변경해줘야 한다.

### 8.6.2 해결책: 추상화 계층에 적합한 유형을 반환하라

클래스를 사용하는 다른 개발자에게 노출되는 개념이 최소가 되도록 노력해야한다.
노출해야할 최소한의 개념은 다음과 같다.

- 요청이 성공하거나 어떤 이유로 실패할 수 있다
- 요청의 성공에 대한 결과 값

외부로 노출할 개념을 최소화하는 유형을 새로 정의하면 보다 모듈화된 코드와 간결한 추상화 계층을 얻을 수 있다.

## 8.7 예외 처리 시 구현 세부 사항이 유출되지 않도록 주의하라

구현 세부 정보가 유출되는 다른 경우는 예외를 발생하는 경우다.
호출하는 쪽에서 복구하고자 하는 오류에 대해 비검사 예외를 사용하는 경우 예외 처리 시
구현 세부 정보를 유출하는 것이 특히 문제가 될 수 있다.

### 8.7.1 예외 처리 시 구현 세부 사항이 유출되면 문제가 될 수 있다

비검사 예외는 예외가 발생하는 위치나 시기, 코드가 어디에서 그 예외를 처리하는 지에
대해 컴파일러에 의해 강제되지 않는다는 것이 특징이다.

이런 경우 추상화 계층 개념을 위반할 뿐만 아니라 신뢰할 수 없고 오류를 일으키기 쉽다.
해당 예외를 제대로 처리하지 못하고 프로그램이 멈추거나 높은 계층에서는 도움이 되지 않는
오류 메시지를 보여줄 수도 있다.

### 8.7.2 해결책: 추상화 계층에 적절한 예외를 만들라

구현 세부 사항의 유출을 방지하기 위해 코드의 각 게층은 `주어진 추상화 계층을 반영하는 오류 유형만`을 드러내는 것이 좋다.
이것은 하위 계층의 오류를 현재 계층에 적합한 오류 유형으로 감싸면 가능하다.

해당 방법의 단점은 다양한 예외를 처리해야하고, 예외를 감싸고 다시 발생시켜야 하기 떄문에 코드 줄이 많아진다.
하지만 반복 코드가 늘어난다는 단점에 비해 `클래스의 동작이 예측 가능`하고 모듈화가 개선된다는 장점이 존재한다.

만약 호출하는 쪽에서 `복구하기를 원하는 오류`가 있다면 오류 유형이 추상화 계층에 적합한지
확인하는 것이 중요하다.

## 요약

- 코드가 모듈화되어 있으면 변경된 요구 사항을 적용하기 위한 코드를 작성하기 쉽다
- 모듈화의 목표 중 하나는 요구 사항 변경이 직접 관련된 코드에만 영향을 주는 것이다
- 모드를 모듈화하는 것은 추상화 계층을 만드는 것과 관련이 깊다
- 해당 기술들을 사용해 모듈화가 가능하다
  - 의존성 주입
  - 인터페이스에 의존
  - 상속 대신 인터페이스 및 구성을 활용
  - 클래스는 자신의 기능만 처리
  - 관련된 데이터의 갭슐화
  - 반환 유형 및 예외 처리 시 구현 세부 정보 유출을 방지