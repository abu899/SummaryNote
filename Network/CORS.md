# Cross Origin Resource Sharing (CORS) 

## Intro

교차 출처 리소스 공유라는 이름으로 번역하며, 하나의 `출처(Origin)`에서 실행 중인 웹 어플리케이션이 `다른 출처의 선택한 자원에 접근할 수 있는 권한`을 
부여하도록 브라우저에 알려주는 체제이다. `CORS`라는 정책이 있기에 브라우저와 서버 간의 안전한 요청 및 데이터 전송을 지원할 수 있다.

## Origin

<p align="center"><img src="./img/cors_1.png" width="80%"></p>

`CORS`의 `Origin`은 Protocol, Host, Port 를 모두 합친 것을 의미한다.
`HTTP`나 `HTTPS` 같이 기본 포트번호가 정해진 경우가 있기에 포트번호는 생략이 가능하지만,
만약 `Origin`에 포트번호가 명시되어 있다면, 명시된 포트번호까지 일치해야 같은 출처로 인정된다.

### Same Origin vs Different Origin

URL : `http://store.company.com/dir/page.html`

| URL                                             |   Outcome    |                     Reason                     |
|:------------------------------------------------|:------------:|:----------------------------------------------:|
| http://store.company.com/dir2/other.html        | Same origin  |             Only the path differs              |
| http://store.company.com/dir/inner/another.html | Same origin	 |             Only the path differs              |                
| https://store.company.com/page.html             |   Failure   |               Different protocol               |
| http://store.company.com:81/dir/page.html	      |   Failure	   | Different port (http:// is port 80 by default) |
| http://news.company.com/dir/page.html	          |   Failure	   |                 Different host                 |                                     

### Same Origin Policy (SOP)

같은 출처(Origin)에서만 리소스를 공유할 수 있다는 규칙이다.
앞서 말했듯이, 두개의 출처를 비교하는 방법은 URL의 구성요소 중 `Protocol`, `Host`, `Port` 세가지가 동일한지 확인하면 된다.
우리가 Postman이나 브라우저를 통해 서버간 통신을 할 때는 이 정책이 적용되지 않지만, 브라우저를 통해 통신하는 경우 이를 위반하는 오류가 발생한다.
그 이유는 출처를 비교한느 로직은 `브라우저에 구현`되어 있기 때문이다.  

`SOP`는 Cross-Site Request Forgery(CSRF)나 Cross-Site Scripting(XSS)등의 보안 취약점을 노린 공격을 방어할 수 있다.
하지만 현실적으로, 다른 출처에 있는 리소스를 가져오는 일은 굉장히 흔한 일이기 때문에, `SOP`만으로는 웹 어플리케이션을 구성하기는 불가능하다.
따라서, 예외 조항을 두고 이 조항에 해당하는 리소스 요청은 출처가 다르더라도 허용하기로 했는데 이것이 `CORS`이다.

```text
실무에서는 프론트엔드와 백엔드로 나눠서 서버가 존재하는 경우가 다수 존재한다.
따라서 백엔드 서버에 연동하여 API를 호출하게 되는데, 두 개의 서버가 다르기에 URL이 달라지게 되므로 `SOP`에 어긋나게된다.
```

## CORS

`CORS`는 기본적으로 HTTP 프로토콜의 `Origin`과 `Access-Control-Allow-Origin`헤더를 통해 동작하게된다. 순서를 보면 다음과 같다.

1. 클라이언트는 `Request Header`내 `Origin`에 요청을 보내는 곳의 주소를 담아 보내게 된다.
2. 서버에서는 `Response Header`내 `Access-Control-Allow-Origin`헤더에 허락하는 `Origin`을 담아 클라이언트로 보낸다
3. 클라이언트에서 자신이 보낸 `Origin`과 서버가 보내준 `Access-Control-Allow-Origin`을 비교한다
   - 유효하면 응답을 사용하고, 유효하지 않다면 응답을 사용하지 않고 버린다


## Reference

- https://developer.mozilla.org/ko/docs/Web/HTTP/CORS 
- https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F
- https://velog.io/@hoo00nn/CORSCross-Origin-Resource-Sharing-%EB%9E%80
- https://evan-moon.github.io/2020/05/21/about-cors/
- https://juneyr.dev/2018-12-23/cors