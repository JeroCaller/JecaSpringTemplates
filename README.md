# JecaSpringBootTemplates

스프링 부트 프로젝트에서 공통적으로 사용할 도구들을 프로젝트 폴더로 만들어 템플릿화하여 제공합니다. 
각 상황별로 스프링 부트 프로젝트 폴더들을 제작할 예정이며, 각 프로젝트 폴더 내 README.md 파일을 참고하면 되겠습니다. 

# 공통 사항
모든 템플릿에 적용되는 공통 사항들을 설명합니다. 

## 공통 Spec
- Build Tool: Gradle
- 작성 시 OS 환경 : Windows 10
- JDK 21
- Spring Boot 버전 3 이상만 사용함

## 설정

1. 프로젝트 폴더 이름 변경 시 setting.gradle 파일에서 rootProject.name 프로퍼티 값도 변경될 폴더명으로 바꿔야 합니다. 또한, application.properties 파일 내 spring.application.name의 값도 해당 폴더명과 동일하게 바꿔야 합니다. 이렇게 하지 않으면 제대로 동작하지 않을 수 있습니다. 

2. 모든 템플릿에서는 모든 의존성들을 설정하지 않았습니다. 따라서 필요한 의존성(라이브러리, 프레임워크)이 있다면 build.gradle에 직접 추가하셔야 합니다. 

3. 프로젝트 폴더 내 Test~로 시작하는 java 파일들은 테스트 및 사용 예시이므로 삭제하셔도 됩니다. 

4. Test~로 시작하는 java 파일 내 작성된 주석들은 제거하셔도 됩니다. 

## 설정 속성 표기법 설명
원활한 사용을 위해 꼭 필요한 설정인지 여부를 안내하기 위해 모든 템플릿에 아래와 같은 별도의 표기법을 사용하여 안내하고 있습니다. 

### application.properties
- customizable - 특정 프로퍼티를 수정해도 기능 상의 문제가 없는지 여부를 표시합니다. 
  - 가능한 값: value, value or delete
    - value - 프로퍼티의 값은 원하는 값으로 수정 가능. 단, 해당 프로퍼티 자체를 삭제하면 템플릿에서 기본 제공하는 기능을 제대로 사용할 수 없을 수 있으니 주의.
    - value or delete - 프로퍼티의 값을 수정해도 되고, 아예 해당 프로퍼티 자체를 삭제해도 템플릿에서 기본 제공하는 기능을 사용할 수 있음을 의미. 

customizable 예시)   
예시 1) 
```
# application.properties

# Mariadb server connect
# [ customizable: value ]
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://127.0.0.1:3308/study
spring.datasource.username=root
spring.datasource.password=1111
```
`# [ customizable: value ]` 가 작성된 주석 아래의 모든 프로퍼티들은 그 값을 원하는 값으로 수정해도 된다는 의미입니다. 단, 프로퍼티 자체를 삭제하면 여기서 제공하는 DB 연동 작업이 제대로 실행되지 않을 수 있으니 주의해야 합니다. 

예시 2)
```
# application.properties

# port configuration
# [ customizable : value or delete ]
server.port=8080
```
`# [ customizable : value or delete ]`가 작성된 주석 아래의 모든 프로퍼티들은 그 값을 원하는 값으로 수정해도 되며, 프로퍼티 자체를 삭제해도 기능 상에 문제가 없다는 의미. 

---

- change-recommended - 프로퍼티의 값의 변경 권고 여부. 이 속성 생략 시 기본값은 false
  - 가능한 값 - true, false
    - true - 프로퍼티의 값을 변경하는 것을 권고. 
    - false - 프로퍼티의 값을 변경하지 않아도 됨. 
  
change-recommended 예시)   
예시 1)
```
# application.properties

# port configuration
# [ change-recommended : true ]
spring.datasource.url=jdbc:mariadb://127.0.0.1:3308/study
spring.datasource.username=root
spring.datasource.password=1111
```
프로퍼티를 다른 값으로 바꾸는 것을 권고. 위 예시에서는 보통 연결하고자 하는 DB의 주소, DB명, username 및 패스워드가 개발자마다 다 다를 것이므로 그에 맞게 바꾸는 것을 권고하는 의미. 

---

- (개행 (한 줄 띄기)) - 아무런 속성의 영향도 받지 않음을 의미.

예시 1)
```
# [ customizable : value or delete ]
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
logging.level.org.hibernate.SQL=debug
logging.level.org.hibernate.type.descriptor.sql=trace

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```
`customizable : value or delete`의 속성은 바로 아래 5줄 모두에 적용됩니다. 그러나 마지막 줄에 존재하는 프로퍼티는 그 이전에 한 줄 개행되어있기 때문에 해당 속성의 영향을 받지 않습니다. 

---

## 그 외 공통 안내 사항
- 위 공통 사항에 적힌 정보는 각 템플릿마다 포함된 README.md에는 작성하지 않을 것이니 위 공통 사항도 같이 참고하시길 바랍니다. 
