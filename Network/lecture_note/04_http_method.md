# HTTP Method

## HTTP API
```text
회원 목록 조회:   /read-member-list
회원 조회:      /read-member-by-id
회원 등록:      /create-member
회원 수정:      /update-member
회원 삭제:      /delete-member   
```
가장 중요한 것은 리소스 식별이다
- 회원을 조회하고 삭제하고 하는 것 자체가 리소스가 아니다
- 회원 자체가 리소스라는 점을 인지해야한다

```text
URI 계층 구조 활용  

회원 목록 조회:   /members
회원 조회:      /members/{id}
회원 등록:      /members/{id}
회원 수정:      /members/{id}
회원 삭제:      /members/{id}
```
리소스와 행위를 분리
- URI는 리소스만 식별
- 해당 리소스를 대상으로하는 행위를 분리하자
  - 리소스 : 회원
  - 행위 : 조회, 등록, 삭제, 변경
- 리소스는 명사, 행위는 동사

## HTTP METHOD

- GET
  - 리소스 조회
  - 서버에 전달하고자 하는 데이터는 query(parameter, string)으로 전달
  - 메시지 바디를 통해 데이터를 전달할 수 있지만, 지원하는 곳이 많지않아 권장하지 않는다
- POST
  - 요청 데이터 처리
  - message body를 통해 **서버로 요청 데이터를 전달**
  - 서버는 요청 데이터를 **처리**
    - message body를 통해 들어오는 데이터를 처리하는 모든 기능 수행
  - 즉, 리소스 URI에 POST 요청에 대해, 요청 데이터를 어떻게 처리할지 리소스마다 정해야한다
    - 새 리소스 생성(등록)
      - 서버가 아직 식별하지 않은 새 리소스 생성
    - 요청 데이터 처리
      - 데이터 생성을 넘어서 어떤 프로세스를 처리해야하는 경우
      - POST /orders/{orderID}/start-delivery (컨트롤 URI)
      - 결과에 따라 새로운 리소스가 생성되지 않을 수 있다
    - 다른 메서드로 처리하기 애매한 경우
- PUT
  - 리소스를 **완전히 대체**
    - 리소스가 있으면 삭제하고 대체 없으면 생성
    - **덮어쓰기**이기 때문에 부분적으로 업데이트하는게 아니다!!! 
  - **클라이언트가 리소스를 식별**하는점이 중요하다
    - 클라이언트가 리소스 위치를 알고 URI를 구체적으로 지정
    - ex) PUT **/members/100(정확한 URI)** HTTP/1.1
    - POST와의 차이점
- PATCH
  - 리소스 **부분 변경**
- DELETE
  - 리소스 제거
  - PUT, PATCH와 마찬가지로 리소스의 위치를 알고 URI를 구체적으로 지정

### HTTP METHOD의 속성

- 안전(Safe)
  - 호출해도 리소스를 변경하지 않는다.
  - 즉, 대상 리소스가 변경되지 않는 속성인가
  - GET은 조회만 하기에 안전
  - POST, PUT, PATCH등은 변경이되기에 안전한 속성이 아님
- 멱등(Idempotent)
  - 한 번 호출하든 두 번 호출하든 100번 호출하든 결과가 똑같다
    - GET은 조회만이기에 멱등 속성임!
    - PUT은 항상 같은 데이터를 몇번을 보내도 같은 데이터로 덮어쓰기에 멱등.
    - DELETE 또한 계속 삭제를 요청해도 삭제된 결과는 똑같기에 멱등.
    - 하지만, POST는 같은 요청을 여러번하면 중복해서 발생되기에 멱등하지 않다!
  - 활용
    - 자동 복구 메커니즘
      - 똑같은 요청을 여러번 시도해도 되기에 확인을 위해 멱등을 이용
  - 멱등은 외부 요인(다른 사용자가 중간에 데이터를 변경하는 등)으로 인해 **중간에 리소스가 변경되는 것**은 고려하지 않는다
- 캐시가능(Cacheable)
  - 응답 결과 리소스를 캐시해서 사용해도 되는가
  - GET, HEAD, POST, PATCH 캐시 가능(스펙상)
    - 하지만, 실제로는 GET, HEAD 정도만 캐시로 사용한다
    - 캐싱을 하려면 key가 일치해야하는데, POST와 PATCH 같은 경우는 message body의 내용을 고려해야하기에 복잡해서 구현이 어려움

## HTTP API 설계 예시

### POST 기반 등록

#### 회원 관리 시스템
```text
회원 목록:      /members        -> GET
회원 조회:      /members/{id}   -> GET
회원 등록:      /members        -> POST
회원 수정:      /members/{id}   -> PATCH, PUT, POST
회원 삭제:      /members/{id}   -> DELETE
```
- 클라이언트가 등록될 **리소스의 URI를 모른다**
  - POST /members
- 서버가 새로운 리소스 URI를 생성해준다
```text
HTTP/1.1 201 Created
Location: /members/100
```
- 컬렉션(Collection)
  - 서버가 관리하는 리소스 디렉토리
  - 서버가 리소스의 URI를 생성하고 관리하는 것
  - 여기서 컬렉션은 /members
  - 대부분 컬렉션을 사용

### PUT 기반 등록

#### 파일 관리 시스템

```text
파일 목록:          /files              -> GET
파일 조회:          /files/{filename}   -> GET
파일 등록:          /files/{filename}   -> PUT
파일 삭제:          /files/{filename}   -> DELETE
파일 대량 등록:      /files              -> POST
```
- 클라이언트가 등록될 **리소스의 URI를 알고 있어야 한다**
  - PUT /files/{filename}
- 클라이언트가 직접 리소스의 URI를 지정한다
- 스토어(Store)
  - 클라이언트가 관리하는 자원 저장소
  - 클라이언트가 리소스의 URI를 알고 관리한다
  - 여기서 스토어는 /files

## HTML FORM 사용

```text
회원 목록:           /members                           -> GET
회원 등록 폼:        /members/new                       -> GET
회원 등록:           /members/new, /members             -> POST
회원 조회:           /members/{id}                      -> GET
회원 수정 폼:        /members/{id}/edit                 -> GET
회원 수정:           /members/{id}/edit, /members/{id}  -> POST
회원 삭제:           /members/{id}/delete               -> POST
```

- GET, POST만 지원한다
  - GET, POST만 지원하기에 제약이 존재한다
- 컨트롤 URI
  - 제약을 해결하기 위해 동사로 된 리소스 경로를 사용한다
  - POST의 /new, /edit, /delete가 컨트롤 URI
  - HTTP 메서드로 해결하기 애매한 경우에 사용한다
  - 최대한 리소스를 가지고 URI를 설계해야하고 대체로 컨트롤 URI를 사용하자
  - 그러나 실무에서는 생각보다 많이 사용됨...

## 참고하면 좋은 URI 설계 개념
ref : https://restfulapi.net/resource-naming
- 문서(document), 정적 리소스
  - 단일 개념(파일하나, 객체 인스턴스, 데이터베이스 row)
  - /members/100, files/star.jpg
- 컬렉션(collection)
  - 서버가 관리하는 리소스 디렉터리
  - 서버가 리소스의 URI를 생성하고 관리
  - /members
- 스토어(store)
  - 클라이언트가 관리하는 자원 저장소
  - 클라이언트가 리소스의 URI를 생성하고 관리
  - /files
- 컨트롤러(controller), 컨트롤 URI
  - 문서, 컬렉션, 스토어로 관리하기 어려운 추가 프로세스
  - 동사를 직접 사용
  - /members/{id}/delete