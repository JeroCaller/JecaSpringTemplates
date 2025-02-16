# JecaSpringTempate - Template Type 1

# Specifications
- Spring Boot 3.4.2
- JDK 21
- Based on REST API

## Dependencies
- devtools
- web
- Spring Data JPA
- validation
- Spring Security
- Lombok
- Mariadb driver
- Swagger
- Junit

( 자세한 사항은 build.gradle 파일 참조 )

## features
- 세션 기반 인증
  - 현재 사용자 인증 여부 체크 (By @AuthChecker)
  - 회원 정보 수정 혹은 회원 탈퇴 시 세션 내 인증 정보 갱신 (By @UpdateAuth, @InvalidateAuth)
  - 위 기능들을 간편하게 사용할 수 있도록 어노테이션 제공.
  - 로그인, 로그아웃 서비스 로직 및 컨트롤러.
- 서비스 인터페이스 템플릿.
  - 인증 (AuthInterface)
  - 파일 업로드 및 다운로드 관련 인터페이스 (FileIOInterface)
  - 유저 CRUD (UserCRUDInterface)
  - 일반 CRUD (CRUDInterface)
- 설정
  - AspectJ
  - JpaAudit
  - application/octet-stream MIME Type 처리를 위한 messageConverter
  - Spring Data JPA - Page serialization
  - Spring Security
  - 파일 리소스 핸들러.
- Entity 공통 필드를 모은 BaseEntity
  - 생성 일시
  - 수정 일시
- BaseEntity에 매핑되는 BaseResponse DTO
- REST API에서 일관적인 JSON 응답을 위한 클래스(RestResponse) 및 응답 코드(CustomResponseCode)
- 커스텀 예외 클래스 제작을 위한 최상위 커스텀 예외 클래스 BaseCustomException
  - 파일 업로드, 다운로드 작업 관련 커스텀 예외 클래스.
- 컨트롤러 레벨에서 발생하는 전역 범위의 예외 처리 클래스(GlobalControllerExceptionHandler)
- 각종 유틸리티 클래스
  - 현재 사용자의 인증 정보 접근을 위한 AuthUtil
  - List 자료구조에 대한 유틸리티 클래스 (ListUtil)
  - Map 자료구조에 대한 유틸리티 클래스 (MapUtil)
  - Page에 대한 유틸리티 클래스(PageUtil)
  - 유효성 검사 관련 유틸리티 클래스(ValidationUtil)

## package structure
```
/src
├─main
│  ├─java
│  │  └─com
│  │      └─example
│  │          │  TemplateType1Application.java
│  │          │
│  │          ├─advice
│  │          │  │  AuthAdvicePrimary.java
│  │          │  │  AuthAdviceSecondary.java
│  │          │  │  TestAdvice.java
│  │          │  │
│  │          │  └─annotation
│  │          │          AuthChecker.java
│  │          │          ExcludeFromAOP.java
│  │          │          InvalidateAuth.java
│  │          │          TestForAOPInClass.java
│  │          │          TestForAOPInClassAndMethod.java
│  │          │          TestForAOPInMethod.java
│  │          │          UpdateAuth.java
│  │          │
│  │          ├─business
│  │          │  │  AuthServiceImpl.java
│  │          │  │  CustomUserDetailsService.java
│  │          │  │  TestFileServiceImpl.java
│  │          │  │  TestMemberServiceImpl.java
│  │          │  │  TestProductServiceImpl.java
│  │          │  │  TestService.java
│  │          │  │
│  │          │  └─interf
│  │          │          AuthInterface.java
│  │          │          CRUDInterface.java
│  │          │          FileIOInterface.java
│  │          │          ReadInterface.java
│  │          │          UserCRUDInterface.java
│  │          │
│  │          ├─common
│  │          │      UserRole.java
│  │          │
│  │          ├─config
│  │          │      AdviceConfig.java
│  │          │      JpaAuditConfig.java
│  │          │      OctetMessageConverter.java
│  │          │      PageSerializationConfig.java
│  │          │      SecurityConfig.java
│  │          │      WebConfig.java
│  │          │
│  │          ├─controller
│  │          │      AuthController.java
│  │          │      TestController.java
│  │          │      TestFileController.java
│  │          │      TestMemberController.java
│  │          │      TestMyMemberController.java
│  │          │      TestProductController.java
│  │          │
│  │          ├─data
│  │          │  ├─entity
│  │          │  │      BaseEntity.java
│  │          │  │      TestFile.java
│  │          │  │      TestMember.java
│  │          │  │      TestProduct.java
│  │          │  │
│  │          │  └─repository
│  │          │          TestFileRepository.java
│  │          │          TestMemberRepository.java
│  │          │          TestProductRepository.java
│  │          │
│  │          ├─dto
│  │          │  ├─request
│  │          │  │      TestFileRequest.java
│  │          │  │      TestMemberRequest.java
│  │          │  │      TestProductRequest.java
│  │          │  │
│  │          │  └─response
│  │          │      │  BaseResponse.java
│  │          │      │  TestFileResponse.java
│  │          │      │  TestFileResultResponse.java
│  │          │      │  TestMemberHistory.java
│  │          │      │  TestMemberResponse.java
│  │          │      │  TestProductResponse.java
│  │          │      │
│  │          │      └─rest
│  │          │              CustomResponseCode.java
│  │          │              RestResponse.java
│  │          │
│  │          ├─exception
│  │          │  │  BaseCustomException.java
│  │          │  │  BaseInternalServerException.java
│  │          │  │
│  │          │  ├─classes
│  │          │  │      FileCreationFailedException.java
│  │          │  │      FileInfoInDBNotDeletedException.java
│  │          │  │      FileNotDeletedException.java
│  │          │  │      FileNotFoundOrReadableException.java
│  │          │  │      IORuntimeException.java
│  │          │  │      NoFileToDeleteException.java
│  │          │  │      NotAuthenticatedUserException.java
│  │          │  │      TestFileNotFoundException.java
│  │          │  │      TestMemberAlreadyExistsException.java
│  │          │  │      TestMemberNotFoundException.java
│  │          │  │      TestOtherMemberFileAccessException.java
│  │          │  │      TestProductAlreadyExistsException.java
│  │          │  │      TestProductNotFoundException.java
│  │          │  │
│  │          │  └─handler
│  │          │          GlobalControllerExceptionHandler.java
│  │          │
│  │          └─util
│  │                  AuthUtil.java
│  │                  ListUtil.java
│  │                  MapUtil.java
│  │                  PageUtil.java
│  │                  ValidationUtil.java
│  │
│  └─resources
│      │  application.properties
│      │
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─example
                    TemplateType1ApplicationTests.java
```

# Test Resources
- Test 로 시작하는 파일들은 삭제해도 무방합니다.

## 테스트를 위해 사용한 DB, TABLE 생성 쿼리문
```sql
CREATE DATABASE IF NOT EXISTS test_for_templates;
USE test_for_templates;

DROP TABLE IF EXISTS tb_file;
DROP TABLE IF EXISTS tb_member;

CREATE TABLE IF NOT EXISTS tb_member (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nickname VARCHAR(20) NOT NULL UNIQUE KEY,
  password VARCHAR(100) NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_file (
  id INT PRIMARY KEY AUTO_INCREMENT,
  path VARCHAR(500) NOT NULL,
  description VARCHAR(1000) DEFAULT '',
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  member_id INT NOT NULL
);

ALTER TABLE tb_file
	ADD CONSTRAINT fk_tb_member_tb_file_id
		FOREIGN KEY (member_id) REFERENCES tb_member(id);

-- 회원 및 파일과는 무관한 정보
DROP TABLE IF EXISTS tb_product;

CREATE TABLE tb_product (
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL UNIQUE KEY,
	category VARCHAR(30) NOT NULL,
	description VARCHAR(1000) DEFAULT '',
	amount INT DEFAULT 0,
	price INT DEFAULT 0
);
```

# 그 외 안내 사항
- 사용 예시는 패키지 내 Test란 이름으로 시작하는 파일들을 참조하시기 바랍니다.
- 이 템플릿은 말 그대로 특정 기능을 편리하게 사용하기 위한 템플릿이므로, Test로 시작하는 파일들 외 다른 요소들을 마음대로 커스텀해도 됩니다. 다만 커스텀 시 일부 기능이 작동되지 않을수도 있습니다. 