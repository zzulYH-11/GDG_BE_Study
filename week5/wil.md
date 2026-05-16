## 객체 지향 프로그래밍 (OOP)

프로그램을 독립적인 객체들의 유기적인 협력과 결합으로 파악하는 패러다임. 유연하고 변경에 용이하여 대규모 소프트웨어 개발에 적합.

### **객체지향의 4대 특징**

**추상화 -** 객체의 공통 속성과 기능을 추출하여 정의

**캡슐화 -** 연관된 데이터와 기능을 하나로 묶어 데이터를 외부로부터 보호

**상속 -** 기존 클래스의 기능을 새로운 클래스가 물려받아 재사용

**다형성 -** 하나의 역할이 상황에 따라 여러 형태(구현체)를 가질 수 있음.

→ 클라이언트는 대상의 역할(인터페이스)만 알면 되며, 내부 구조나 구현체(클래스)가 바뀌어도 영향을 받지 않아 프로그램이 유연해지고 변경이 용이해짐

## SOLID: 좋은 객체 지향 설계를 위한 5원칙

#### **SRP (단일 책임 원칙, Single Responsibility Principle)**

 : 한 클래스는 하나의 책임만 가져야 함 

하나의 클래스에 여러 기능(책임)이 있으면, 특정 기능을 수정해야할 때 다른 기능에도 영향을 미치게 되어 수정해야 할 코드가 많아짐. 유지보수성을 확보하기 위해 하나의 클래스에는 하나의 책임만 부여하자.

#### **OCP (개방-폐쇄 원칙, Open/Closed Principle)**

클래스는 확장에는 열려 있고, 수정에는 닫혀 있어야 함. (기존 코드 변경 없이 기능을 확장할 수 있어야한다)

#### **LSP (리스코프 치환 원칙, Liskov Substitution Principle)**

자식 클래스는 언제나 부모 클래스를 대체할 수 있어야 함 

(단순히 문법적인 상속이나 타입 호환성을 넘어서 행동적 호환성을 보장해야함, 부모 클래스의 기능을 다르게 변경하면 안된다는 말)

#### **ISP (인터페이스 분리 원칙, Interface Segregation Principle)**

인터페이스를 각각 사용에 맞게끔 잘 분리해서 클라이언트의 목적과 용도에 적합한 인터페이스만을 제공하여 클라이언트가 사용하지 않는 인터페이스에 의존하지 않도록 해야한다. 거대한 인터페이스보다 용도에 맞는 여러 개의 구체적인 인터페이스가 좋음. 이래야 인터페이스가 명확해지고, 대체 가능성이 높아짐 (SRP의 인터페이스 버전 느낌)

#### **DIP (의존관계 역전 원칙, Dependency Inversion Principle)**

추상화(인터페이스)에 의존해야지, 구체화(구현 클래스)에 의존하면 안 됨.

## IoC와 DI

```java
public class MemberService {

	//private final MemberRepository memberRepositoy = new JpaMemberRepository();
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	// Reposioty 구현체를 바꾸려면 코드를 수정해야함 (OCP 위반)
}
```

다형성 구현을 통해 SOLID 원칙을 모두 충족한 것 같지만 사실 MemberService는 인터페이스 타입 변수 안 구현 클래스에 의존 중이다 (DIP 위반)

이처럼 다형성만으로는 SOLID 원칙을 지킬 수 없다. 의존성 주입이 필요해지는 시점이다.

### IoC (제어의 역전, Inversion of Control)

객체의 생성 및 관리 제어권이 개발자가 아닌 프레임워크에 있는 것. 스프링의 경우 객체를 생성, 관리하는 역할을 스프링 컨테이너가 대신한다. 그래서 스프링 컨테이너를 IoC 컨테이너 라고도 한다.

### Spring Container

ApplicationContext가 스프링 컨테이너(스프링 빈 저장소)의 핵심 인터페이스이고, 빈의 생성, 관리, 소멸 등의 생명주기를 담당한다

### 스프링 컨테이너와 빈(Bean)

**스프링 빈이란** 스프링 컨테이너가 관리하는 자바 객체로, 어플리케이션 전역에서 사용하는 공용 객체이다. 스프링은 객체(빈)를 딱 하나만 생성해 컨테이너에 보관해놓고 공유함으로써 메모리를 효율적으로 사용한다. 이걸 싱글톤이라고 한다. 빈은 여러 스레드가 공유하므로 상태를 가지면 안된다고 한다.

### 빈 등록 방법

**설정 파일을 수동으로 작성하여 등록하기**

`@Configuration` 으로 설정 클래스 생성

 등록하고자 하는 객체를 반환하는 메서드에  `@Bean` 어노테이션 사용.

**컴포넌트 스캔을 통한 자동 등록**

클래스에 `@Component` (또는 `@Service`, `@Repository`)를 붙인다. SpringBootApplication 어노테이션 안에 `@ComponentScan` 어노테이션이 포함되어 스프링이 실행될 때 자동으로 빈들을 찾아 컨테이너에 등록하고 의존성도 주입하여준다

### DI (의존성 주입, Dependency Injection)

객체가 직접 의존 객체를 생성하고 사용하는 게 아니라, **외부(컨테이너)에서 주입받아 사용하는 방식. 이를 통해 IoC를 구현할 수 있다.** 

```java
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		}
}
```

클라이언트 코드 수정 없이 구현체 변경 가능 (OCP, DIP 준수).

### 의존성 주입 방식 3가지

를 살펴보기 전에 먼저 @Autowired 어노테이션에 대해 살펴보자

@Autowired는 스프링 컨테이너에 등록된 빈 중 필요한 타입의 객체를 자동으로 찾아서 주입해주는 어노테이션이다. 개발자가 직접 객체를 찾아 연결하지 않아도 스프링이 알아서 해준다.

- **생성자 주입**

```java
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired  // 생성자가 하나뿐이면 생략 가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

생성 시점에 주입되어 불변성 보장. 

`@RequiredArgsConstructor`를 사용하여 `final` 필드에 대한 생성자 자동 생성하여 코드를 간략화할 수 있음. 가장 권장되는 방식임

- 수정자 주입

```java
@Service
public class MemberService {

    private MemberRepository memberRepository;  // final 불가

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

변경 가능성이 있는 의존 관계에 활용한다. 객체를 먼저 생성해 두고, @Autowired 어노테이션이 붙은 세터를 호출하여 의존성을 나중에 주입한다. 

등록할 빈이 없어도 괜찮다고 설정하려면 @Autowired(required=false)로 작성하면 된다. 수정자 주입에서는 이렇게 선택적으로 의존성을 주입할 수 있다. 생성자 주입에는 사용 불가하다

- 필드 주입

```java
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;  // final 불가
}
```

생성자나 메서드 없이 필드에 @Autowired를 붙여 사용. 코드가 간결하지만  

final 키워드를 쓸 수 없어 의존관계가 변경될 가능성이 있다.

 순수 자바 코드로 단위 테스트를 작성하려면 의존 객체를 직접 넣어줄 방법이 없기 때문에 테스트가 힘들다. 필드 주입은 스프링 컨테이너가 리플렉션 기술로 강제 주입하는 방법인데, 스프링이 없으면 의존성을 넣어줄 방법이 없다.

 또 어떤 의존성을 갖는지 생성자 주입, 세터 주입보다 파악하기 힘들다

이 방법은 쓰지 말자.

### 조회되는 빈이 2개 이상일 시 해결법

- `@Primary`: 이 어노테이션이 붙은 빈을 먼저 선택함
- `@Qualifier`: 빈에 별칭을 부여하여 주입 시 명시적으로 지정.

```java
// 빈 등록 시 별칭 부여
@Component
@Qualifier("jpaMemberRepository")
public class JpaMemberRepository implements MemberRepository { ... }

@Component
@Primary
public class MemoryMemberRepository implements MemberRepository { ... }
```

```java
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	public MemberService(@Qualifier("jpaMemberRepository") MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		}
}
```

위 예시처럼 서비스에서 리포지토리를 주입받을 때 저렇게 어노테이션으로 별칭을 지정해주면 해당 빈을 주입받을 수 있다(이때는 @Primary 어노테이션이 붙은 다른 빈이 있더라도 별칭을 지정한 빈이 우선되어 주입된다). 만약 서비스에서 별칭을 정해주지 않는다면 @Primary 어노테이션이 붙은 MemoryMemberRepository가 주입된다.

만약 위 어노테이션 없이 같은 타입의 빈이 여러개 존재한다면 스프링은 예외를 던진다(NoUniqueBeanDefinitionException)

---
# 추가 공부 내용 정리

## Long과 long

구현체에 인터페이스의 추상함수를 오버로딩하여 작성할 때 분명 잘 작성한 것 같은데 인텔리제이가 인식을 못 하는 이슈가 있었다. 이는 파라미터를 Long, long으로 다르게 작성했기 때문이었다.

둘이 뭐가 다를까?

long은 원시 타입으로 값 자체를 저장하여 메모리를 효율적으로 사용한다

Long은 참조형(wrapper class)로 null을 저장할 수 있다.

PA는 `id == null`이면 새 엔티티로 판단해서 `INSERT`를 치는데, `long`이면 null이 될 수 없어서 항상 `0`이 들어가 새 엔티티 감지가 안 되는 문제가 생긴다. 그래서 엔티티의 id는 Long으로 설정하는게 관례이다.

 그리고 Long 타입을 사용해야 List<Long>처럼 id들을 담는 컬렉션을 사용할 수가 있다.

## 무조건 인터페이스를 구현하도록 짜면 좋은가?

No

## 리포지토리의 경우

개발 초기 DB 설계 전 메모리 repo를 활용해 빠르게 개발하다가 DB로 전환하는 경우

초기에 RDB만 사용하다가 특정 데이터를 MongoDB나 Redis 등으로 옮길 때

외부 결제 api를 사용하다가 내부 결제 시스템을 구축하여 내부 DB로 전환할 때 등

실제로 바꿀 일이 생기므로 인터페이스를 권장

## 서비스

아직 어떤 할인 로직이 적용될 지 모르는데 일단 개발은 해야하는 경우, 테스트에서 Mock 객체로 변환하는 경우, 실제로 구현체가 여러개인 경우처럼 특수한 경우에는 상황에 따라 사용

## 컨트롤러의 경우

단순히 http 요청을 받아 넘기는 역할만 하므로 구현체가 바뀔 일이 없음. 정말 가끔 스웨거 어노테이션이 너무 많아 코드가 묻히는 경우 인터페이스에 어노테이션을 몰아넣는 정도? 컨트롤러에서는 비권장