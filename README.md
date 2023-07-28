# study-spring-cloud-mas
* 멀티모듈로 구성된 Spring Cloud 를 이용한 MAS 프로젝트
  * MAS 템플릿을 만들기 전 스터디용 프로젝트

-------------------------------

# ApiGateway 및 Discovery 공통 개발 환경
* Build Tool : Gradle
* Java Version : 17
* Spring Boot Version : 3.1.2

-------------------------------

# ApiGatewayService 프로젝트
* SpringCloudGateway
  * 클라이언트 -> APIGateWay -> DiscoveryService -> APIGateWay -> EndPoint
* 모든 클라이언트의 요청을 받아 라우팅 설정에 따라 클라이언트 대신 각각의 EndPoint 로 요청을 보낸다
  * 프록시 서버의 역할을 한다고 볼 수도 있다
* 서비스 공통 정책 등 공통적인 로직에 대한 처리를 담당한다
  * 인증/인가, 권한 부여, 응답 캐싱, 로드밸런싱, Mediation (메세지, 포맷 등 변환), Qos 조정 등 
    * Qos 조정 (Quality of Service) API 서비스를 클라이언트 대상에 따라서 서비스 레벨을 조정하는 것
      * 사용자에 따라 API 호출 건수 제어 등..

* 인증 / 인가
  * 최초 로그인 시점에는 User 마이크로 서비스에서 검증
    * 로그인 후 추가적인 요청 -> 다른 마이크로 서비스에 대한 요청 및 User 마이크로 서비스에 대한 요청 등..
      * GateWay 에서 해당 토큰의 유효성을 검증 후 다른 마이크로 서비스에는 사용자에 대한 정보만 전달한다
      * GateWay 에서 인증에 대한 검증을 함으로써 개별 마이크로 서비스에서의 검증로직이 필요없어지고 중앙화된 관리가 가능하다
  * GateWay 에서는 Redis 를 활용해서 검증한다
    1. 로그인 요청 -> GateWay -> User 마이크로 서비스 -> 토큰 발급 -> Redis 저장
    2. 추후 요청 (Jwt 토큰 검증이 필요한 요청) -> GateWay Filter -> Jwt 토큰 확인 -> Redis 확인 -> 해당 토큰 검증
    3. 토큰 검증 후 성공 -> 요청 진행, 실패시 에러 반환

### 의존성
* Netflix-eureka-client
* SpringCloudGateway
* Lombok 
* Jwt 관련 라이브러리 (jjwt 등..)

----------------------------

# DiscoveryService 프로젝트
* EurekaServer
  * EurekaServer 는 EurekaClient 정보들을 제공하고 서비스는 LocalCache 에 저장한다 
* 외부의 서비스들이 마이크로 서비스를 검색하기 위해 사용하는 역할을 담당한다
* 각 마이크로 서비스가 어느 위치에 있는지 등록해 놓는 것
* API Gateway 에 요청이 전달된 후 다음으로 EurekaServer 요청이 들어온다
  * EurekaServer 는 필요한 서비스가 있는 곳의 정보를 API Gateway 에 반환하게 된다 

### 의존성
* Netflix-eureka-server

----------------------------

# UserService 프로젝트
* 회원 관련 마이크로 서비스
* 최초 로그인시 SpringSecurity 에서 회원 검증 후 JwtToken 을 발급
  * 토큰 발급 시 이미 로그인 된 회원에 대한 요청을 UserService 에서 처리하지 않기 위해 Redis 에 토큰 저장
  * 저장된 토큰을 활용 GateWay 에서 토큰에 대한 검증 실시

### 개발환경 
* Build Tool : Gradle
* Java Version : 17
* Spring Boot Version : 3.0.5
* Spring Cloud Version : 2022.0.3

### 의존성
* Netflix-eureka-client: 4.0.0
* SpringWeb
* Lombok
* h2
* SpringBootDevtools
* SpringDataJpa
* QueryDsl: 5.0.0
* ModelMapper
* Security
* Validation
* Jwt 관련 라이브러리 (jjwt 등..)
* Redis
