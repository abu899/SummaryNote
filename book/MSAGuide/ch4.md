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