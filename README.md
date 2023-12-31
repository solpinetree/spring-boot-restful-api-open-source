<a name="readme-top"></a>


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">게시판 관리 RESTful API 오픈 소스</h3>

  <p align="center">
<!--     <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a> -->
  </p>
</div>

<br/>

<!-- TABLE OF CONTENTS -->
  <ol>
    <li>
      <a href="#기능">기능</a>
    </li>
    <li>
      <a href="#애플리케이션-실행-방법">애플리케이션 실행 방법</a>
    </li>
    <li>
      <a href="#데이터베이스-테이블-구조">데이터베이스 테이블 구조</a>
    </li>
    <li>
      <a href="#데모-영상">데모 영상</a>
    </li>
    <li>
      <a href="#구현-방법">구현 방법</a>
    </li>
     <li>
      <a href="#api-명세">API 명세</a>
    </li>
  </ol>

&nbsp;
&nbsp;
&nbsp;
&nbsp;


<!-- ABOUT THE PROJECT -->
## 기능

#### 1. 사용자 회원가입
- 이메일과 비밀번호로 회원가입
- 유효성 검사
  - 이메일 조건: @ 포함
  - 비밀번호 조건: 8자 이상
  - 비밀번호는 암호화하여 저장

#### 2. 사용자 로그인
- 이메일과 비밀번호로 로그인 후, JWT 반환

#### 3. 게시글 생성
#### 4. 게시글 목록 조회
- pagination 기능 구현

#### 5. 특정 게시글 조회
#### 6. 특정 게시글 수정
#### 7. 특정 게시글 삭제
#### 8. 단위 테스트 



<p align="right">(<a href="#readme-top">back to top</a>)</p>

&nbsp;
&nbsp;
&nbsp;
&nbsp;


## 애플리케이션 실행 방법
### deploy
- 도커 환경. 프로젝트 루트 경로에서
> docker-compose up --build -d

실행해주세요.
spring boot와 mysql 을 각각 도커 컨테이너로 실행되도록 구성했습니다.

&nbsp;



<p align="right">(<a href="#readme-top">back to top</a>)</p>

&nbsp;
&nbsp;
&nbsp;
&nbsp;

## 데이터베이스 테이블 구조
![img.png](img.png)

user, post를 1:n 관계로 매핑했습니다.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

&nbsp;
&nbsp;
&nbsp;
&nbsp;

## 데모 영상
### 회원가입
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/8e1a8594-f218-49f3-b559-2af2584afa7c

### 로그인
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/9f712f5a-76bd-4ea4-9c57-75a7e332e2a4

### 게시글 작성
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/7a14ca49-b755-404b-83e3-e5255b061346

### 게시글 목록 조회
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/03af435d-0f91-41f1-91c5-959505fcab9d

### 게시글 상세 조회
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/11a36501-a099-4e9c-984b-50f7cbff4436

### 게시글 수정
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/b222a6ba-4f7b-4501-a8ab-2e089fe5e30e

### 게시글 삭제
https://github.com/solpinetree/wanted-pre-onboarding-backend/assets/83967710/c88e4885-4481-4a51-bd0a-6678737f3983



<p align="right">(<a href="#readme-top">back to top</a>)</p>

&nbsp;
&nbsp;
&nbsp;
&nbsp;

## 구현 방법
### JWT 생성 
Bearer 헤더로 jwt 를 받아서 인증을 합니다. SecurityFilter 에 따로 정의한 JwtTokenFilter를 추가해서 JwtTokenFilter로 토큰을 확인합니다. <br>
token에는 유저의 email이 저장되어있습니다. token에서 추출한 email로 user를 조회하게됩니다. <br>
token의 유효기간은 30일로 정했습니다.

### 유효성 검사
유효성 검사는 `spring-boot-starter-validation` 을 이용해서 했습니다.<br> 
jakarta.validation 어노테이션을 사용해서 필드마다 유효조건을 걸고 유효하지 못한 값이 들어왔을 때 던져지는 `MethodArgumentNotValidException` 을 `GlobalControllerAdvice` 가 받도록 구성했습니다. 

### 비밀번호 암호화
`PasswordEncoder` 를 이용해서 비밀번호를 암호화 했습니다. 로그인 했을 때의 비밀번호 일치 여부는 `PasswordEncoder` 가 제공하는 matches 메소드를 사용했습니다.

### Pagination
`스프링 프레임워크` 가 제공하는 `Page`, `Pageable` 을 사용해서 구현했습니다. <br> 
`@PageableDefault` 어노테이션을 사용해서 default로 20개, 생성 시간 내림차순으로 조회하도록 설정했습니다. 
<p align="right">(<a href="#readme-top">back to top</a>)</p>

&nbsp;
&nbsp;
&nbsp;
&nbsp;

## API 명세

### User API

* [회원가입](api-docs/user/join.md) : `POST /users/join`
* [로그인](api-docs/user/login.md) : `POST /users/login`


### Post API

* [게시글 생성](api-docs/post/create.md) : `POST /posts`
* [게시글 목록 조회](api-docs/post/list.md) : `GET /posts`
* [게시글 상세 조회](api-docs/post/detail.md) : `GET /posts/{postId}`
* [게시글 수정](api-docs/post/modify.md) : `PUT /posts/{postId}`
* [게시글 삭제](api-docs/post/delete.md) : `DELETE /posts/{postId}`


<p align="right">(<a href="#readme-top">back to top</a>)</p>

&nbsp;
&nbsp;
&nbsp;
&nbsp;

