# MicroService

## spring cloud version Greenwich.M3 & spring boot 2.1.1.RELEASE

### 配置信息

	spring.datasource.url=jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false
	spring.datasource.username=springstudent
	spring.datasource.password=springstudent
	spring.jpa.show-sql=true
	## Hibernate Properties
	# The SQL dialect makes Hibernate generate better SQL for the chosen database
	spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
	# Hibernate ddl auto (create, create-drop, validate, update)
	spring.jpa.hibernate.ddl-auto = update


### 1.服务注册与发现(eureka)

#### eureka server 配置	

> `@EnableEurekaServer`

> application.properties
	
	spring.application.name=eureka-server
	server.port=8761
	eureka.client.register-with-eureka=false
	eureka.client.fetch-registry=false
		

> pom.xml
 
	<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>

#### eureka client 配置

- 本地调试最好设置`eureka.instance.hostname = localhost`,不然可能出现通过ip服务访问不到的情况

> `@EnableEurekaClient`

> pom.xml

	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	</dependency>
	
> application.properties
	
	eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
	eureka.instance.hostname=localhost
		

### 2.服务消费(feign)
1. 一般使用feign进行服务间通讯

	- Feign 采用的是基于接口的注解
	- Feign 整合了ribbon，具有负载均衡的能力
	- 整合了Hystrix，具有熔断的能力
	
			  <dependency>
			        <groupId>org.springframework.cloud</groupId>
			        <artifactId>spring-cloud-starter-openfeign</artifactId>
			  </dependency>


### 3.网关(zuul)
> `application`添加注解`@EnableZuulProxy`
 
> pom.xml
	
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
	</dependency>
	
> application.properties
	
	server.port=8080
	spring.application.name=gateway-service
	
	eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
	eureka.instance.hostname=localhost
	
	zuul.routes.api-router.path=/api/**
	zuul.routes.api-router.serviceId=user-service

### 4.配置中心(spring cloud config)

- http://localhost:8888/user/dev 测试是否能正常访问

#### config server

> `application`添加注解`@EnableConfigServer`

> pom.xml

	<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>

> application.properties
	
	
	spring.application.name=config-server
	server.port=8888
	eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
	eureka.instance.hostname=localhost
	
	spring.cloud.config.server.git.uri=https://github.com/forezp/SpringcloudConfig/
	spring.cloud.config.server.git.searchPaths=respo
	spring.cloud.config.label=master
	spring.cloud.config.server.git.username=
	spring.cloud.config.server.git.password=

#### config client

> pom.xml
	
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>

> 需要配置bootstrap.properties
 
	spring.application.name=config-client
	server.port=8771
	eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

	spring.cloud.config.label=master
	spring.cloud.config.profile=dev
	spring.cloud.config.uri= http://localhost:8888/

### 5.消息总线(Spring Cloud Bus)

- 先使用`homebrew install rabbitmq`,然后使用`brew services start rabbitmq`启动rabbitmq.`http://localhost:15672/#/`查看是否启动成功。 其server端口为`5672`

	
> pom.xml

	<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
   	</dependency>
   	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
   	
> 配置application.properties
		
	spring.rabbitmq.host=localhost
	spring.rabbitmq.port=5672
	spring.rabbitmq.username=guest
	spring.rabbitmq.password=guest
	
	spring.cloud.bus.enabled=true
	spring.cloud.bus.trace.enabled=true
	management.endpoints.web.exposure.include=bus-refresh
	
> 刷新配置(post)
	
	http://localhost:8771/actuator/bus-refresh

> /actuator/bus-refresh接口可以指定服务，即使用"destination"参数，比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为customers的所有服务


### 6.服务链路追踪(Spring Cloud Sleuth -zipkin)

#### zipkin server
1. 下载启动zipkin服务, `https://github.com/openzipkin/zipkin`

	> java -jar zipkin.jar

2. 访问 `localhost:9494`进入管理界面

#### 追踪
> pom.xml	 其中包含了`spring-cloud-starter-sleuth`
		
		
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-zipkin</artifactId>
	</dependency>
	
> 配置application.properties
	
	spring.zipkin.base-url=http://localhost:9411
	#配置上传样本比例
	spring.sleuth.sampler.probability=1

### 7.OAuth2 & JWT
- [OAuth2 Using JWT](./OAuth2-jwt.md)
