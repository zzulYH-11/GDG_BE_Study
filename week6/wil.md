# Bean Validation

클라이언트가 잘못된 데이터를 보냈을 때, 이를 DTO 단에서 입력 데이터의 형식이 올바른지 어노테이션을 통해 유효성 검사를 할 수 있다. 이를 빈 벨리데이션이라고 한다. 

Bean Validation이라는 Java 표준 스펙에 여러 어노테이션들이 정의되어 있고 Spring Boot에는 Bean Validation의 실제 구현체인 Hibernate Validator가 기본으로 내장되어 있다.

이를 사용하기 위해서는 다음 의존성을 build.gradle에 추가해야 한다.

`implementation 'org.springframework.boot:spring-boot-starter-validation`

컨트롤러의 RequestBody DTO 앞에 `@Valid` 어노테이션을 붙여주어야 실제로 검증 로직이 수행된다. `@Valid` 는 자바 표준 검증 어노테이션이다.

여러 DTO를 만들기 귀찮아 일부 필드만 사용하는 방식으로 DTO를 재사용하고 싶을 때, 그 필드들을 따로 그룹화하여 검증하는 `@Validated`를 사용할 수 있다. `@Validated` 는 스프링 전용 검증 어노테이션이다.

- `@NotNull`: 필드가 null인 경우를 방지한다.
- `@Size(min=x, max=y)`: 데이터의 최소/최대 길이 제한한다.
- `@Pattern(regexp=...)`: 정규 표현식을 이용해 전화번호 형식 등 특정 포맷 검증한다.

regexp = "^010-\\d{4}-\\d{4}$” → 전화번호를 010-XXXX-XXXX 형식으로 제한

- 각 어노테이션에 `message` 속성을 부여해 에러 문구 설정 가능하다.
- @NotBlank, @NotEmpty, @Null, @Max(value), @Min(value) 등등…

# 예외 처리 (Exception Handling)

유효성 검사 실패나 비즈니스 예외 발생 시, 클라이언트에게 명확한 에러 원인을 알려주기 위해 커스텀 예외 처리를 구현해보자.

### 1) 전역 예외 핸들러 (Global Exception Handler)

`@RestControllerAdvice`를 사용하여 도메인 상관없이 모든 컨트롤러에서 발생하는 예외를 별도의 한 클래스에 모아 중앙 집중식으로 처리하는 방법이다(AOP 개념 활용). 

응답 공통 규격인 `ErrorResponse` DTO를 생성하여 JSON 바디에 일관된 에러 메시지가 담기도록 설정한다.

- 시스템 내에서 처리되지 않은 예상치 못한 예외는 가장 하단에 `@ExceptionHandler(Exception.class)`를 가지는 handleUnknownException 클래스를 두어 직접 만든 ErrorResponse DTO를 반환하도록 코드를 작성.  `500 Internal Server Error`로 처리.
- 스프링에서 제공하는 핸들러. 예외 종류에 따라 response를 설정 가능하다
- Global Exception을 처리하여 스프링 애플리케이션 전역의 모든 에러 처리 방법을 결정한다.
- 일반화 해보면, @ExceptionHandler(A.class)가 있을 때 A 타입 에러가 발생하면, 해당 에러 타입을 다루는 핸들러(어노테이션이 붙은 클래스)가 컨트롤러 메서드를 대신하여 Response Body를 생성하고 응답한다.
- handleUnknownError의 Exception.class는 모든 에러 클래스의 공통 부모이다.
- 우리가 만든 특정 핸들러에서 처리하지 못한 예외는 전부 handleUnknownError 메서드가 처리한다.

### 2) 커스텀 예외 클래스 구현

`RuntimeException`을 상속받는 CustomException 클래스를 구현(common → exception 패키지 안)하고, 이 안에 내부 클래스로 `BadRequestException` (400 에러 처리용), `NotFoundException` (404 에러 처리용) 등을 구현해둔다.(보통은 도메인 별로 한 외부 클래스에 묶어 관리한다고 한다)

핸들러에 등록하여 발생한 예외에 맞는 HTTP Status Code와 커스텀 메시지를 매핑하여 반환하자. 유효성 검사 실패 시 발생하는 `MethodArgumentNotValidException`도 핸들러에 등록하여 첫 번째 에러 메시지를 추출한 뒤 400 에러로 리턴함.

### 3) 에러 메시지 클래스 분리 (상수화)

 "회원을 찾을 수 없습니다", "이미 존재하는 아이디입니다." 등 에러 메시지를 하드코딩하여 중복 사용하면 수정하고 싶을 때 하나씩 찾아서 수정해야한다. 

`ErrorMessage` 클래스를 생성하여 `public static final String` 상수로 모아서 관리하면 유지보수성을 극대화할 수 있다.

```java
package ~.common.message;

public class ErrorMessage {

	// 상수의 변수명은 항상 대문자로만! 
	public static final String MEMBER_NOT_FOUND = "회원을 찾을 수 없습니다.";
	public static final String MEMBER_ALREADY_EXIST = "이미 존재하는 로그인 아이디입니다.";
	// 등등
	}
	
```

이러면 문자열 상수만 모아두는 수준이고 HTTP 상태코드, 에러코드 번호 같은 관련 데이터를 묶을 방법이 없다는 단점이 있다. 

타입 안정성이 없어 아무 String이나 넘겨도 컴파일 에러가 안 난다?

```java
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U002", "권한이 없습니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "주문을 찾을 수 없습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력입니다.");

    private final HttpStatus status;   // HTTP 상태코드
    private final String code;         // 에러 식별 코드
    private final String message;      // 에러 메시지
}
```

이렇게 Enum으로 코드를 짜면 관련 데이터를 하나의 상수에 묶을 수 있고, Errorcode타입 외엔 들어올 수 없어 타입 안정성을 확보할 수 있다. 또한 이미 정의된 것만 사용 가능하므로 오타로 인한 버그를 원천 차단할 수 있다.

# API 문서화 (Swagger / OpenAPI)

프론트엔드 개발자 등 타 부서와 협업하고 소통할 때 공유할 API 설명서가 필요하다.

세팅이 간편하고 테스트 기능이 기본 내장된 Swagger 라이브러리를 사용해보자. 

spring doc을 사용하면 OpenAPI 규격(API의 표준 명세)의 API 문서를 생성할 수 있다.

swagger ui를 사용하면 api 문서에 swagger 디자인을 적용할 수 있다.

이를 위해선 build.gradle에 의존성을 추가해야함.

어플리케이션을 실행하고 http://localhost:8080/swagger-ui/index.html에 접속하면 문서를 볼 수 있다. 여기서 간단한 api 테스트도 가능하다.

swagger 문서화에 사용되는 주요 어노테이션들을 살펴보자

- `@Tag(name = "...", description = "...")`: 컨트롤러 클래스 상단에 배치하여 해당 컨트롤러의 이름을 표시하고 간단한 설명을 추가할 수 있다.
- `@Operation(summary = "...", description = "...")`: 각 API 메서드의 기능 요약 및 상세 설명 명시 가능하다
- `@ApiResponse(responseCode = "...", description = "...")`: API가 리턴할 수 있는 HTTP 상태 코드와 그 상황에 대한 설명을 명세화해줌. 여러개 설정하는 것도 가능하다

---

## Swagger UI 확인 스크린샷
![alt text](images/img1.png)

## POSTMAN 테스트 스크린샷
![alt text](images/img2.png)