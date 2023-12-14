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