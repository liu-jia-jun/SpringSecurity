 # SpringSecurity认证授权

## 笔记

### 设置用户名和密码的三种方式

#### 第一种：
直接在application.properties配置文件中设置用户名和密码

#### 第二种：
创建一个config类去继承WebSecurityConfigurerAdapter类,重写configure方法

#### 第三种：
##### 1.创建配置类，设置使用哪个userDetailsService实现类

##### 2.编写实现类，返回User对象，User对象有用户名和操作权限

##### 3.自定义实现类设置:
    1.编写UserDetailsService实现类，返回User对象，User对象有用户名和操作权限
    2.编写配置类去继承WebSecurityConfigurerAdapter类并重写里面的方法


### 授权注解

#### 使用SpringSecurity注解需要添加@EnableGlobalMethodSecurity(securedEnabled = true , prePostEnabled = true) 该注解的作用是开启Springsecurity权限授权的注解
@EnableGlobalMethodSecurity(securedEnabled = true , prePostEnabled = true)这个注解可以加在启动类中，也可以加配置类中

#### @Secured({"ROLE_","ROLE_"})
在Controller方法中添加,匹配用户是否有该方法的访问权限

####  @PreAuthorize("hasAnyAuthority('admin')")
在Controller方法中添加,匹配用户是否具有该方法的访问权限

### 在配置类中添加退出的配置
1.http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();

2.在某个页面中添加退出连接<a href="/logout" >退出</a>

### 配置自动登录

#### 1.创建SpringSecurity在自动登录功能中使用的数据库
jdbcTokenRepository.setCreateTableOnStartup(true);这条语句会在数据库中创建相应的一张表用来保存Token值
#### 2.注入数据源,表示操作Mysql中的哪个资源
`    @Autowired
     private DataSource dataSource;`
#### 3.配置操作数据库对象
`    @Bean
     public PersistentTokenRepository persistentTokenRepository(){
         JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
         jdbcTokenRepository.setDataSource(dataSource);
 
 //        这是在开启时创建在对应的数据源的数据库中创建相应用于存储cookie的表,用过一次之后需要关闭
 //        jdbcTokenRepository.setCreateTableOnStartup(true);
         return jdbcTokenRepository;
     }`
     
#### 4.添加SpringSecurity中的配置
`//                在配置类中配置自动登录功能的配置
                 .and().rememberMe().tokenRepository(persistentTokenRepository())
 //                设置自动登录的有效时长,单位为秒
                 .tokenValiditySeconds(600)
                 .userDetailsService(userDetailsService)`

### Tips
    1.hasAuthority多个是"与"的关系,hasAnyAuthority多个是"或"的关系
    2.在配置类中使用hasRole或者hasAnyRole等方法时赋予发值不需要添加"ROLE_"前缀，因为在springsecurity中已经添加了不能重复添加
    