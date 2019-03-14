## 1.Inversion of Control
 
1. create and manager objects (Inversion of Control)
2. Inject object's dependencies (Dependency Injection) 

	
### 2. Configuring Spring Container	
1. XML configuration file
2. Java Annotation
3. Java Source Code

### 3. Spring Development Process
	
1. Configure your Spring Beans
2. Create Spring Container
3. Retrieve Beans from Spring Container

### 4. Spring Bean Scopes and Lifecycle

#### scope	
1. `singleton` 
2. `prototype`---->Create new bean instance for each container request
3. `request`
4. `session`
5. `global-session`

#### lifecycle
> container started---> Bean instantiated ---> Dependencies Injected ---> Internal spring processing ---> your custom init method

1. 在bean初始化的时候可以添加初始化方法

### 4. JAVA Annotation

#### 1. Inversion of control
1. Enable component scanning in Spring config file
2. Add the @Component Annotation to your java classes
3. Retrieve bean from Spring container

>BeanId  `@Component("beanId")` defaultBeanId: make first letter lower-case ` TennisCoach-> tennisCoach`


#### 2.Injection
1. Constructor Injection,
2. Setter Injection, 
3. Field Injections

	
		@Autowired
		@Qualifier("happyFortuneService")
		private FortuneService fortuneService
		
#### 3.Bean Scope

		@Component
		@Scope("prototype")
		public class TennisCoach implements Coach {
			...
		}
		
#### 4.Life cycle

		@Component
		public class TennisCoach implements Coach {
			@PostConstruct
			public void doMyStartupSutff() {...}
			@PreDestory
			public void doMyCleanupSutff() {...}
		}
		
## 2. Hibernate

- [jpa-and-hibernate-cascade-types](https://vladmihalcea.com/a-beginners-guide-to-jpa-and-hibernate-cascade-types/)

#### 1. OneToOne - Cascade Types

1. [@JoinColumn](https://stackoverflow.com/questions/11938253/whats-the-difference-between-joincolumn-and-mappedby-when-using-a-jpa-onetoma)

		@Entity
		@Table(name="instructor")
		public class Instructor {
			...
			
			@OneToOne(cascade=CascadeType.All)
			@JoinColumn(name="instructor_detail_id")
			private InstructorDetail instructorDetail;
		}
		
#### 2. OneToMany

		
		@Entity
		@Table(name="instructor")
		public class Instructor {
			
			@OneToMany(mappedBy="instructor",
			cascade={CascadeType.PERSIST,CascadeType.MERGE,
			CascadeType.DETACH,CascadeType.REFRESH})
			private List<Course> courses;
		
		}
		
#### 3. ManyToOne

			
		public class Course {
			...
			@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,
			CascadeType.DETACH,CascadeType.REFRESH})
			@JoinColumn(name="instructor_id")
			private Instrucotr instructor;
			...
		}	

## 3.AOP

#### 1.应用场景
		
1. logging
2. security
3. transactions


##### 2.AOP Terminology(术语)
1. `Aspect`:module of code for a cross-cutting concern(logging, security..)
		
		@Aspect
		@Component
		public class DemoAspect {
			
			@Before("execution(public void addAccount())")
			public void beforeMehtod() {
				...
			}
		}


2. `Advice`:what action is taken and when it should be applied
	1. `@Before`
	2. 	`@AfterReturning`
	3. `@AfterThrowing`
	4. `@After`
	5. `@Around`
		

3. `Join Point`: when to apply code during program execution
		
	1. 可以利用`join point`获取到方法参数

4. `Pointcut`: A predicate expression for where advice should be applied
		
	1. reuse ponitcut
		
				
			@Pointcut("execution(* com.luv2code.aopdemo.dao.*.*(..))")
			private void forDaoPackage() {}
			
			@Before("forDaoPackage()")
	
	2. parameter Pattern wildcards
		
		- `()` matches a method with no arguments
		- `(*)` matches a method with one argument of any type
		- `(..)` matches a method with 0 or more argument of any type

	3. expression

			execution(modifiers-pattern? return-type-pattern declaring-type-pattern? 
			method-name-pattern(param-pattern) throws-pattern?)
			
			@Before("execution(public void com.dao.demo.MyClass.mymethod())")	
		
## 4.Spring Security

#### 1.Password Encryption
1. bcrypt
2. database password field length should be 68
		

## 5.Spring Boot


##### 1.@SpringBootApplication (默认扫描当前包)

- 等同于`@EnableAutoConfiguration` `@ComponentScan`  `@Configuration`
- `@SpringBootApplication(scanBasePackages={"com.demo","org.acme"})`

##### 2.Application Properties
- `@Value(${coach.name})` from Application Properties

##### 3.注意事项
- do not use`src/main/webapp` if your application is packaged as a JAR. it work only with WAR packaging

##### 4.spring-boot-actuator

1. appliaction.properties `http://localhost:8080/actuator/info`
	
		info.app.name = Robot Chef
		info.app.description = Server for Robot Chef
		info.app.version = 1.0.0
	
2. endpoint

		management.endpoints.web.exposure.include=*

#### 5.run from comman line
- 使用maven package 打包项目
- `java -jar myapp.jar` or `manw spring-boot:run`


#### 6.Spring Data JPA
1. Various Dao Techniques
	
	1. Version1: Hibernate API
	2. Version2: Use standard JPA API (Interface -> Impl)
	3. Spring Data JPA (重复编码量少)

2. method
	
	1. `findAll()`
	2. `findById()`
	3. `save()`
	4. `deleteById()`
	5. ...others...

3. `Spring Data JPA`spring will give you CRUD impl for Free
	
4. Development Process
	
	1. Extend JpaRepository interface
	2. Use your Repository in you app

			public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
			// ..no need to write any code LOL;
			}
		
#### 7. spring-boot-starter-data-rest
1. Your entity: Employee
2. JpaRepository: EmployeeRepository extends JpaRepository
3. Maven POM dependency for: spring-boot-starter-data-rest
	 

- api生成规则:
	 - lowercase first character, add `s` to the entity:`Employee`-> `employees`
	 
- 自定义api endpoint
	
		@RepositoryRestResource(path="members")
		public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
		}
		
- 分页`http://localhost:8080/employees?page=0&sort=lastname`
	
- 配置
		
		spring.data.rest.base-path = /magic-api
		spring.data.rest.default-page-size = 20
		spring.data.rest.max-page-size
		
#### 8.异常处理
1. Create a custom error response class
2. create a custom exception class
3. update Rest service to throw exception if student not found
4. Add an exception handler using @ExceptionHandler
		
## Projcet
1. [spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate](https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/)
2. [jwt-role-based-authorization](https://medium.com/@xoor/jwt-authentication-service-44658409e12c)
3. [Oauth2-Stateless-Authentication-with-Spring-and-JWT-Token](https://github.com/tinmegali/Oauth2-Stateless-Authentication-with-Spring-and-JWT-Token)		

	