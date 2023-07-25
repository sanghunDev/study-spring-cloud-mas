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
      
### 의존성
* Netflix-eureka-client
* SpringCloudGateway
* Lombok

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
