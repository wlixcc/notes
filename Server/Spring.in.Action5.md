# 1.Getting started
1. 一般使用java类配置bean, bean ID和方法名一致

		@Configuration
		public class ServiceConfiguration {
		  	
		  @Bean
		  public InventoryService inventoryService() {
		    return new InventoryService();
		  }
		  @Bean
		  public ProductService productService() {
		    return new ProductService(inventoryService());
		  }
		}
2. `autowiring` 和 `component scanning`配合实现了Automatic configuration

# 2. Developing web applications
1. Lombok(简化pojo)
	
	1. pom.xml中添加依赖

				  <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        		</dependency>

	2. The `@Data` implicitly adds a required arguments constructor,

# 3. Working with data

1. JPA entity需要有无参数构造函数,如果使用Lombok，需要加上`@NoArgsConstructor`

		@Data
		@RequiredArgsConstructor
		@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
		@Entity
		public class Ingredient {
		  @Id
		  private final String id;
		  private final String name;
		  private final Type type;
		  public static enum Type {
		    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
		}
		}
		
# 4. Securing Spring

1. 添加依赖

		 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
     	 </dependency>
  - All HTTP request paths require authentication.
  - No specific roles or authorities are required.
  - There’s no login page.
  - Authentication is prompted with HTTP basic authentication.  There’s only one user; the username is user.  	 
     	 
     	 
     	 
# 其他
1. [modelemapper](https://stackoverflow.com/questions/44534172/how-to-customize-modelmapper)