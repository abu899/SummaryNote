# 1장. 코드 품질

## 이 장에서 다루는 내용

- 코드 품질이 중요한 이유
- 고품질 코드가 이루고자 하는 네 가지 목표
- 고품질 코드 작성을 위한 높은 수준에서의 여섯 가지 전략
- 고품질 코드 작성이 어떻게 시간과 노력을 절약할 수 있는지

고품질 코드는 `신뢰할 수 있고, 유지보수가 쉬우며, 버그가 적은 소프트웨어를 생산`한다.
코드 품질을 높으는 것에 관한 원칙들은 소프트웨어가 처음에 만들어지는 방식을 보장하고,
이후 요구 사항이 진화하고 `새로운 상황이 등장하더라도 그 방식을 계속 유지할 수 있도록 하는 것에 관심`을 둔다.

이 챕터에서는 고품질 코드가 달성해야 할 네가지 목표를 살펴보고 일상 업무에서 사용할 수 있는
여섯 가지 고차원 전략으로 확장한다.

---

## 1.1 코드는 어떻게 소프트웨어가 되는가?

코드는 일반적으로 엔지니어가 작성하자마자 실제로 실행되는 소프트웨어가 되는 것이 아니다.
코드가 의도한 대로 작동하고 기존 기능이 여전히 잘 동작한다고 확신하기 위한 과정과 점검이 이뤄진다.
이를 `개발 및 배포 프로세스`라고 부른다. 그 순서는 다음과 같다

1. 개발자가 코드베이스의 로컬 복사본을 가지고 코드를 변경
2. 작업이 끝나면 코드 검토를 위해 변경된 코드를 가지고 병합 요청(pull request)
3. 다른 개발자가 코드를 검토(code review)하고 변경을 제안
4. 작성자와 검토자가 모두 동의하면 코드가 코드베이스에 병합(merge)
5. 배포(release)는 코드베이스를 가지고 주기적으로 진행
6. 테스트에 실패하거나 코드가 컴파일되지 않으면 코드베이스에 병합되는 것을 막거나 코드가 배포되는 것을 막는다

## 1.2 코드 품질의 목표

코드를 작성할 떄 다음 네가지 상위 수준의 목표를 달성하려고 노력해야한다.

### 1.2.1작동해야 한다

- 애초 작성된 목적대로 동작해야 한다

### 1.2.2 작동이 멈춰서는 안된다

- 코드는 고립된 환경에서 혼자 실행되는 것이 아닌, 주변 상황에 영향을 받는다.
    - 다른 코드에 의존할 수 있으며, 그 코드는 변경될 수 있다
    - 새로운 기능이 필요하면 코드를 수정할 수도 있다
    - 해결하고자 하는 문제는 시간에 따라 변경된다
    - 코딩의 모든 단계에서 고려해야 할 사항
    
### 1.2.3 변화하는 요구 사항에 적응해야 한다

- 요구사항은 변화한다
    - 비지니스 환경, 사용자 선호가 변화
    - 가정이 더 이상 유효하지 않음
    - 새로운 기능이 계속 추가
- 요구 사항이 어떻게 변할 것인지 완벽하고 정확하게 예측하는 것은 불가능
- 하지만 어떻게 변할지 알지 못한다고 변한다는 사실 자체를 완전히 무시해야하는 것은 아니다

### 1.2.4 이미 존재하는 기능을 또다시 구현해서는 안된다

- 일반적으로 큰 문제를 여러 개의 작은 하위 문제로 나눈다
- 이런 하위 문제들은 이미 다른 사람이 해결한 상태일 확률이 높다
- 자기가 다시 작성하는 대신 이미 구현된 코드를 재사용하는 것이 좋다
    - 시간과 노력을 절약
    - 버그 가능성을 줄여줌
        - 기존 코드는 이미 테스트를 진행하고 서비스 환경에서 사용 중이므로 버그 가능성이 낮다
    - 기존 전문 지식을 활용
        - 기존 코드 작성자의 전문지식과 향후 업데이트를 받을 수 있음
    - 코드가 이해하기 쉬움
        - 표준화된 방식으로 작성된 코드는 어떻게 동작하는지 즉시 이해 가능
- 코드를 다시 작성하지 않는다는 `양방향 개념`
    - 다른 개발자들이 동일한 문제를 해결하기 위해 코드를 다시 작성하지 않도록 `쉽게 재사용할 수 있는 방식으로 코드를 구성`해야 한다

## 1.3 코드 품질의 핵심 요소

일상적 업무로 하는 작업에서는 구체적인 전략을 파악하는 것이 유용하다.
따라서 여섯 가지 전략을 중심으로 이를 설명한다.

### 1.3.1 코드는 읽기 쉬워야 한다

보통 코드를 읽을 때 다음과 같은 사항을 확인한다.

- 코드가 하는 일
- 어떻게 그 일을 수행하는가
- 어떤 것을 필요로 하는가
- 코드 실행 결과물

코드 가독성이 떨어진다면 코드의 기능에 대해 잘못 이해하거나 중요 세부 사항을 놓칠 가능성이 높다.

### 1.3.2 코드는 예측 가능해야 한다

개발자는 이름, 데이터 유형, 일반적인 관행 같은 단서를 사용해 코드가 입력값으로 무엇을 예상하는지,
무슨 일을 하는지, 무엇을 반환하는지를 예측한다. 이 예측이 어긋나는 일이 코드에서 벌어진다면,
이로 인해 버그가 코드 내로 유입되는 일이 발생할 수 있다.

코드를 작성할 떄 아무리 좋은 의도를 가지고 있더라도 예상을 벗어난 일을 수행한다면, 예상을 벗어난 동작을 수행하는 위험이 있을 수 있다.
따라서 코드가 예상을 벗어나는 일을 수행하지 않는지 주의하고, 그런 코드를 작성해서는 안된다.

### 1.3.3 코드를 오용하기 어렵게 만들라

자신이 작성하는 코드는 종종 다른 코드에 의해 호출 되기 떄문에, 호출할 때 파라미터가 입력되거나
시스템이 특정 상태에 있을 것을 예상한다. 작성한 코드가 잘못 사용된다면 버그가 발생하거나 동작하지만 눈에 띄지 않을 수 도 있다.

따라서, 코드를 오용하기 어렵거나 불가능하게 하면 코드가 작동하면서 계쏙 잘 작동할 가능성을 극대화 할 수 있다.

### 1.3.4 코드를 모듈화하라

모듈화는 개체나 시스템의 구성 요소가 독립적으로 교환되거나 교체될 수 있음을 의미한다.
모듈화된 시스템의 주요 특징 중 하나는 인터페이스가 잘 정의되어 서로 `다른 구성 요소 간 상호작용하는 지점이 최소화`되는 것이다.

코드를 외부에 의존하지 않고 실행할 수 있는 모듈로 나누면 `모듈 사이의 상호작용은 인터페이스를 사용`한다.
기능을 관리가능한 단위로 나누고, 상호작용이 잘 정의되고, 문서화 된 모듈화는 변화하는 요구 사항에 적응하기 쉽다. 

### 1.3.5 코드를 재사용 가능하고 일반화할 수 있게 작성하라

재사용성과 일반화는 유사하지만 약간 다른 개념이다.
 
- 재사용성
  - 여러가지 다른 상황에서도 사용될 수 있음을 의미
  - 문제는 동일(드릴로 구멍을 뚫음)하지만 상황은 다른 경우(벽, 천장, 바닥)
- 일반화
  - 개념적으로는 유사(드릴은 무언가를 돌림)하지만 조금씩 다른 문제(구멍을 뚫음, 나사를 돌림)를 해결할 수 있음

코드는 일단 만들어지면 유지보수에 지속적인 시간과 노력이 들어간다.
따라서, 코드베이스에서 코드 라인을 적게하고 문제를 해결하는 것이 다른 문제를 일으킬 가능성을 줄인다.

코드가 재사용되고 일반화할 수 있으면 여러 상황에서 사용하여 여러 문제를 해결할 수 있다.
모듈화된 코드 또한 높은 재사용성과 일반화를 가진다.

### 1.3.6 테스트가 용이한 코드를 작성하고 제대로 테스트하라

테스트는 두 가지 핵심 사항에 대한 방어 수단으로 사용될 수 있다.

- 제대로 동작하지 않는 코드를 코드베이스에 병합되지 않도록 함
- 제대로 동작하지 않는 코드가 배포되지 않도록 막고 서비스 환경에서 실행되지 않도록 함

테스트는 코드가 동작하고, 멈추지 않고 계속 실행하도록 보장하기 위한 필수적인 부분으로 다음과 같은 이유로서 필요하다.

- 소프트웨어의 코드베이스는 크고 복잡해서 한 사람이 모든 세부사항을 알 수 없다
- 사람은 실수할 수 있다
 
#### 테스트

개발자들은 일반적으로 테스트 코드를 통해 실제 코드를 돌려보고, 모든 것이 정상적으로 작동되는지 확인하며, 이를 자동화하기 위해 노력한다
흔히 볼 수 있는 테스트는 다음과 같이 세 가지로 볼 수 있다.

- 단위 테스트
  - 개별 함수나 클래스와 같은 작은 단위의 코드를 테스트
  - 개발자가 가장 자주 작업하는 수준의 테스트
- 통합 테스트
  - 여러 구성 요소, 모듈, 하위 시스템으로 구성
    - 구성 요소와 하위 시스템을 함께 연결하는 과정을 통합(Integration)
  - 여러 구성 요소들이 제대로 작동하고 계속 실행되는지 확인
- 종단간(End to End, E2E) 테스트
  - 처음부터 끝까지 전체 소프트웨어 시스템에서 작동의 흐름을 테스트

#### 테스트 용이성

얼마나 테스트하기 적합한지를 나타내며, 모듈화된 코드는 테스트 용이성이 좋다. 

테스트 용이성을 확인하기 위해 코드를 작성하면서 `어떻게 테스트할 것인가`를 계속 확인하는 것이 좋다.
즉, 코드를 다 작성하고 나서 테스트에 대해 생각하는 것이 아니다.

## 1.4 고품질 코드 작성은 일정을 지연시키는가?

단기적으로는 고품질 코드를 작성하는데 시간이 더 걸릴 수는 있다. 하지만 고품질 코드를 작성하는 것이
장기적으로는 개발 시간을 단축시킨다.

## 요약

- 좋은 소프트웨어를 만들려면 고품질 코드를 작성해야 한다.
- 실제 서비스 환경에서 실행되는 소프트웨어가 되기 전에 여러 단계와 검사, 테스트를 통과해야한다
- 버그나 제대로 동작하지 않는 기능을 사용자에게 제공하거나 실행되는 것을 막기위해 테스트 해야한다
- 테스트는 코드를 작성하는 모든 단계에서 고려하는 것이 좋다
- 고품질 코드를 작성하면 처음에는 시간이 오래걸리지만, 장기적으로는 개발 시간이 단축될 수 있따




