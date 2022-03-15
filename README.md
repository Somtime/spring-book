# 토비의 스프링 3.1 Vol.1 스프링의 이해와 원리

## 1장 오브젝트와 의존관계

<details>
<summary>
<strong>
오브젝트와 의존관계
</strong>
</summary>

<b>자바빈 (JavaBean)</b>

- 디폴트 생성자  
  : 매개변수가 없는 기본 생성자를 가지고 있어야 함

  ```
  public class User { public User() { } }
  ```

- 프로퍼티  
  : private 로 선언한 멤버 변수를 getter/setter 매소드로 멤버변수에 접근하여 수정 및 조회

  ````
  public class User {
  private String id;
  private String name;
  private String password;

          public String getId() {
              return id;
          }

          public void setId(String id) {
              this.id = id;
          }

          public String getName() {
              return name;
          }

          public void setName(String name) {
              this.name = name;
          }

          public String getPassword() {
              return password;
          }

          public void setPassword(String password) {
              this.password = password;
          }
      }
      ```
  ````

<br>

<b>분리와 확장을 고려한 설계</b>

- 관심사의 분리 (Separation of Concerns)  
  : 하나의 관심사는 하나의 기능만 가지도록 별도로 구성하는 것

<br>

<b>템플릿 메소드 패턴 (Template Method Pattern)</b>

- 슈퍼클래스에서 기본적인 로직의 흐름을 구현
- 슈퍼클래스의 매소드는 템플릿, 추상, 훅 메소드 3종류로 분류
  - 템플릿 메소드 (Template Method) : 공통 기능 구현
  - 추상 메소드(Abstract Method) : 서브 클래스에서 구현
  - 훅 메서드(Hook Method) : 기본 기능은 구현 but 서브 클래스에 재구현 가능

<br>

<b>팩토리 메소드 패턴 (Factory Method Pattern)</b>

- 객체를 생성하기 위한 방법을 팩토리에 정의
- 구체적인 오브젝트 생성 방식은 서브 클래스에서 결정
- 주로 인터페이스 타입으로 오브젝트를 리턴하기 때문에 슈퍼클래스에서는 서브클래스에서 어떤 오브젝트를 만들어 리턴하는지는 알 수 없음

<br>

<b>객체지향 설계 원칙 (SOLID)</b>

- 단일 책임 원칙 (SRP : The Single Reponsibility Principle)  
  : 단일 클래스는 단일 책임만을 가져야 한다.
- 개방 폐쇄 원칙 (OCP : The Open Closed Principle)  
  : 높은 응집도와 낮은 결합도
  - 클래스가 하나의 책임이나 관심사에만 집중되어 있어 변경되어도 외부에 영향을 미치지 않음
- 리스코프 치환 원칙 (LSP : The Liskov Substitution Principle)  
  : 하위 클래스는 부모 클래스의 인터페이스 규약을 지켜야 함
- 인터페이스 분리 원칙 (ISP : The Interface Segregation Principle)  
  : 범용 인터페이스 보다는 구체적인 여러개의 인터페이스
- 의존관계 역전 원칙 (DIP : The Dependency Inversion Principle)  
  : 변하기 쉬운 것(구현체 클래스) 보다는 변화하지 않는 것(인터페이스, 추상 클래스)과 의존관계를 맺어야 함

<br>

<b>제어의 역전 (Inversion of Control)</b>

- IOC 컨테이너가 오브젝트 생성, 관계설정, 사용 제거 등 오브젝트 전반(스프링에서는 @Bean)에 걸친 모든 제어권을 갖게 된다는 개념

<br>

<b>싱글톤 패턴 (Singleton Pattern)</b>

: 객체의 인스턴스를 하나만 생성하여 생성된 객체를 어디에서나 사욜할 수 있게 하는 것

```
public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() { }

    public static Singleton getInstance() {
        return singleton;
    }

    public void method() { }
}
```

- 장점
  - 객체를 하나만 생성하여 사용하기 때문에 여러 곳에서 생성하지 않아 메모리의 낭비를 방지할 수 있음
  - 인스턴스가 전역으로 선언되기 때문에 클래스 간의 데이터 공유가 용이함
- 단점
  - 하나의 인스턴스를 사용하기 때문에 동시성 문제가 발생할 수 있음
  - 싱글톤으로 구현된 객체가 너무 많은 기능을 가지면 결합도가 높아져 OCP에 위배될 수 있음

<br>

<b>Reference</b>

<a>https://coding-factory.tistory.com/712</a>  
<a>https://western-sky.tistory.com/40</a>
<a>https://tecoble.techcourse.co.kr/post/2020-11-07-singleton/</a>

</details>

## 2장 테스트

<details open>
<summary>
<strong>
테스트
</strong>
</summary>
