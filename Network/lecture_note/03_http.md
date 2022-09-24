# HyperText Transfer Protocol(HTTP)

현재는 모든것을 HTTP 메시지에 담아서 보낸다

- HTML, txt
- image, 음성, 영상, 파일 등
- 거의 모든 형태의 데이터 전송 가능
- HTTP/1.1
    - 가장 많이 사용하는 버젼이고 거의 모든 기능이 포함되어 있다
    - 이후 2, 3는 성능개선에 초점이 맞춰져있다

## 특징

### 클라이언트 서버 구조

- Request Response 구조
- 클라이언트는 서버에 **요청**을 보내고 응답을 기다린다
- 서버는 요청에 대한 결과에 대해 **응답**한다
- 클라이언트와 서버를 개념적으로 분리하고
    - 서버는 비지니스 로직이나 데이터를 담당하고
    - 클라이언트는 사용성과 UI를 그리는데 집중
    - 각각의 기능에 대해 독립적으로 발전시킬 수 있는 장점이 생겼다.

### Stateless protocol

- 서버가 클라이언트의 이전 상태를 보존하지 않는 다는 의미.
- 반대로 Stateful은 서버가 클라이언트의 이전 상태를 계속 가지고 있어야한다
- 즉, 서버가 달라지더라도 클라이언트의 요청을 받는데 문제가 없기 때문에 응답서버를 쉽게 바꿀 수 있다(무한 서버 증설 가능)
- 수평 확장(Scale-out)에 유리하다

- 실무에서의 한계점
  - 모든것을 무상태로 유지할 수 있는 경우가 있고 아닌 경우가 존재
  - ex) 로그인의 경우 상태를 유지해야한다
  - 일반적으로 브라우저 쿠키와 서버 세션등을 사용해서 상태 유지를 해야한다
  - 상태 유지는 최소한만 사용해야한다
  - 클라이언트에서 서버로 데이터를 무상태보다 많이 보내야한다

### 비연결성

- HTTP는 연결을 유지하지 않는 모델
- 클라이언트가 요청을 하고 서버가 응답받은 후 연결을 끊는다
- 서버는 연결을 유지할 필요가 없으므로 사용하는 자원을 최소한으로 유지할 수 있다
- 서버 자원을 효울적으로 사용 가능!  
- 한계점
  - TCP/IP 3 way handshake가 항상 추가된다
  - HTML 뿐만 아니라 이미지, css 등 수많은 자원이 같이 다운로드 된다
  - 현재는 HTTP 지속연결(Persistent Connections)로 문제 해결

### HTTP 메시지

#### HTTP 메시지 구조
```text
Start-line 시작라인
header 헤더
empty line 공백 라인(CRLF)
message body
```

#### Request Message
```text
GET /search?q=hello&hl=ko HTTP/1.1  -> start-line
Host:www.google.com                 -> header
                                    -> empty line
```
- start line (Request message)
  - request-line = method(공백)request-target(공백)HTTP-version(CRLF)
  - method
    - GET, POST, PUT, DELETE
    - 서버가 수행해야 할 동작 지정
  - request-target
    - 요청 대상
    - absolute-path[?query]
  - HTTP version
- header
  - field name":"(OWS)field-value(OWS) -> OWS: 띄어쓰기 허용
  - field name은 대소문자 구분이 없다
  - HTTP 전송에 필요한 부가정보가 모두 들어 있다
- message body
  - 실제 전송할 데이터
  - HTML 문서, 이미지, 영상 등 byte로 표현할 수 있는 모든 데이터
#### HTTP Response message
```text
HTTP/1.1 200 OK                     -> start-line
Content-Type:text/html;charset=UTF-8-> header
Content-Length:3423                 -> header
                                    -> empty line
<html>                              -> message body
    <body>...</body>                -> message body
</html>                             -> message body
```
- start line (Response message)
  - status-line = HTTP-version(공백)status-code(공백)reason-phrase(CRLF)
  - status-code
    - 200(성공), 400(client error), 500(server error)
  - reason phrase
    - 사람이 이해할 수 있는 status-code에 대한 글

### 단순하고 확장 가능함

