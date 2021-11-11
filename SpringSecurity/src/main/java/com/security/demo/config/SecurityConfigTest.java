package com.security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 第三种方式设置SpringSecurity的用户名和密码
 * <p>
 * 1.编写UserDetailsService实现类，返回User对象，User对象有用户名和操作权限
 * 2.编写配置类去继承WebSecurityConfigurerAdapter类并重写里面的方法
 *
 * @author admin
 */
@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//该注解的作用是开启Springsecurity权限授权的注解
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

//    注入数据源用于SpringSecurity对数据库进行操作
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

//        这是在开启时创建在对应的数据源的数据库中创建相应用于存储cookie的表,用过一次之后需要关闭
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    /**
     * 配置哪个用户,参数是将userDetailsService写入到SpringSecurity中
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 配置一些访问时的参数
     * 例如:权限设置授权规则等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //修改403页面,当用户访问某个资源没有权限时,跳转到该页面提示用户没有访问权限,该方法需要在Controller层添加对应的控制器
        http.exceptionHandling().accessDeniedPage("/nopermission");


        //在配置类中添加退出配置
        http.logout().logoutUrl("/logout").
        //配置退出之后跳转的页面
        logoutSuccessUrl("/index").permitAll();

        //自定义自己编写的登陆页面
        http.formLogin()
                //登陆页面设置
                .loginPage("/login")
                //登陆访问路径
                .loginProcessingUrl("/user/login")
                //登陆成功之后跳转的页面
                .defaultSuccessUrl("/index").permitAll()
//                配置信息输入有误时跳转的页面
                .failureUrl("/userLogin?error")
                //设置提交表单中用户名的name属性值,默认为username
                .usernameParameter("username")
                //设置提交表单中密码的name属性值,默认为password
                .passwordParameter("password")
                .and().authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                //如果当前的主体具有指定的权限，则返回true，否则返回false,该方法适合单个资源进行授权
                .antMatchers("/show").hasAuthority("admin")
                .antMatchers("/worker").hasAuthority("worker")
                //如果当前的主体具有指定的权限，则返回true，否则返回false,该方法适合多个资源进行授权
                .antMatchers("/message").hasAnyAuthority("admin,worker")

//                .antMatchers("/message").hasAnyRole("admin")
                .anyRequest().authenticated()
//                在配置类中配置自动登录功能的配置
                .and().rememberMe().tokenRepository(persistentTokenRepository())
//                设置自动登录的有效时长,单位为秒
                .tokenValiditySeconds(600)
                .userDetailsService(userDetailsService)
                .and().csrf().disable();
    }
}
