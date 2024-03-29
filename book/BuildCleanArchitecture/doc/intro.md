# 만들면서 배우는 클린 아키텍쳐(Get Your Hands Dirty on Clean Architecture_ Build 'clean' applications)
# 저자 : 톰 홈버그

## 역자 서문

클린 코드와 클린 아키텍쳐 이론은 어느정도 알지만 어떻게 적용할지 모르는 상황에서 이책이 해답이 될 수 있을까?
아쉽게도 이 책은 정답은 될 수 없다. 실제 실무에서는 책 내용보다 더 복잡하고 다양한 상황이 등장하기 때문이다.

하지만, 이책을 통해 클래스 간의 의존관계는 어느정도로 허용하고, 패키지 레벨은 어떻게 나눠야할까에 대한 고민에
대한 힌트를 얻을 수 있을 것이다.

## 추천사(객체지향의 사실과 오해 - 조영호)

육각형 아키텍쳐에서 어플리케이션은 `비지니스 관심사를 다루는 내부`와 `기술적인 관심사를 다루는 외부`로 나뉜다.
외부에 포함된 `기술적인 컴포넌트는 어댑터(adapter)`, `어댑터가 내부와 상호작용하는 접점은 포트(port)`이다.
따라서 육각형 아키텍쳐 패턴을 포트와 어댑터 패턴이라고도 부른다.

에릭 에반스의 도메인 주도 설계라는 서적에서는 다음과 같은 말이 있다.
> 도메인 로직이프로그램상의 다른 관심사와 섞여 있으면 그와 같은 대응을 달성하기가 수월하지 않다.
> 따라서, 도메인 주도 설계의 전제 조건은 도메인 구현을 격리하는 것이다.

도메인을 기반으로 어플리케이션을 구축하기 위해서는 육각형 아키텍쳐 처럼 `경계와 의존성을 강제할 수 있는 아키텍쳐`를
채택하는 것이 중요하다는 사실을 깨달았다.

이 책을 읽음으로써, 실무에 적용할 때 고려해야하는 상황을 깨달을 수 있었다.
어떻게 아키텍쳐를 개선할지, DDD를 위해 필요한 아키텍쳐를 어떻게 구현할지, 클린 아키텍쳐의 실체가
궁금하다면 이 책을 읽어보자.

## 참고사항

해당 서적의 이론적인 내용을 정리하고, 실습의 경우 실습코드를 보며 서적을 이해하는 방향으로
진행하기에 실습 챕터에서는 추가적으로 `실습`을 붙인다. 추후 내용을 다시 볼 때 참고하자.

## Reference

[Get Your Hands Dirty on Clean Architecture Github](https://github.com/thombergs/buckpal)