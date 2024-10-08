# 1장. 마이크로서비스 아키텍쳐란?

## 1.1 일반적인 정의

> MSA는 시스템을 독립적으로 배포할 수 있는 서비스들로 구성하고 각 서비스는 잘 정의된 API로 통신한다.

MSA가 제공하는 장점은 다양하다.

- 서비스 단위로 변경하고 독립적으로 배포할 수 있다
- API로 기능을 재사용하고 조합하여 신규 기능을 만들 수 있다
- 아키텍쳐를 조직 구조에 맞게 정렬할 수 있다
- 서비스 단위로 스케일 아웃 할 수 있다
- 서비스의 장애가 다른 서비스에 퍼지는 것을 차단해 대형 장애를 방지할 수 있다
- 서비스별 다른 기술을 사용할 수 있으므로, 신규 기술을 적용하기 쉽다

---

## 1.2 정확하게 무엇인가?

### 1.2.1 유래

MSA의 형성과정을 살펴보면 어떤 개인이나 단체가 정의한 것이 아니다. 2010년 전후로 유사한 아키텍쳐를 가진 시스템이 생겨나고 있었다.
2011년에 일부 아키텍트가 모여 자신들이 탐구하던 아키텍쳐 스타일에 대해 이름을 붙였고 이후 여러 사람에게 불리며 널리퍼지게 되었다.
이처럼 MSA는 `자생적으로 생겨난 것이기 때문에 구체적인 정의가 없는 것`은 자연스러운 일이다.

또한 `MSA는 적용하는 목표가 명확하지 않다`. 서로 다른 배경을 가진 시스템들의 기술적인 특징을 모아 정리하다 보니 목표가 다양하고 장황하다.

가장 혼란스러운 점은 `아키텍쳐의 기준이 없다는 것`이다. 예를 들어 서비스 크기가 얼마나 되어야 마이크로인가에 대한 명확한 기준이 없다.
서비스 담당자가 한 명? 한 팀당 하나의 서비스? 이런 기준이 없다.

### 1.2.2 협의의 정의

먼저 기타 아키텍쳐 스타일이 아닌 MSA를 선택해야만 하는 이유가 무엇인지를 인지하고 MSA의 어떤 특징이 어떤 장점을 불러일으키는지를 이해해야 한다.

#### MSA의 장점

기존 아키텍쳐로는 해결하기 어렵기 때문에 MSA를 선택하게 하는 이유가 진정한 장점이라고 볼 수 있다

1. 시스템을 빠르게 변경할 수 있다

- 각 서비스를 담당하는 팀이 독립적으로 기획, 개발, 배포하여 다른 팀과 협업하거나 일정을 조율할 필요없이 빠른 시스템 변경이 가능하다

2. 독립적인 배포가 가능하다

- 모놀리식 아키텍쳐에 강한 모듈화를 적용해도 다른 모듈에 영향을 주는 부분을 대부분 차단할 수 있다
- 하지만, 독립적으로 배포한다는 점은 업무 단위로 별로 시스템을 배포할 수 있기 때문에 MSA만의 진정한 장점

3. 업무 단위로 장애를 차단하고 확장할 수 있다

- 장애에 민감한 업무는 다른 업무로부터 분리하여 보호하고, 다른 업무는 장애에 대한 부담을 덜어 쉽게 변경할 수 있다

이처럼 MSA는 `독립적으로 배포하여 시스템 변경 속도를 더 빠르게` 해야하거나 `업무 단위로 장애 차단 또는 시스템을 확장할 필요`가 있는 시스템에
적합하다.


#### MSA의 장점을 살리는 특징

1. 각 서비스는 비지니스 기능 단위로 나누어야 한다

- 서비스하는 팀이 협업 부서에 요청할 필요가 없기 때문
- 비지니스 기능은 서비스 내부에서 시스템을 변경하는 단위로 대부분의 변경이 이뤄져야 한다

2. 각 서비스 간에 임의적인 접근이 불가능하도록 격리해야 한다

- 다른 서비스가 임의의 코드를 참조할 수 없도록 소스를 분리하고 정해진 인터페이스로만 접근해야 한다

3. 서비스는 독립적으로 실행되고 API로 통신한다

- 각 서비스 간에 데이터를 조회하거나 트랜잭션을 발생시켜야 하는 경우 REST API나 이벤트로 통신

4. 각 서비스는 독립적으로 개발하고 배포해야 한다

- 서비스별로 독립된 팀이 자체적으로 세운 일정에 따라 개발, 배포

즉, MSA를 제대로 적용한 시스템은 서비스가 업무 단위로 분리되어 서로 정해진 인터페이스로만 통신하고, 독립적으로 실행하며 독립적으로 배포된다.

### 1.2.3 대규모 시스템 딜리버리 퍼포먼스 복원

모놀리식 아키텍쳐는 간결하고 오랜 기간 널리 사용되어 충분히 검증된 아키텍쳐 스타일이다.
따라서, 기피해야될 아키텍쳐가 아닌 MSA와 다른 장점을 가진 구조로 둘 중 시스템에 더 적합한 것을 선택해 적용하면 된다.

작은 시스템을 새로 개발할 떄는 모놀로식 아키텍쳐를 선택하는 것이 자연스럽다. 구조가 간단하고 많은 개발자에게 익숙하므로 개발 및 운영 효율이 좋다.

#### 시스템 규모가 커진다면?

사용자가 증가함에 따라 기능이 고도화되고 새로운 기능이 추가된다. 이런 경우 기능을 변경하기 위해 더 많은 사람들과 협업해야 하므로
그만큼의 `커뮤니케이션 비용`이 발생한다. 팀 내부에서 협의하고 확인하는 것과 다른 팀에 확인을 요청하는 것은 전혀 다른 차원의 일이다.

배포할 때 스트레스를 받는다. 모놀로식 구조에서는 문제가 발생하면 해당 팀이 문제를 해결하는 동안 다른 팀 사람들은 모두 대기해야될 수 있다.
이런 과정이 반복되면 예정보다 몇배의 시간이 소요될 수 있다.

#### 시스템 장애가 발생하면?

시스템 규모가 클수록 장애 발생 시 큰 영향을 받는다. 큰 장애가 여러 차례 발생하면 철저히 검증할 시간을 확보하기 위해 배포 주기를 늘리게된다.
이렇게 릴리스의 기간이 늘어남에 따라 변경 사항을 한번에 몰아서 하는 것이므로 그만큼 `리스크가 증가하고 담당자의 부담`이 커진다.

운영 환경에 변경 사항을 반영하는게 부담되면 결국 `요구사항을 소극적으로 접수`하게 된다. 시스템의 변경 리드 타임은 길어지고
사용자의 불만이 쌓여 더이상 변경하기 어려운 시스템이 되어간다.

배포 주기가 느려지는 이유는 실력이나 일을 대충해서가 아니다. 함께 합리적인 의사 결정을 거쳐 내린 결과로 상황이 발생하므로,
문제가 문제로 인식되지 못하거나 개선 방향을 찾기 어려워지는 경우가 많다.

MSA를 도입하면 `시스템을 독립적인 서비스로 분리하고 이를 각 팀에 배정`할 수 있다. 각 팀은 담당 서비스의 내부 기능을 변경할 때
다른 팀을 신경 쓰지 않아도 된다.

`다른 서비스의 기능을 사용하기만하고 제공하지 않는 서비스의 경우` 다른 서비스와 보조를 맞출 필요가 없고,
내 서비스에 장애가 발생하더라도 다른 서비스까지 장애가 발생하지 않는다. 이 경우 기능 변경과 배포에 부담이 전혀 없다.

반면 다른 서비스에 기능을 제공하는 경우는 조금 상황이 다르다. 서비스 내부 변경의 경우 비슷하지만, `외부에도 영향을 주는 변경은
모놀로식 아키텍쳐일 때보다 오히려 더 어렵다`. 따라서 가능하면 기존 API와 `하위 호환성`을 가질 수 있또록하는 고민이 필요하다.
영향이 전파되어 거의 전 시스템에 장애를 유발할 수 도 있기에 더 신중하고 점진적인 배포 방식을 고려해야한다.

### 1.2.4 대규모 시스템 딜리버리 퍼포먼스 향상

기능 변경 요청 단계에서는 `테스트 대기라는 아무런 생산성 없이 보내는 시간이 존재`한다. 배포 주기를 짧게 가져가면
변경 완료된 코드가 대기하는 시간이 줄어든다. 변경이 1, 2개에 불과하므로 테스트에 필요한 시간 또한 짧아진다.

대규모 시스템은 기능 변경 건수가 많기 때문에 지속적인 전달(CI)을 도입해 딜리버리 퍼포먼스를 향상하는 것이 상대적으로 어렵다.
이를 효과적으로 도입하기 위해서는 `협업하는 인원을 줄이고, 시스템을 작은 서비스로 분리하여 한번에 배포하는 변경 건수`를 줄여야한다.
협업하는 인원이 줄어듬에 따라 코드를 통합하기 쉬워지고, 적은 기능 변경을 반영하므로 기능을 검증하기도 쉽다.
또한 문제가 생기는 경우 다른 서비스의 버전에 신경쓸 것 없이 바로 해당 서비스만 이전 버전으로 복구할 수 있다.

### 1.2.5 이해하기 어려운 이유

`MSA로 해결하려는 목표는 기술적인게 아니기에` 기술 관점으로만 본다면 왜 적용하는지 이해하기 어렵다. 큰 조직의 개발 생산성을
유지하거나 작은 조직들이 독립적이고 빠르게 나아가게 하는 것은 기존 아키텍쳐에는 없던 목표이다. 확장성이나 장애 내성 같은
기술적인 측면에서 긍정적인 점을 느낄 수 있지만, `개발 난이도와 관리 부담이 증가`되는 것을 고려하면 큰 장점이 없다고 생각할 수 도 있다.

프로젝트 중심으로 일하는 방식과도 연관이 있다. MSA는 운영중인 시스템이 빠르게 변경되고 진화해간다는 뜻이다.
하지만 실무에서는 운영 중인 시스템과 프로젝트가 명확하게 분리되는 경우가 대부분이다. 시장의 요구에 빠르게 대응하기 위해서는
운영 중에도 많은 변경을 할 수 있어야 한다.

### 1.2.6 MSA와 직접적인 관련이 없는 것들

1. REST API
2. 재사용 관점에서의 MSA
   - 재사용만을 위해서 시스템을 서비스로 분리할 필요는 없다
   - 재사용 관점에서 중요한 것은 `재사용 할 대상`으로 이를 재사용하는 수단은 쉽게 선택할 수 있따
3. 클라우드 네이티브 어플리케이션
   - 클라우드 네이티브 어플리케이션은 모놀리식 아키텍쳐와 MSA를 포함

---

## 1.3 MSA 대표적인 사례와 특징

### 1.3.2 특징

1. 가장 눈에 띄는 특징은 인터넷 기반의 B2C 비지니스 시스템이다
   - 사용자가 수백만에서 수억에 달하기에 사용자의 니즈 파악이 어렵다
   - 어떤 기능을 잘 사용하는지, 어떤 개선점을 파악해야하는지 확인하기 어려운 경우가 많다
2. 경쟁이 심한 시장에서 핵심 서비스를 제공하는 시스템이다
   - 좋은 기능을 빠르고 안전하게 제공할 수 있어야 한다
3. 시스템을 개발하고 운영하는 엔지니어 수가 많다
   - 도입한 시점에서 이미 수십에서 수백명 혹은 수천명
4. MSA 도입 시점은 경험해보지 못한 새로운 상황이다
   - 기존에는 대규모 시스템에서는 더 엄격한 프로세스를 적용하여 천천히 안정적으로 반영하는 것이 표준이었다
   - 하지만 이런 경우 경쟁에서 뒤쳐질 수 밖에 없다

---

## 1.4 점진적인 전환 vs 빅뱅 전환

1. 스트랭글러 패턴을 적용하여 점진적으로 변환하는 방식
   - 시스템의 일부 기능을 서비스로 분리하고 기존 코드를 대체하는 작업을 반복
   - 시간이 오래걸리지만 리스크가 낮음
2. 프로젝트를 통해 한 번에 재개발하는 빅뱅 방식
   - 기간은 단축되지만 리스크가 높음

종종 MSA를 적용하는 것 차제가 목표인 것처럼 생각하는 경우가 존재한다. 하지만 중요한 것은 `시스템의 목표를 어떻게 달성`하는가 이다.

### 1.4.1 점진적인 전환

점진적인 전환의 경우 아키텍쳐 전환 관점에서 보면 그다지 실효성이 없어 보일 수 있다. 조금씩 변화하며 각 단계마다
많은 검증 및 노력이 필요하고 최종 전환까지 오랜 시간이 걸리기 때문이다.

`MSA의 모습을 갖추는 것이 중요한 것이 아니다`. 큰 시스템을 분할해 시스템 개선 속도롤 향상하는 것이 목표라면,
`시스템을 상대적으로 작은 부분으로 나눈 것이 중요`하지 전체 시스템의 모양이나 어떤 기술이 사용되는지는 중요하지 않다.

스케일 아웃을 위해 MSA를 도입하는 경우 더 명확하다. 사용자의 트래픽이 유난히 크게 증가하는 서비스를 분리하고
상대적으로 덜 중요한 부분은 점전적으로 진행할 수 있다.

운영 중인 시스템을 점진적으로 변경하는 것은 쉽게 선택할 수 없는 사항이다. 하지만, 빅뱅 방식으로 재개발하는 것보다
`리스크가 적고 MSA의 장점을 빠르게 발휘`할 수 있다.

#### 1.4.2 빅뱅 전환

MSA를 적용하는 또다른 방식으로 전면 재개발하는 것이다. `운영 시스템의 장애 걱정없이` 새로운 기능을 개발하고 검증이 가능하다.
점진적인 전환이 불가능한 상황이거나 처음 만드는 시스템의 경우 빅뱅 전환 방법이 자연스러울 수 있다.

빅뱅 방식의 가장 큰 단점은 리스크가 높다는 것이다. 실패를 최소화하기 위한 리스크 관리가 매우 중요하다.

