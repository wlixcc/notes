# OAuth2 Using JWT
### 1.配置auth-service

> pom.xml

     <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
    
> WebSecurityConfig 继承 `WebSecurityConfigurerAdapter`

		@Configuration
	@EnableWebSecurity
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .csrf().disable()
	                // disable form authentication
	                .formLogin().disable()
	                // disable anonymous user
	                .anonymous().disable()
	                .authorizeRequests().anyRequest().denyAll();
	
	
	
	    }
	
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
	        auth.inMemoryAuthentication()
	                .withUser("user")
	                .password("{noop}password").roles("USER")
	                .and()
	                .withUser("admin")
	                .password("{noop}password").authorities("ROLE_ADMIN");
	    }
	}

> `AuthorizationConfig` 继承 `AuthorizationServerConfigurerAdapter`
		
		
	@Configuration
	@EnableAuthorizationServer
	public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	
	    private int accessTokenValiditySeconds = 3600 * 24 * 7;
	    private int refreshTokenValiditySeconds = accessTokenValiditySeconds*3;
	
	    /**
	     * 只有配置了这个Bean才会开启密码类型的验证
	     */
	    @Autowired
	    @Qualifier("authenticationManagerBean")
	    private AuthenticationManager authenticationManager;
	
	    /**
	     * 配置tokenStore和authenticationManager
	     * @param endpoints
	     * @throws Exception
	     */
	    @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints.authenticationManager(authenticationManager);
	    }
	
	    /**
	     * 配置客户端信息
	     * @param clients
	     * @throws Exception
	     */
	    @Override
	    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	                clients.inMemory()
	                .withClient("test-client")
	                        .secret("{noop}123456")
	                .authorizedGrantTypes("password", "refresh_token")
	                .scopes("read", "write")
	                .accessTokenValiditySeconds(accessTokenValiditySeconds)
	                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
	    }
	}
	
> 发送请求测试
	
	curl test-client:123456@localhost:9999/oauth/token -d "grant_type=password&username=user&password=password&client_id=test-client"

> 成功返回
	
	{"access_token":"9da8cc8f-5327-40c4-8bab-d069e1e197f8","token_type":"bearer","refresh_token":"bad7a37d-1f57-41c5-b1e2-e290460fe8eb","expires_in":604314,"scope":"read write"}
	

#### 2.JWT
1. 对称加密, 在`AuthorizationConfig`类中配置`tokenStore`(使得refresh_token起效),`JwtAccessTokenConverter`,`DefaultTokenServices`
	
	
		@Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(jwtAccessTokenConverter());
	    }
	
	    @Bean
	    public JwtAccessTokenConverter jwtAccessTokenConverter(){
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        converter.setSigningKey("123456");
	        return converter;
	    }
	
	    @Bean
	    @Primary
	    public DefaultTokenServices tokenServices () {
	        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        defaultTokenServices.setSupportRefreshToken(true);
	        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
	        return defaultTokenServices;
	    }
	    
	     @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints
	                .authenticationManager(authenticationManager)
	                .tokenServices(tokenServices())
	                .tokenStore(tokenStore())
	                .accessTokenConverter(jwtAccessTokenConverter());
	    }
		
2. 非对称加密

>  `keytool -genkeypair -alias test-jwt -keyalg RSA -keypass 123456 -keystore test-jwt.jks -storepass 123456` 生成jks文件

> `keytool -list -rfc --keystore test-jwt.jks | openssl x509 -inform pem -pubkey` 导出公钥

 1. 将生成的jks放在`main/resources/static`目录下,调整`JwtAccessTokenConverter`部分代码
 	
	 	
	 	@Bean
	    public JwtAccessTokenConverter jwtAccessTokenConverter(){
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
	                new ClassPathResource("/static/test-jwt.jks"),
	                "123456".toCharArray()
	        );
	        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("test-jwt"));
	        return converter;
	    }	
	    
### 2.Resource Server
1. 在`AuthorizationConfig`提供`TokenStore`bean 和`JwtAccessTokenConverter`bean
		
			@Configuration
		public class AuthorizationConfig {
		
		    @Autowired
		    JwtAccessTokenConverter jwtAccessTokenConverter;
		
		    @Bean
		    public TokenStore tokenStore() {
		        return new JwtTokenStore(jwtAccessTokenConverter);
		    }
		
		    @Bean
		    protected JwtAccessTokenConverter jwtAccessTokenConverter(){
		        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		        Resource resource = new ClassPathResource("static/public.cert");
		
		        String publicKey;
		        try {
		            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
		        converter.setVerifierKey(publicKey);
		        return converter;
		    }	
		}
		
2. 配置 `ResourceServerConfig` 继承`ResourceServerConfigurerAdapter`

		@Configuration
		@EnableResourceServer
		public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
		
		    @Autowired
		    TokenStore tokenStore;
		
		
		    @Override
		    public void configure(HttpSecurity http) throws Exception {
		        http.csrf().disable()
		                .authorizeRequests()
		                .antMatchers("/user/login","/user/register").permitAll()
		                .antMatchers("/**").authenticated();
		    }
		
		    @Override
		    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		        resources
		                .tokenStore(tokenStore);
		    }
		}
		
3. 使用获取到的accesstoken访问资源服务器进行测试

4. Method Security
	
		@EnableGlobalMethodSecurity(prePostEnabled=true)
		public class ResourceConfig extends ResourceServerConfigurerAdapter {
		 //...
		}
		
		@PreAuthorize("hasRole('USER')")
	    @GetMapping("/hello")
	    public String hello() {
	        return "hello";
	    }

### 3. Using Database on the Oauth2 Authentication Process
1. 参考示例工程[auth-demo](./auth-demo),需要注意的是, 配置client secret 需要encoder password

		@Override
	    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	                clients.inMemory()
	                .withClient("test-client")
	                        .secret(passwordEncoder.encode("123456"))
	                .authorizedGrantTypes("password", "refresh_token")
	                .scopes("read", "write")
	                .accessTokenValiditySeconds(accessTokenValiditySeconds)
	                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
	    }
	    


