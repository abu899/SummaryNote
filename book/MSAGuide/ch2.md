# 2장. 어떻게 적용해야 할까?

## 질문 1. 우리 시스템에 마이크로서비스 아키텍쳐가 적합할까?

어떤 아키텍쳐 스타일이 시스템에 적합하다는 것은 다른 아키텍쳐 스타일보다 `시스템의 중요한
비기능 요구 사항을 잘 달성할 수 있다는 의미`이다. MSA는 업무 단위로 시스템을 개발 및 배포하여
변경 리드 타임을 개선할 수 있고, 업무 단위로 장애 영향을 차단하며, 업무 단위로 시스템을 확장할 수 있는 장점이 있다.

그 중 업무 단위로 변경 리드 타임을 개선하는 것은 MSA 도입의 보편적인 이유다. 딜리버리 퍼포먼스가 저하되어 있는 거대한 시스템을
지속적인 전달과 변경 및 배포하는 단위를 작게 유지하기 위해 도입할 수도 있다.

업무 단위로 장애를 차단하거나 확장하는 것도 MSA의 도입 이유가 되지만, 모놀리식 아키텍쳐에서도
이 부분은 달성할 수 있기에 차이점을 잘 구별할 필요가 있다.

## 질문 2. 엔터프라이즈 시스템에도 어울릴까?

엔터프라이즈 시스템은 기업이나 정부 기관등의 비지니스를 돕기 위한 IT 시스템으로 MSA가 어울리지 않는다고
생각할 수 있다. 그러한 이유는 다음과 같다.

1. 엔터프라이즈 시스템은 모든 것을 계획하여 진행한다
   - 기업의 목표에 맞춰 프로젝트를 꾸리고 단기간에 집중적으로 개발한다
   - 시장의 피드백을 빠르게 따라가기 보다는 잘 정의된 기능을 신속하게 구현하는게 중요하다
2. 직접적으로 경쟁 관계인 시스템이 없다
   - 기업 내부에서만 사용하므로 다른 기업 시스템과 동시에 사용하는 사용자가 없다
   - 기업 비지니스 특성에 맞게 최적화 되어 다른 시스템과 리드 타임을 일대일로 비교하기 어렵다
3. 대상 사용자가 정해져 있고 상대적으로 사용 인원이 적다
   - 사용자가 한정되므로 니즈 파악이 용이하고 개선안을 구체화하기 쉽다
   - 시스템의 트래픽이 증가하고 감소하는 폭이 상대적으로 작다
4. 도메인이 다양하게 세분화되고, 도메인에 따라 시스템이 만들어진다

이처럼 엔터프라이즈 시스템은 B2C 비지니스 시스템보다는 변경 리드 타임이 중요하지 않기에
MSA에 적합하지 않다고 생각할 수 있다. 하지만 엔터프라이즈 시스템도 `내부적으로는 항상 변경 리드 타임을 중요시하고
시스템의 중요 지표`로써 관리하고 있다.

기업의 내부 시스템을 비교해보면 유독 리드 타임이 저하되는 시스템을 찾을 수 있다. 규모가 큰 기업이라면
MSA를 도입해 변경 리드 타임을 단축할 수 있는 시스템을 구축할 수 있고 그런 사례도 늘어나고 있다.

## 질문 3. 프로젝트 일정은 어떻게 수립할까?

MSA를 적용한다고 해서 프로젝트의 진행 방식이 크게 바뀌진 않지만, 일부 태스크에는 차이점이 있다.

### 도입 목표 및 서비스 선정

MSA를 도입할 때는 `목표를 명확하게 정의하고 서비스를 선정`해야 한다. 도입하는 목표는 서비스 선정을 포함하여 시스템의
전반적인 방향을 제시한다. 이후, 구성하는 `서비스 목록을 선정하고 서비스 목록과 서비스 간의 관계`는 인프라 설계의 기준이 되므로
초기에 정하는게 좋다. 이후 서비스 경계를 바꾸거나 합칠 수 있으므로 너무 부담을 가질 필요는 없다.

### 개발팀 구성

개발팀은 앞에서 선정한 서비스를 전담할 수 있도록 구성한다. 하나의 서비스는 하나의 개발팀이 담당하는 것을
원칙으로 한다. 개발을 아웃소싱하는 경우 `모든 기준은 운영팀을 기준`으로 한다.
MSA는 운영 중 개발 생산성을 높이는 것으로 아웃소싱 후 개발팀이 철수할 때 운영팀에 인계되므로
운영팀을 기준으로 의견을 반영하는 것이 맞다.

### 소프트웨어 미들웨어 설계

`도입 목표와 서비스 목록은 소프트웨어 미들웨어를 설계하는 기준`이 된다. 자유로운 스케일링, API 게이트웨이 등의 인프라를 구축해야 할 수도 있고
반대로 고정된 인프라의 경우 웹 서버의 설정으로 충분할 수 있다. 서비스 간의 인터페이스가 많다면 메시지 큐나
서비스 메시와 같은 클라이언트 사이드 라우팅 기술이 필요할 수 있다.

### 하드웨어 인프라 설계

서비스와 미들웨어 구성이 결정되면 하드웨어 구성과 용량을 결정할 수 있다. 이에 필요한
컴퓨팅 자원과 네트워크 자원을 설계하고 구축하는 테스크를 계획해야 한다.

### PoC(Proof of Concept)

검증이 필요한 새로운 기술 요소가 있다면 PoC를 수행할 수 있다. 먼저 PoC를 수행하여 동작하는
샘플을 확보하는 것이 좋다. 공통 기능을 제공하는 서비스는 정식으로 미리 개발을 시작하는 것도 좋다.

프로젝트의 비용과 일정은 MSA의 도입 목표, 소프트웨어 미들웨어, 하드웨어 인프라의 아키텍쳐에 영향을 받으므로 프로젝트 기획 단계에서
상당 부분 확정하는 것이 좋다.

## 질문 4. 프로젝트 비용은 어떻게 산정해야 할까?

프로젝트 비용을 구성하는 요소는 다양하지만 MSA 도입으로 영향을 받는 것은 크게 소프트웨어/하드웨어 비용과 소프트웨어 개발 비용이다.
서비스를 도출 한 후 아키텍쳐가 결정되면 산정할 수 있다. `미들웨어와 하드웨어의 경우 모놀리식 아키텍쳐와 동일한
계산 방식`을 가진다. 반면 소프트웨어 개발 비용 산정에는 족므 차이가 있다.

소프트웨어 개발 비용을 산정하는 방식을 다양하고 MSA의 공수를 산정하는 획일적인 계산 방법 또한 존재하지 않는다.
다만, MSA 적용으로 `개발 난도가 높아지거나 신규 개발이 필요한 부분을 식별`하는 것으로 MSA 시스템의 소프트웨어 개발 비용을 산정할 수 있다.

시스템의 기능이 하나의 서비스 안에서만 동작한다면 모놀리식 아키텍쳐로 개발하는 것과 비용이 같다. 하지만 여러 서비스에 걸쳐 구현되는 기능은
네트워크 통신이 필요하므로 구현 난도가 증가한다. 모놀로식 아키텍쳐로 기능을 구현하는 비용에 다른 서비스와 연계하는 코드의 개발 난도를
반영하는 것으로 MSA 개발 비용을 산정할 수 있다.

추가적으로 런타임이 분리된 구조로 인해 사용자 인증, 통합 로깅, 트레이싱과 같은 새로운 기능이 추가될 수 있으며,
인력의 러닝 커브 또한 비용에 반영할 수 있다.

## 질문 5. 서비스는 분리하고 데이터베이스만 열어주면 안될까?

현장에서 MSA를 적용할 때 가장 어려운 것 중하나는 서비스별로 DB를 분리하는 것이다.
PM은 리스크 헤징을 위해 다양한 방법을 찾는 중 유일한 예외로 서비스가 다른 서비스의 DB에 엑세스하여 조인하는 것을
허용하는 경우가 존재한다.

하지만 서비스 간에 조인을 허용하면, 결국 여러 서비스의 코드가 복잡하게 얽히게된다.
결국 내가 코드를 변경하고 싶어도 다른 서비스에서 참조하지 않는다는 확신을 가질 수 없게 된다.
그 결과로 남는 것은 모놀로식 아키텍쳐와 MSA의 단점만을 모아 놓은 최악의 아키텍쳐이다.

이는 MSA의 핵심 특징을 이해하지 못했기 떄문에 발생하는 것이다. 오히려 SQL 조인을 차단하는 것을
가장 중요하게 생각해야 한다.

MSA를 도입할 때 `서비스별로 DB를 분리하는 것은 피할 수 없다`. 서비스간 조인을 못하는게 부담스러울 수 있지만, 초기에 구현해보면
의외로 어렵지않게 해결해 나갈 수 있다.

서비스 갯수를 줄이는 것도 구현 부담을 줄일 수 있다. 서비스 갯수가 줄어들면 서비스 간의 API 참조가 줄어들어 전반적인 개발부담이 줄어든다.
또한 까다로운 트랜잭션을 피할 수도 있다. 적은 갯수의 서비스로 시작하여 시스템을 운영한 후 점진적으로
세분화하는 것이 오히려 더 좋은 방법이다.

아주 예외적으로 구체적인 사유가 있다면 `엄격한 관리하에` 다른 서비스의 DB에 엑세스를 허용할 수 있다.
정해진 기간 내에 해결이 어려운 구현 이슈 등 일시적인 예외를 허용하고 관리 감독할 수 있다.
앞서 설명한 사례와의 차이점은 `예외 케이스를 정확하게 인지하고 통제한다는 것`이다.

## 질문 6. 데이터베이스는 어디까지 분리해야 충분할까?

물리적인 서버를 분리해야하는지 혹은 논리적으로 스키마만 분리하면되는지 궁금할 수 있다.

비지니스 민첩성 관점에서는 서비스의 DB가 논리적으로 차단되면 충분하다. `중요한것은 서비스가 다른 서비스의 내부에
임의로 접근할 수 없어야 한다는 점`이다.

비지니스 민첩성 이외에 서비스 간의 장애 영향을 차단하거나 확장하는 것이 중요하다면 DB 서버를 분리해야 할 수도 있다.

## 질문 7. 도메인 주도 설계를 배워야할까?

MSA와 도메인 주도 설계(DDD)가 함께 언급되는 경우가 많지만 이는 직접적인 연관은 없다.
DDD는 MSA를 구축하는 하나의 방법일 뿐 서비스 도출하는 방법이며, 서비스 도출 과정을 짧은 시간에 비교적 정형화된 프로세스로 보여주는 것이다.

DDD의 바운디드 컨텍스트는 `독립적으로 모델링하는 단위`이지 독립적으로 배포되고 실행하는 서비스 단위는 아니다.
중요한 것은 어떤 방법론으로 서비스를 나누거나 개발하는지가 아니고, `어떤 상태가 되도록 서비스를 나누는가`이다.

## 질문 8. 우리 시스템은 왜 마이크로서비스 아키텍쳐를 도입했을까?

이런 질문의 근본 원인은 MSA를 도입할 때 구체적인 목표를 정의하지 않았기 떄문이다. 목표를 명확히 했다면 무리한 도입을
사전에 차단했을 수도 있으며 `시스템 구조만 변경하는 것이 아닌 적절한 조직과 프로세스도 도입` 되었을 수 있다.

도입하는 목표가 명확하지 않으면 세부적인 의사 결정에도 어려움이 생긴다.

1. 어떤 기준으로 서비스를 도출할지 판단할 수 없다
2. 기술 관점의 의사 결정을 적절히 내릴 수 없다
   - MSA는 시스템에 필요한 것을 선택하고 불필요한 것을 생략하는 과정이다
   - 불명확한 목표는 어떤 것이 필요한지, 불필요한지 판단할 수 없고 목표 달성에 쓸모없는 것이 들어간 아키텍쳐가 되어버린다

## 질문 9. 우리 시스템은 마이크로서비스 아키텍쳐일까?

MSA라고 할 수 있는 조건은 다음과 같다.

- 서비스가 업무 단위로 구성
- 각 서비스는 코드부터 DB까지 분리
- 운영게에서 독립적으로 배포

MSA와 동일한 구성을 가졌지만 독립적으로 배포가 불가능하면 제대로 구축된 MSA라고 하기 어렵다.
서비스별 소스코드와 실행환경이 분리되었지만, 운영계에서 배포전 전체 통합테스트를 거쳐야 배포가 가능하다면
이또한 모놀리식 아키텍쳐와 별 차이가 없다.

시스템은 서비스 단위로 독립적으로 배포할 수 있더라도, 요구 사항 접수부터 배포까지 시스템 단위의 공통 조직에서
통합 관리 하고 있다면 MSA의 본래 목적을 잘 살리지 못하고 있는 것이다.

MSA를 판단하는데 시스템 구조나 구성 요소가 유사한지는 고려하지 않아도 된다.
예를 들어 분산 시스템이 중요시하는 비기능 요구사항은 성능과 확장성으로, 이는 MSA와 일맥상통하는 점이 있다.
하지만 업무 기능에 따라 서비스를 분할하고, 개별 팀이 서비스를 전담하여 독립적으로 개발 및 배포하며,
소스코드와 데이터베이스를 분할하는 것을 `강요`하지 않는다.