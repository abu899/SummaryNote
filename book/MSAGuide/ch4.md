# 4장. 서비스 선정하기

이번 장 에서는 서비스를 선정할 때 시행착오를 줄이고 안정적으로 서비스를 분할할 수 있는 방법에 대해 알아본다.

## 4.1 서비스 선정 및 설계 절차

1. 도입하고자 하는 시스템에 MSA가 적합한지를 판단한다(적합성 검토)
2. MSA로 달성하고자 하는 목표를 정하고 목표가 달성된 상태를 기술한다(도입 목표 수립)
3. 시스템을 분석해서 서비스로 분리할 수 있는 업무를 식별한다(분할 단위 도출)
4. 식별한 업무를 조합하여 서비스를 선정한다(서비스 선정)
5. 업무 시나리오를 바탕으로 시스템 동작을 시뮬레이션해서 서비스의 역할과 인터페이스 도출하고 도입 목표를 만족하는지 검토(서비스 설계)

서비스를 선정하고 설계하는 작업은 시스템과 업무를 잘 아는 사람들이 모여 1~2주 이내에 짧은 기간에 진행한다.
서비스 목록이 있어야 개발 일정과 비용을 산출할 수 있으므로, 개발 전에 서비스 설계가 완료되어야 한다.

---

## 4.2 적합성 검토

가장 먼저 결정해야하는 것은 회사의 시스템이 MSA에 적합한지 판단하는 것이다.
시스템의 중요한 비기능 요구사항을 MSA로 달성할 수 있고, MSA의 단점을 적절히 보완할 수 있는지를 판단한다.

> 비기능 요구 사항
> 시스템이 제공하는 기능에 기대하는 품질 수준

MSA를 통해 달성할 수 있는 비기능 요구 사항은 `비지니스 민첩성, 장애 격리, 확장성`이다.

### 4.2.1 비지니스 민첩성

비지니스 민첩성은 변경 리드 타임을 단축하여 `시장의 변화에 빠르게 대응`하는 것을 의미한다.
이런 역량을 `딜리버리 퍼포먼스`라고 하며 `배포 빈도, 변경 사항 리드 타임, 평균 복구 시간, 변경 사항 실패율`을 기준으로 측정할 수 있다.
변경 사항 리드 타임의 경우 코드 커밋 시점부터 운영 시스템에 반영하여 성공적으로 동작하는 시점까지 소요되는 시간이다.
변경 사항 실패율은 운영 시스템의 소프트웨어나 인프라를 수정했을 때 실패하는 확률이다.

소프트웨어 딜리버리 퍼포먼스의 개선은 두가지 유형으로 구분할 수 있다.

1. 저하된 퍼포먼스 복구
   - 평균보다 저하된 딜리버리 퍼포먼스를 복구하는 것
   - 크고 중요한 시스템일 수록 프로세스를 엄격하게 지키는 경우가 많음
   - 운영의 규모가 크고 요구 사항 접수부터 배포까지 일정조율과 협업이 잦아 업무 효율이 저하
   - `여러 개의 작은 서비스로 분리하여 일하는 인원의 규모를 줄이는 것으로 개선`
2. 더 빠른 퍼포먼스 달성
   - 평균 딜리버리 퍼포먼스를 가진 시스템이 작고 독립적인 조직을 구성하고, CI/CD를 통해 딜리버리 퍼포먼스를 향상시키는 것
   - 시스템을 서비스로 분리하여 각 팀이 코드와 인프라에 대한 전권을 가지도록 개선

#### 딜리버리 퍼포먼스가 저하되었는지 판단하는 기준

시스템의 종류나 도메인에 따라 상황이 다를 수 있어 절대적인 기준은 없다. 하지만 경쟁 업체의 시스템보다
기능 출시가 늦고, 시장의 변화속도를 따라잡지 못한다면 이는 분명한 신호다.

저자가 현장에서 경험한 사례를 바탕으로 3가지 기준을 제시한다.

1. 시스템에 변경된 기능을 정기적으로 배포하는 주기는 비지니스 민첩성을 측정할 수 있는 직접적인 지표가 된다

예를 들어, 규모가 큰 시스템의 경우 월 1회나 그 미만으로 배포한다면 저하된 것으로 볼 수 있다.
사용자가 요청한 변경사항이 빠르게 변경되길 원하지만 시스템이 따라가지 못하는 경우에만 해당한다.
`비정기 배포나 긴급한 버그 패치는 제외`한다.

2. 운영 중인 시스템을 개선하고 유지하는 담당자의 수가 간접적인 지표가 될 수 있다

함께 `협업하는 담당자 수가 많아질수록` 비지니스 민첩성이 저하되는 경향이 있다.
여기서 담당자의 정의는 변경 요청을 접수하고, 요구사항을 상세화하여 설계하며 기능을 개발, 검증, 배포하는 모든 사람을 말한다.

3. 월평균 기능 개선이나 변경 건수도 간접적인 지표가 될 수 있다

정기적으로 배포하는 모놀리식 시스템이 많은 기능을 변경하고 있다면, 내부적으로는 이미 한계에 다다랐을 수 있다.
기능 변경이 잦은 큰 시스템의 경우 MSA 적합도가 높은 편이다.

인원 수나 변경 건수의 숫자에는 크게 의미를 둘 필요는 없고 어느정도 수치가 되었을 때
딜리버리 퍼포먼스가 낮아지는지를 이해할 수 있어야한다.

### 4.2.2 장애 격리

장애 격리는 `시스템의 대규모 장애를 방지`하는 것을 말한다.

MSA가 기존 분산 아키텍쳐 스타일과 다른 점은 업무를 기준으로 시스템을 분리하므로 `업무 단위로 장애 확산을 방지`할 수 있다는 것이다.
별도로 관리해야 하는 중요한 업무가 있고 단위별로 장애를 방지해야하는 상황이라면 MSA가 적합하다.

시스템에 미션 크리티컬한 업무가 포함되면, 시스템의 모든 기능은 해당 업무를 기준으로 변경 프로세스를 가져가게 된다.
이런 중요 업무를 일반 업무와 별도의 서비스로 분리하면 중요 업무는 다른 업무의 오류로 장애가 발생하는 것을 방지할 수 있다.

### 4.2.3 확장성

확장성은 시스템 규모를 필요에 따라 확장하고 축소하는 것이다. MSA의 장점은 업무 단위로 기술 스택을 적용하고
스케일 인, 아웃을 할 수 있다는 것이다.

단순히 트래픽이 증가하고 감소하는 것을 해결하려면 스케일 업 방식도 고려할 수 있다.
시스템이 스트키 세션이나 세션 클러스터링을 하고 있다면 스케일링을 하는 경우 문제가 발생할 수 있으므로
인증 및 세션관리 방식을 변경해야할 수 있다.

---

## 4.3 도입 목표 수립

목표를 구체적으로 기술하는 것은 이후 서비스 선정과 설계 단계에서 `중요한 기준점`으로 활용된다.
도입 목표도 적합성 검토와 마찬가지로 비지니스 민첩성, 장애 격리, 확장성을 기준으로 수립해야한다.

### 4.3.1 비지니스 민첩성

이는 다양한 관점에서 정할 수 있지만, `최종 목표에 가까운 시스템의 배포주기나 변경 리드타임 단축`으로 잡아야한다.

또한 이키텍쳐 뿐만이 아니라 조직과 프로세스가 함께 변경되어야한다. 만약 아키텍쳐 기준으로만 목표를 잡으면
조직과 프로세스는 이전 방식을 그대로 적용할 수 있기에 MSA의 장점을 살리기 어렵다. 비지니스 관점에서
직접적인 효과를 측정할 수 있는 `배포 주기, 배포 횟수 또는 변경 리드 타임의 단축을 목표`로하는 것이 좋다.

```text
비지니스 민첩성을 기준으로 한 목표 수립 예시

- 정기 배포 주기를 월 1회에서 주 1회로 단축
- 정기 배포 주기를 주 1회에서 매일 1회로 단축
- 배포 횟수를 월 2회에서 32회로 증가(8개의 서비스를 주 1회 배포)

조직과 프로세스 관점의 목표 예시

- 작고 독립적인 조직이 서비스를 전담
- 담당 조직이 배포 시점을 정하고 직접 운영 환경에 배포
```

아키텍쳐의 목표는 서비스별로 개발과 배포를 독립적으로 하는 것으로 `다른 서비스 개발팀과 협의할 필요가 없는 것이 목표`다.
배포한 코드의 오류가 있더라도 다른 팀의 코드에 오류가 발생하지 않고, 새로 업데이트한 서비스가 오작동하더라도
다른팀 서비스에 장애를 일으키지 않는다는 확신이 있어야한다. 이를 위해 소스코드를 분리하고
서비스간의 데이터베이스 접근을 차단하여 API나 이벤트로만 통신해야한다.

```text
아키텍쳐 관점의 목표 수립 예시

- 서비스별 소스 코드와 빌드 배포 파이프라인을 분리
- 서비스는 정해진 API와 이벤트로만 통신
- 서비스는 다른 서비스의 데이터베이스에 접근할 수 없다(조인 불가)
```

### 4.3.2 장애 격리

장애 격리의 목표는 엔드 유저 관점에서 구체적으로 정의하는 것이 좋다.
서비스에 장애가 발생할 때 다른 서비스에 미치는 영향을 완벽하게 차단하는 것은 어렵다. 따라서 `장애가 발생했을 때
모든 기능을 100프로 정상 제공하는 것은 불가능`하므로, 엔드 유저 입장에서 `임팩트를 최소화`하는 것을 목표로 해야한다.

서비스 사이에서 장애가 퍼지는 메커니즘을 경험해보지 못하면 구상한 방법이 실제로 효과가 없을 수 있다.
따라서 `엔드유저가 체감할 수 있는 목표를 정해두고` 실제 효과가 있는 방법을 찾아서 적용하고 검증해야한다.
서비스가 호출하는 API에 타임 아웃을 설정하는 것도 좋은 방법이다.

```text
장애 격리를 기준으로한 목표 수립 예시

- 업무 A의 서비스에 장애가 발생하면, A를 실행할 때 안내 메시지가 제공되고 다른 서비스 화면은 정상 출력된다
- 업무 A의 서비스에 장애가 발생하면, 업무 B의 서비스 기능 중 A 서비스에 의존하는 기능만 오류가 발생하고 나머지는 정상 동작해야한다.
- 중요 기능을 담당하는 C 서비스는 다른 업무 서비스에 장애가 발생하더라도 사용자가 정상적으로 사용할 수 있어야 한다
```

장애 격리 목표는 중요 업무와 같은 구체적인 대상이 아니고서는 모든 사례를 기술하기 어렵다. 전반적인 장애 확산을
방지하기 위한 것이라면 많은 상황을 정의하는 것이 불가능하다. 따라서 모호한 상황을 명시하고 해결하는 것을
목표로 정의해야 한다.

### 4.3.3 확장성

확장성과 관련된 도입 목표 또한 구체적으로 정의해야 한다.

```text
확장성을 기준으로한 목표 수립 예시

- 서비스 A의 인스턴스 증설 요청을 받으면 인프라 담당자는 4시간 이내에 증설할 수 있어야 한다.
- 서비스 B의 인스턴스 평균 CPU  사용량이 70% 이상이 되면, 기존 인스턴스 수의 10%에 해당하는 인스턴스를 신규로 실행한다.
```

스케일 아웃하여 대응하려는 시도는 `신규 인스턴스가 시작하는 시간` 떄문에 정상적으로 동작하지 않을 수 있음을 고려해야한다.
신규 인스턴스가 준비되는 동안 기존 요청을 차단하거나 서비스 품질을 떨어뜨려 더 큰 부하를 감당할 수 있게해야한다.

```text
신규 인스턴스가 실행되는 동안 목표 수립 예시

- 추천 서비스 CPU 사용량이 80% 이상이 유지되면, 기존 인스턴스 20%에 해당하는 인스턴스를 신규 인스턴스로 생성한다.
- 이때 기존 인스턴스는 고객 요청의 50%에 대해 추천 알고리즘을 실행하지 않고 사전에 정의한 정보를 제공한다. 
```

위의 목표는 품질 속성 시나리오(quaility attribute scenario)를 기반으로 정의한 것이다.
잘 인지하기 어려운 비기능 요건을 시나리오처럼 기술하여 설계에 사용하는 것으로 구체적인 수치를 제시하기 어려울 수 있다.
너무 고민하지 말고 대략적인 목표치를 수치로 정하고 시작해도 무방하다.

### 4.3.4 구체적인 목표 공유

도입 목표는 적용과정에 관여하는 모든 참여자에게 공유해야한다. 대규모 프로젝트의 경우
신규 개발자가 계속해서 투입되면 목표가 잘 전달되지 않을 수 있으므로, 이부분에 대해 잘 인지할 수 있도록 해야한다.

---

## 4.4 분할 가능한 업무 식별

### 프로세스

적합성 검토 -> 도입 목표 수립 -> 분할 단위 도출 -> 서비스 선정 -> 서비스 구체화

도입 목표를 정했다면 서비스로 분리 가능한 업무를 식별한다. 식별된 업무가 바로 서비스가 되는 것은 아니므로
구현 이슈 등은 고려하지 않고 업무를 식별해도 된다. 이후 `서비스 선정`에서 하나의 업무가
하나의 서비스가 될 수도, 여러 개가 하나의 서비스가 될 수도 있다.

### 4.4.1 서비스를 왜 업무 기준으로 나눠야 하지?

업무를 기준으로 나누는 이유는 사용자에게 전달하는 리드 타임을 최소화할 수 있기 때문이다.
규모가 큰 시스템은 한 시스템에 여러 이해관계자의 기능을 담고 있는 경우가 많기에 `여러 이해 관계자의
기능 요청이 서로 경합`을 벌이게 된다.
그렇기에 여러 이해관계자의 변경 요청이 시스템 단위로 접수되므로 나중에 요청한 요구 사항은 반영이 밀릴 수 있다.
또한 많은 변경 사항을 함께 통합, 검증, 배포하기 떄문에 단계별 소요 시간이 길어지게 된다.

업무를 기준으로 서비스를 나누면 대부분의 기능 변경이 하나의 서비스에만 일어나게 할 수 있다.
다만 MSA는 서비스와 조직이 나누어져 있으므로 `여러 서비스를 동시에 변경하는 것은 모놀리식보다 더 어렵다`.
여러 서비스를 동시에 변경하게 되는 경우 여러 팀이 모여 함께 변경 방향을 논의하고, 타이밍에 맞춰서 검증하고 배포해야 하기 때문이다.

결국 `업무는 사용자가 원하는 변경의 요청 단위`이다. 응집도가 높은 업무를 기준으로 서비스를 나누면 서비스 간의 참조 관계도 줄어든다.
또한 참조 관계가 적어지면 불필요한 API 호출이나 보상 트랜잭션도 즐어들어 구현 난도가 감소한다.

### 4.4.2 어떤 기준으로 업무를 나눌까?

분할 단위 도출 단계에서는 업무 기능을 응집도가 높은 것끼리 분류하는 데에 집중한다.
서비스가 나누어져서 구현이 너무 어려워진다면 하나의 서비스로 합칠 수도 있다. 따라서 서비스로 구현했을 떄의
이슈는 걱정하지 말고 순수하게 업무를 도출하는 것에 집중한다.

#### 주요 업무 분류

가장 보편적인 방법은 `주요 업무를 분류`하는 것이다. 이미 업무를 기준으로 내부 조직이 편제되어 있기에
시스템에서 기존에 분류한 주요 업무를 나열하면 된다.

첫 번째 방법은 시스템의 업무 구성을 보여주는 `정보구조도의 활용`이다. 정보구조도는 시스템 업무를 요약하여 구조적으로 보여줄 수 있다.
이 때 업무의 레벨, 중요도, 규모를 고려하여, 최상위 업무는 비슷한 레벨이어야 하고, 업무 간의 고나계나 중요도를
고려하여 배치한다. 상위 업무 아래의 하위 업무 또한 함께 작성한다.

정보 구조도와 비슷하게 메뉴 구조도 좋은 기준이 된다. 메뉴 구조는 서비스 도출과 직접적인 관련이 없는
사용자 경험도 함께 고려하여 결과를 반영하게 된다.

두 번째 방법은 여러 레벨로 구성한 유스케이스 다이어그램이다. 사용자 관점에서 시스템과 사용자의 상호작용을
다이어그램으로 표현한 것으로, 유스케이스는 비교적 상세한 기능을 담고 있어서 서비스의 후보가 되기에 작은 경우가 많다.
반면 규모가 큰 시스템은 하나의 다이어그램으로 표현이 불가능하지만, `시스템을 보여주는 하나의 유스케이스 다이어그램과
업무별 세부 기능을 보여주는 다이어그램으로 구분`하여 표시할 수 있다.

세 번째 방법은 도메인 주도 설게의 컨텍스트 다이어그램의 활용이다. DDD의 바운디드 컨텍스는 독립적인 모델링 단위가 되며
구현할 때는 트랜잭션 단위가 된다. 따라서 이를 서비스로 분할 할 수 있는 주요 업무로 선택할 수 있다.

위 세가지 방법 중 어떤 것이 더 좋거나 나쁘거나 한 방법은 없으며 결과적으로 비슷한 결과를 가진다.
가장 익숙한 방법을 선택하는 것이 좋다.

#### 비지니스 프로세스

업무는 비지니스 프로세스의 단계별로 나눌 수 있다. 긴 호흡을 가진 비지니스 프로세스는 각 단계의 전후가 명확하게 구분되는 경우가 많다.
이런 경우 각 단계는 서비스로 전환 가능한 업무 단위가 된다.

#### 시장이나 제품

시장이나 제품에 따라 업무를 구분할 수 있다. 업무의 종류나 프로세스는 서로 동일해 보이지만,
대상에 따라 세부 속성이나 프로세스 규칙이 다르고 이해관계자도 다르다.

실제 현장에서는 이런 판단이 어려우므로 업무를 분류하는 단계에서는 일단 별도의 업무로 식별하고,
서비스를 선정하는 단계에서 검토하는 것이 좋다.

### 4.4.3 업무 간 연관 관계 식별

업무를 도출하면 업무 간의 관계를 기술해야한다.

#### 이벤트 스토밍

시스템을 이해하고 업무 간의 관계 또한 파악하고 싶다면 DDD의 이벤트 스토밍을 빅 픽쳐 단계에서 실행하는 것도 하나의 방법이다.
큰 그림을 파악하기 위해 하루 이내에 진행하며 업무 이벤트, 사람, 그리고 시스템 이 세가지 요소만으로
주요 업무와 업무 간의 관계 그리고 바운디드 컨텍스트를 식별할 수 있다.

서비스는 프로세스나 모델링의 관련성만 고려하는 것이 아니라 실제 비지니스의 요구 사항이
반영되야하므로 도메인 전문가가 직접 참여하도록 해야한다.

#### 코드 레벨 분석

소스 코드를 분석하여 업무 간의 관계를 짐작할 수 도 있다.

업무 간의 관계는 서버 단의 비지니스 로직과 데이터 엑세스 로직을 분석하여 도출할 수 있다.
서비스 분할을 위해서 관심을 둘 만한 업무 간의 관계나 여러 업무에 걸친 트랜잭션은 비지니스 로직과 데이터 엑세스 로직에 있다.

소스코드 분석은 일종의 경향을 파악하는 것이 목적이므로 매우 정교할 필요는 없다.
이를 통해 서비스로 전환한 후에 API 호출이나 트랜잭션이 얼마나 발생할지 판단할 수 있다.

코드 레벨에서 참조하는 횟수는 실제 런타임에 참조하는 횟수와는 다르다는 점을 명심해야한다.
따라서 참조로 인해 서비스에 부하가 커질지를 감안하려면 실제 호출 빈도를 반영해서 판단해야 한다.

쓰기 참조 중에 다른 업무에 무분별하게 엑세스하는 것은 제외해야한다. 이 경우 업무 간의
쓰기 참조 수가 증가하므로 두 업무를 다른 서비스에 배치하면 구현 난도가 올라가게 된다.
원래도 다른 업무의 테이블에는 직접 데이터를 생성하지 않는 것이 바람직하다.

코드 간의 참조는 업무간의 관계를 일정 부분 대변하고 난도를 유추할 수 있지만, 이것만으로 `서비스를 도출하는 것은 적절하지 않다`.
첫째로 서비스는 단순히 업무만을 고려하여 선정하는 것이 아니고 담당 조직이나 `관리 부담`을 고려해야하기 떄문이다.
둘쨰로는 코드의 참조 관계가 반드시 `업무 간의 관계와 일치하지 않을 수` 도 있다. 참조 관계만으로
서비스를 선정하면 참조가 적은 서비스만 도출하게 된다.
마지막으로 시스템이 크고 복잡할 수록 여러 관계자가 모여 서비스를 선정하고 설계하는게 더 빠르고 정확할 수 있다.

---

## 4.5 서비스 선정

앞에서 식별한 업무를 조합하여 서비스로 선정해야한다. 앞서 도출한 업무들은 서비스를 구성하는 `최소한의 블록`이 된다.
서비스 갯수가 많아지면 관리가 어렵고 구현 부담이 늘어나므로 `조직과 시스템의 특성을 고려`하여 선택한다.

### 4.5.1 적절한 서비스 갯수

#### 이상적인 서비스 갯수

담당 인력 대비 서비스의 수가 너무 많아지면 그만큼 `관리 부담`이 증가하므로 시스템을 너무 잘게 나눌 필요는 없다.
또한 `서비스 간의 인터페이스` 또한 많아지므로 구현 난도가 높아진다.

일반적인 권장사항은 하나의 팀이 하나의 서비스를 전담하는 것이다. 팀이 독자적으로 개발하고
배포할 수 있도록 팀에 `독립적인 시스템을 제공하는 것이 목표`기에 업무의 수나 특성으로 서비스의 갯수를 정할 수는 없다.
코드를 적게 만들어 변경을 쉽게하고자 한다면 `시스템 내부에 강한 모듈화`를 고려하자.

따라서, `적절한 서비스 수는 팀의 수를 계산`해보면 대략적으로 짐작할 수 있다.
시스템 담당자를 적절한 인원으로 팀을 나누면 적절한 서비스 갯수를 예측해볼 수 있다.
실제 시스템의 담당 조직은 나누어질 수 있는 단위가 어느 정도 정해져있는 경우가 많으므로,
`담당 조직의 수는 서비스 수에 영향`을 미치며 대략적인 범위를 정하는 가이드가 된다.

#### 서비스 갯수를 제한하는 요소

- 구현 난도
  - 서비스 갯수가 많아지면 인터페이스도 늘어나고 구현 난도가 증가하는 경향이 있다
- 구현 기술 친숙도
  - MSA 구현 경험이 전혀 없고 사전 교육도 어려운 상황이라면 서비스 갯수를 줄이는 것이 좋다
- 시스템 유형
  - 민감한 트랜잭션이 많은 시스템은 서비스로 분리하는 것이 부담이 될 수 있다
  - 중요한 트랜잭션이 한 서비스 안에서 처리될 수 있도록 서비스의 수를 조금 줄이는 것도 좋은 선택이다
- 인프라 유형
  - 클라우드 같은 유연한 인프라를 사용할 수록 서비스 갯수 증가에 부담이 적다

### 4.5.2 업무를 조합하여 서비스 선정

#### 업무 분류 기준

업무를 하나의 서비스로 모으는 기준은 `업무 간의 유사성`이다.

#### 유형 1. 업무별 담당 부서에 따라 분류하기

이런 구조에서 가장 큰 장점은 업무팀의 변경 요청이 다른 업무팀의 요청이나 반영 일정에 간섭받지 않고 반영될 수 있다는 점이다.

하지만 항상 업무와 담당 부서가 매핑되지는 않는다. 하나의 부서가 여러 기능을 담당하는 경우도 있고,
여러 부서가 하나의 기능을 공동으로 담당하는 경우도 있다. 그럼에도 가능한 한 담당 부서의 변경 요청이 다른
담당 부서의 요청과 `경합하지 않도록 서비스를 배치`하는 것이 좋다.

#### 유형 2. 제품이나 시장별 담당 부서에 따라 분류하기

모든 제품은 기획, 설계, 개발, 검증 단계를 거쳐 개발되고 공통된 속성을 가진다.
따라서 기획 서비스, 설계 서비스, 개발 서비스, 검증 서비스로 분할이 가능하다.
이런 구조는 소스 코드 크기가 줄어들어 변경 및 배포가 쉬워질 수 도 있지만, 각 소스 코드에는 `제품의 고유 로직`을 처리하기 위한
많은 분기문이 그대로 존재하여 단위 업무의 복잡도는 그대로 유지될 가능성이 존재한다.

또한 여전히 `업무팀 간에는 병목`이 존재하게 된다. 요청한 기능 변경이 완료될 때 까지 기다려야 반영할 수 있는 문제가 존재한다.
이런 경우 `제품에 따라 서비스`를 나눠 리드 타임을 최소화 할 수 있다.

이 구조의 가장 큰 장점은 각 제품팀의 요청을 다른 제품팀의 간섭을 받지 않고 반영할 수 있다.
각 제품팀이 자신이 정의한 우선순위에 따라 기능을 제공할 수 있고, 담당하는 제품에 관련한 코드만
남길 수 있으므로 복잡도는 더 낮아진다.

#### 중복에 대한 우려

제품별로 서비스를 분리하는 경우 서비스에 중복된 코드가 늘어나는 단점이 생길 것이라고 생각할 수 있다.

- 업무를 기준으로 서비스를 분할하면 하나의 변경으로 여러 제품의 기능을 개선할 수 있지 않을까?
  - 규모가 크고 복잡한 시스템은 변경 영향을 최소화하고 변경 요청으로 인한 리스크를 최소화해야한다.
  - 따라서 다른 제품을 함께 변경하고 싶지 않을 상황이 더 많다.
- 전체 시스템 코드 양이 증가하여 개발 비용과 운영 비용이 상승하지 않을까?
  - MSA는 시스템 전체 운영 비용을 낮추려고 도입하는 것이 아니며` 더 빠르게 비지니스 요구 사항을 반영하는 것`이다.
- 통일된 프로세스나 규칙이 깨지는 것이 아닐까?
  - 구현 코드가 분리되었다고 하나의 표준이나 프로세스를 따르지 않는 것은 아니다

#### 유형 3. 담당 조직이나 다른 업무의 분리

일부 업무를 다른 회사나 해외 조직과 같이 `협업하기 어려운 곳이 담당`한다면, 해당 업무는 별도의 `서비스로 분리` 하는 것이 좋다.

#### 유형 4. 반영 프로세스가 남다른 업무의 분리

시스템 업무 중 더 `복잡한 반영 프로세스`를 가진 것이 존재할 수 있다. 예를들어, 변경 내역을
외부 기관에 허락받거나 사후에 신고해야하는 경우다. 해당 기능을 별도의 서비스로 분리하여 더 간편한 반영 프로세스를 따르게 할 수 있다.

#### 유형 5. 미션 크리티컬한 업무의 분리

크리티컬한 시스템의 `장애 영향도`를 줄이기 위해 MSA를 도입할 수 있다.
장애 영향도가 높은 중요 업무를 별도의 서비스로 분리하여 다른 업무 기능의 버그나 장애로 인한 영향을 최소화할 수 있다.

#### 유형 6. 사용자 트래픽이 크게 증가, 감소하는 업무의 분리

사용자 `트래픽이 크게 증가하고 감소`하는 업무가 있다면 별도의 서비스로 분리하는 것이 좋다.
필요에 따라 독자적으로 스케일 인, 아웃이 가능하다.

#### 안티패턴 1. 데이터 참조나 트랜잭션을 기준으로 서비스 분류하기

업무 간의 관계는 시스템의 참조 관걔와 비례하는 경향이 있지만 항상 그렇지는 않다.
구현 레벨의 참조 관걔를 우선해 서비스를 분리하면 구현이 쉬워지지만, `연관이 적은 업무`가 모여 서로 변경을 어렵게 할 수 있다.
서비스는 `업무 구분이나 업무 간의 관계를 우선적으로 고려`하여 분류해야한다.

#### 안티패턴 2. 기준 정보 관리 서비스

기준 정보 서비스는 시스템에서 동일한 기준으로 사용하는 데이터로, 해당 데이터를 서비스로 분리하는 것은 좋지 않다.

모놀리식 관점에선 여러 업무의 기준 정보를 통합해 별도의 업무로 분류해도 패널티가 없다.
하지만, MSA의 경우 서비스 간 `불필요한 API 통신이나 데이터 동기화`를 유발한다.

#### 4.5.3 시스템 기능을 제공하는 서비스

시스템에는 업무와 관련없이 순수한 시스템 관점의 기능이 존재하며, 이는 `다른 관점에서 서비스로 분류`될 수 있다.

대표적으로 로그인, 접근제어, 사용자 관리 같은 기능이 있다. 또한 배치 서비스 또한 그러한데,
`모놀리식 시스템에서는 일반 업무와 부하가 발생하는 타이밍과 패턴이 다르기 때문에 별도의 서버에서 실행`하는 경우가 많다.
`MSA의 경우` 공통의 배치 서비스를 만들어 공유하기에는 `각 서비스의 독립성`을 해칠 수 있다.

이 경우 `공통 배치 서비스는 스케쥴러 역할만`하고 `실제 배치 작업은 업무 서비스가 실행`하게 할 수 있다.
배치 스케쥴링과 관련된 기능을 공통으로 가져가고 배치 실행은 API를 요청받는 인스턴스에서 진행한다.

`인터페이스를 담당하는 서비스` 또한 시스템 업무와 관련이 없는 시스템 기능이다.
모놀리식 시스템에서는 외부 시스템과의 언터페이스가 많다면 `별도의 인터페이스 서버`를 두는 경우가 존재한다.
외부 시스템의 데이터를 배치로 가져와 가공하거나 주기적으로 동기화하기도 한다.
MSA에서도 외부 시스템과의 인터페이스를 담당하는 서비스가 필요할 수 있지만, `단순히 API 호출을 릴레이`하는 경우라면
인터페이스 서비스 없이 직접 호출하는 것이 좋다.

구체화된 뷰나 CQRS 패턴을 적용하여 릭기 전용 서비스 또한 추가될 수 있다. 이 경우 읽기 전용 서비스는
데이터 조회에 최적화된 구조로 개발하거나, 조회 확장성을 강화하는 경우가 많다.

마지막으로 리포트, 통계 등 다양한 업무 데이터를 종합해 만들어내는 경우가 존재한다.
일반적으로 `한 서비스에서 제공할 수 있는 데이터는 해당 서비스가 직접 담당`하고, `여러 서비스의 데이터를 조합하는 경우
이를 전담하는 별도의 서비스`를 두는 것이 좋다.



