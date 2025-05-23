# SummaryNote

## Java
- [Java Virtual Machine(JVM)](javanotes/JVM.md)
- [Garbage Collection](javanotes/GC.md)
- [Lambda expression](javanotes/Lambda.md)
- [Functional Programming](javanotes/FunctionalProgramming.md)
- [Stream](javanotes/Stream.md)

--- 
## Spring

### JPA
- [Persistence Context](JPA/Persistence.md)

--- 

## Network

### Network Basic

성공과 실패를 결정하는 1%의 네트워크 원리  
저자: Tustomu Tone  
출판사: 성안당

- Ch1
  - [Request Message](Network/network_basic/ch1/01_Request_Message.md)
  - [IP address and DNS](Network/network_basic/ch1/02_IP_address_and_DNS.md)
  - [Domain Name System(DNS)](Network/network_basic/ch1/03_DNS.md)
  - [Socket](Network/network_basic/ch1/04_Socket.md)
- Ch2
  - [Protocol Stack](Network/network_basic/ch2/01_Socket_and_protocol_stack.md)
  - [Connect to Server](Network/network_basic/ch2/02_Connect_to_server.md)
  - [Data Request](Network/network_basic/ch2/03_Data_Request.md)
  - [Disconnect](Network/network_basic/ch2/04_Disconnect.md)
  - [Packet](Network/network_basic/ch2/05_1_Packet.md)
- Ch3
  - [Signal](Network/network_basic/ch3/01_Signal.md)
  - [Switching Hub](Network/network_basic/ch3/02_Switching_Hub.md)
  - [Router](Network/network_basic/ch3/03_Router.md)

### Lecture note

모든 개발자를 위한 HTTP 웹 기본 지식  
강연자: 김영한   
출처: 인프런

- [Internet network](Network/lecture_note/01_Internet_network.md)
- [URI and Request](Network/lecture_note/02_URI_and_request.md)
- [HTTP](Network/lecture_note/03_http.md)
- [HTTP method](Network/lecture_note/04_http_method.md)
- [Status Code](Network/lecture_note/05_status_code.md)
- [HTTP header](Network/lecture_note/06_http_header.md)
- [HTTP header cache](Network/lecture_note/07_http_header_cache.md)


김영한의 실전 자바 - 고급 1편, 멀티스레드와 동시성
출처: 인프런

- [프로세스와 스레드](lecture/java-adv-1/docs/ch1.md)
- [스레드 생성과 실행](lecture/java-adv-1/docs/ch2.md)
- [스레드 제어와 생명주기](lecture/java-adv-1/docs/ch3.md)
- [메모리 가시성](lecture/java-adv-1/docs/ch4.md)
- [동기화](lecture/java-adv-1/docs/ch5.md)
- [고급 동기화](lecture/java-adv-1/docs/ch6.md)
- [생산자, 소비자 문제](lecture/java-adv-1/docs/ch7.md)
- [CAS(Compare And Swap, Compare And Set)](lecture/java-adv-1/docs/ch8.md)
- [동시성 컬렉션](lecture/java-adv-1/docs/ch9.md)
- [스레드 풀과 Executor 프레임워크](lecture/java-adv-1/docs/ch10.md)

### Others

- [REST API](Network/others/REST.md)
- [Cross Origin Resource Sharing(CORS)](Network/others/CORS.md)

---

## AWS

- [EC2](aws/ec2.md)
- [VPC](aws/vpc.md)
- [EBS](aws/ebs.md)
- [Load Balancer](aws/lb.md)

---

## Book

### 클린 아키텍쳐(Robert C. Martin)

- part 1
  - [1장. 설계와 아키텍쳐란?](book/CleanArchitecture/part1/ch1.md)
  - [2장. 두 가지 가치에 대한 이야기](book/CleanArchitecture/part1/ch2.md)
- part 2
  - [4장. 구조적 프로그래밍](book/CleanArchitecture/part2/ch4.md)
  - [5장. 객체 지향 프로그래밍](book/CleanArchitecture/part2/ch5.md)
  - [6장. 함수형 프로그래밍](book/CleanArchitecture/part2/ch6.md)
- part 3
  - [7장. 단일 책임 원칙(Single Responsibility Principle, SRP)](book/CleanArchitecture/part3/ch7.md)
  - [8장. 개방 폐쇄 원칙(Open Close Principle, OCP)](book/CleanArchitecture/part3/ch8.md)
  - [9장. 리스코프 치환 원칙(Liskov Substitution Principle, LSP)](book/CleanArchitecture/part3/ch9.md)
  - [10장. 인터페이스 분리 원칙(Interface Segregation Principle, ISP)](book/CleanArchitecture/part3/ch10.md)
  - [11장. 의존성 역전 원칙(Dependency Inversion Principle, DIP)](book/CleanArchitecture/part3/ch11.md)
- part 4
  - [12장. 컴포넌트](book/CleanArchitecture/part4/ch12.md)
  - [13장. 컴포넌트 응집도](book/CleanArchitecture/part4/ch12.md)
  - [14장. 컴포넌트 결합](book/CleanArchitecture/part4/ch12.md)
- part 5
  - [15장. Architecture(아키텍쳐)](book/CleanArchitecture/part5/ch15.md)
  - [16장. 독립성](book/CleanArchitecture/part5/ch16.md)
  - [17장. 경계: 선 긋기](book/CleanArchitecture/part5/ch17.md)
  - [18장. 경계 해부학](book/CleanArchitecture/part5/ch18.md)
  - [19장. 정책과 수준](book/CleanArchitecture/part5/ch19.md)
  - [20장. 업무 규칙](book/CleanArchitecture/part5/ch20.md)
  - [21장. 소리치는 아키텍쳐](book/CleanArchitecture/part5/ch21.md)
  - [22장. 클린 아키텍쳐](book/CleanArchitecture/part5/ch22.md)
  - [23장. 프레젠터와 험블객체](book/CleanArchitecture/part5/ch23.md)
  - [24장. 부분적 경계](book/CleanArchitecture/part5/ch24.md)
  - [25장. 계층과 경계](book/CleanArchitecture/part5/ch25.md)
  - [26장. 메인 컴포넌트](book/CleanArchitecture/part5/ch26.md)
  - [27장. 크고 작은 모든 서비스들](book/CleanArchitecture/part5/ch27.md)
  - [28장. 테스트 경계](book/CleanArchitecture/part5/ch28.md)
- part 6
  - [30장. 데이터베이스는 세부사항이다.](book/CleanArchitecture/part6/ch30.md)
  - [31장. 웹은 세부사항이다](book/CleanArchitecture/part6/ch31.md)
  - [32장. 프레임워크는 세부사항이다.](book/CleanArchitecture/part6/ch32.md)
  - [33장. 사례 연구](book/CleanArchitecture/part6/ch33.md)
  - [34장. 빠져 있는 장](book/CleanArchitecture/part6/ch34.md)
  
### 만들면서 배우는 클린아키텍쳐(Tom Hombergs)

- [Intro](book/BuildCleanArchitecture/doc/intro.md)
- [1장. 계층형 아키텍쳐의 문제는 무엇일까?](book/BuildCleanArchitecture/doc/ch1.md)
- [2장. 의존성 역전하기](book/BuildCleanArchitecture/doc/ch2.md)
- [3장. 코드 구성하기](book/BuildCleanArchitecture/doc/ch3.md)
- [4장. 유스케이스 구현하기](book/BuildCleanArchitecture/doc/ch4.md)
- [5장. 웹 어댑터 구현하기](book/BuildCleanArchitecture/doc/ch5.md)
- [6장. 영속성 어댑터 구현하기](book/BuildCleanArchitecture/doc/ch6.md)
- [7장. 아키텍쳐 요소 테스트하기(실습)](book/BuildCleanArchitecture/doc/ch7.md)
- [8장. 경계 간 매핑하기](book/BuildCleanArchitecture/doc/ch8.md)
- [9장. 어플리케이션 조립하기](book/BuildCleanArchitecture/doc/ch9.md)
- [10장. 아키텍쳐 경계 강제하기](book/BuildCleanArchitecture/doc/ch10.md)
- [11장. 의식적으로 지름길 이용하기](book/BuildCleanArchitecture/doc/ch11.md)
- [12장. 아키텍쳐 스타일 결정하기](book/BuildCleanArchitecture/doc/ch12.md)

### 도메인 주도 개발 시작하기(최범균)

- [Intro](book/DDDStart/intro.md)
- [1장. 도메인 모델 시작하기](book/DDDStart/ch1.md)
- [2장. 아키텍쳐 개요](book/DDDStart/ch2.md)
- [3장. 애그리거트](book/DDDStart/ch3.md)
- [4,5,6 장] JPA와 관련된 기능 위주로 설명되어 생략
- [6장. 응용 서비스와 표현 영역](book/DDDStart/ch6.md)
- [7장. 도메인 서비스](book/DDDStart/ch7.md)
- [8장. 애그리거트 트랜잭션 관리](book/DDDStart/ch8.md)
- [9장. 도메인 모델과 바운디드 컨텍스트](book/DDDStart/ch9.md)
- [10장. 이벤트](book/DDDStart/ch10.md)
- [11장. CQRS](book/DDDStart/ch11.md)

### Modern Java in Action

- [Intro](book/ModernJavaInAction/intro.md)
- part 1 - 기초
  - [1장. 자바 8, 9, 10, 11: 무슨 일이 일어나고 있는가?](book/ModernJavaInAction/part1/ch1.md)
  - [2장. 동작 파라미터화 코드 전달하기](book/ModernJavaInAction/part1/ch2.md)
  - [3장. 람다 표현식](book/ModernJavaInAction/part1/ch3.md)
- part 2 - 함수형 데이터 처리
  - [4장. 스트림 소개](book/ModernJavaInAction/part2/ch4.md)
  - [5장. 스트림 활용](book/ModernJavaInAction/part2/ch5.md)
  - [6장. 스트림으로 데이터 수집](book/ModernJavaInAction/part2/ch6.md)
  - [7장. 병렬 데이터 처리와 성능](book/ModernJavaInAction/part2/ch7.md)
- part 3 - 스트림과 람다를 이용한 효과적 프로그래밍
  - [8장. 컬렉션 API 개선](book/ModernJavaInAction/part3/ch8.md)
  - [9장. 리팩토링, 테스팅, 디버깅](book/ModernJavaInAction/part3/ch9.md)
- part 4 - 매일 자바와 함께
  - [11장. null 대신 Optional 클래스](book/ModernJavaInAction/part4/ch11.md)
  - [12장. 새로운 날짜와 시간 API](book/ModernJavaInAction/part4/ch12.md)
  - [13장. 디폴트 메서드](book/ModernJavaInAction/part4/ch13.md)
- part 5 - 개선된 자바 동시성
  - [15장. CompletableFuture와 리액티브 프로그래밍 컨셉의 기초](book/ModernJavaInAction/part5/ch15.md)
  - [16장. CompletableFuture: 안정적 비동기 프로그래밍](book/ModernJavaInAction/part5/ch16.md)
  - [17장. 리액티브 프로그래밍](book/ModernJavaInAction/part5/ch17.md)
- part 6 - 함수형 프로그래밍과 자바 진화의 미래
  - [18장. 함수형 관점으로 생각하기](book/ModernJavaInAction/part6/ch18.md)
  - [21장. 결론 그리고 자바의 미래](book/ModernJavaInAction/part6/ch21.md)

### 가상 면접 사례로 배우는 대규모 시스템 설계 기초(Alex Xu)

- [1장. 사용자 수에 따른 규모 확장성](book/SystemDesignInterview/ch1.md)
- [2장. 개략적인 규모 추정](book/SystemDesignInterview/ch2.md)
- [3장. 시스템 설계 면접 공략법](book/SystemDesignInterview/ch3.md)
- [4장. 처리율 제한 장치의 설계](book/SystemDesignInterview/ch4.md)
- [5장. 안정 해시 설계](book/SystemDesignInterview/ch5.md)
- [6장. 키-값 저장소 설계](book/SystemDesignInterview/ch6.md)
- [7장. 분산 시스템을 위한 유일 ID 생성기 설계](book/SystemDesignInterview/ch7.md)
- [8장. URL 단축기 설계](book/SystemDesignInterview/ch8.md)
- [10장. 알림 시스템 설계](book/SystemDesignInterview/ch10.md)
- [11장. 뉴스 피드 시스템 설계](book/SystemDesignInterview/ch11.md)
- [12장. 채팅 시스템 설계](book/SystemDesignInterview/ch12.md)
- [13장. 검색어 자동완성 시스템](book/SystemDesignInterview/ch13.md)

### 좋은 코드, 나쁜 코드(Tom Long)

- [1장. 코드 품질](book/GoodCodeBadCode/ch1.md)
- [2장. 추상화 계층](book/GoodCodeBadCode/ch2.md)
- [3장. 다른 개발자와 코드 계약](book/GoodCodeBadCode/ch3.md)
- [4장. 오류](book/GoodCodeBadCode/ch4.md)
- [5장. 가독성 높은 코드를 작성하라](book/GoodCodeBadCode/ch5.md)
- [6장. 예측 가능한 코드를 작성하라](book/GoodCodeBadCode/ch6.md)
- [7장. 코드를 오용하기 어렵게 만들라](book/GoodCodeBadCode/ch7.md)
- [8장. 코드를 모듈화하라](book/GoodCodeBadCode/ch8.md)
- [9장. 코드를 재사용하고 일반화할 수 있도록 하라](book/GoodCodeBadCode/ch9.md)
- [10장. 단위 테스트의 원칙](book/GoodCodeBadCode/ch10.md)
- [11장. 단위 테스트의 실제](book/GoodCodeBadCode/ch11.md)

### 마이크로서비스 아키텍쳐 구축 가이드(김용욱)

- [Intro](book/MSAGuide/intro.md)
- Part 1. 마이크로서비스 아키텍쳐 이해하기
  - [1장. 마이크로서비스 아키텍쳐란?](book/MSAGuide/ch1.md)
  - [2장. 어떻게 적용해야 할까?](book/MSAGuide/ch2.md)
  - [3장. 데이터베이스를 분리한다고?](book/MSAGuide/ch3.md)
- Part 2. 마이크로서비스 아키텍쳐 적용하기
  - [4장. 서비스 선정하기](book/MSAGuide/ch4.md)
  - [5장. 서비스 설계 원칙](book/MSAGuide/ch5.md)

### 스프링으로 시작하는 리액티브 프로그래밍(황정식)

- Part1. 리액티브 프로그래밍
  - [Ch1. 리액티브 시스템과 리액티브 프로그래밍](book/ReactiveWithSpring/part1/ch1.md)
  - [Ch2. 리액티브 스트림즈(Reactive Streams)](book/ReactiveWithSpring/part1/ch2.md)
  - [Ch 3. Blocking I/O와 Non-Blocking I/O](book/ReactiveWithSpring/part1/ch3.md)
  - [Ch 4. 리액티브 프로그래밍을 위한 사전 지식](book/ReactiveWithSpring/part1/ch4.md)
- Part2. Project Reactor
  - [Ch 5. Reactor 개요](book/ReactiveWithSpring/part2/ch5-7.md)
  - [Ch 6. 마블 다이어그램](book/ReactiveWithSpring/part2/ch5-7.md)
  - [Ch 7. Cold Sequence와 Hot Sequence](book/ReactiveWithSpring/part2/ch5-7.md)
  - [Ch 8. Backpressure](book/ReactiveWithSpring/part2/ch8.md)
  - [Ch 9. Sinks](book/ReactiveWithSpring/part2/ch9.md)
  - [Ch 10. Scheduler](book/ReactiveWithSpring/part2/ch10.md)
  - [Chapter 11. Context](book/ReactiveWithSpring/part2/ch11.md)
  - [Chapter 12. Debugging](book/ReactiveWithSpring/part2/ch12.md)
  - [Chapter 13. Testing](book/ReactiveWithSpring/part2/ch13.md)
  - [Chapter 14. Operator](book/ReactiveWithSpring/part2/ch14.md)

---
## Etc

### Git
  - [git basic](etc/Git/git_basic.md)
  - [git merge](etc/Git/merge_method.md)

### Database
  - [RDBMS Modeling](etc/database/RDBMS_Modeling.md) 
  - [분산 데이터 베이스 매니징 시스템](etc/database/Distributed-Database-Manger-System.md)

### Open-source
- [Apache Calcite Core](etc/ApacheCalcite/core.md)

### INFCON 2022
- [Stack Changes](etc/Infcon2022/StackChanges.md)
- [나도 내 코드의 문제를 찾고 싶다고요](etc/Infcon2022/FindProblem.md)
- [레거시 개편의 기술](etc/Infcon2022/Legacy.md)

### Regular Expression
- [Regular Expression](etc/RegEx/RegularExpression.md)

### Coding Test

- [Intro](etc/CodingTest/intro/intro.md)

### MySQL 성능 최적화 입문/실전

- [Intro](etc/BasicQueryOpt/intro.md)
- [Index](etc/BasicQueryOpt/index.md)
- [Explain](etc/BasicQueryOpt/explain.md)
- [SQL 최적화 연습](etc/BasicQueryOpt/practice.md)
- [실전 SQL 최적화](etc/BasicQueryOpt/work.md)