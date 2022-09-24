# HTTP 리퀘스트 메세지를 작성한다

## URL(Unifrom Resource Locator)

- 우리가 흔히 사용하는 "http://"는 http라는 프로토콜을 사용해 접근하겠다는 표현.
- http 이외에 ftp, file, mailto 또한 사용될 수 있으며 액세스할 때의 프로토콜 종류를 표현.
- 프로토콜에 따라 뒤에 resource들의 작성 방식이 달라질 수 있음.

## 브라우저는 먼저 URL을 해독한다

- 웹서버에 Request message를 작성하기 위해 URL을 해독.
  ![URL-IMG](img/url.png)
- 위와 같은 형태로 URL이 존재하고 웹서버명 뒤에는 디렉토리와 파일이름이 붙음.
ex) <http://www.example.co.kr/dir/file1.html>
- 예시와 같은 URL은 www.example.co.kr의 주소의 dir안에 file1.html에 접근하겠다 라는 의미.
- 파일명이 없이 directory만 있는 경우 index.html 또는 default.htm이라는 파일명으로 기본 접근파일을 생성해둠.

## HTTP의 기본 개념

### Client가 Server를 향해 Request message를 보냄

- Request message에는 '무엇을', '어떻게 해서' 하겠다는 내용이 담겨 있음.
- '무엇을'에 해당하는 부분이 ***URI(Uniform Resource Identifier)***.
- ***URI***는 페이지 데이터를 저장한 파일의 이름이나 cgi 프로그램 파일명이 될 수 있음.
- 하지만 URI는 URL 그 자체가 될 수도 있으며(포워드 프록시),
그만큼 다양한 액세스 대상을 통칭하는 말이 URI.
- '어떻게 해서'에 해당하는 것이 Method로 흔히 아는 ***GET, POST, PUT, DELETE***등이 이에 해당.

  > Request message를 URL에서 만들게 되는데 이 내용은 p45, HTTP 리퀘스트 메시지를 만든다 부터 확인.

### Request message가 Server에 도착

- Request message가 서버에 도착하면 그 내용을 해독하고 URI와 메시지를 조사하여 요구에 따라 동작.
- 결과 데이터를 Response message에 저장.
- Response message에는 실행 결과의 정상 종료여부를 나타는 ***Status code***를 포함.  
ex) 404 Not Found

### Sever가 Client를 향해 Response message를 보냄

- Response message를 Client에 반송하고,
Client는 ***message안의 데이터를 추출하여 화면에 표시***하는 것이 HTTP의 동작