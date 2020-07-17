package com.boot.bootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 启用Spring Security的Web安全支持
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 将用户设置在内存中
     * @param auth
     * @throws Exception
     */
    @Autowired
    private AuthenticationProvider provider;  //注入我们自己的AuthenticationProvider

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHander myAuthenticationFailHander;

    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception {
        // 在内存中配置用户，配置多个用户调用`and()`方法
        auth.authenticationProvider(provider);
/*                .passwordEncoder(passwordEncoder()) // 指定加密方式
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
                .and()
                .withUser("test").password(passwordEncoder().encode("123456")).roles("USER");*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder：Spring Security 提供的加密工具，可快速实现加密加盐
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //super.configure(http);
       /* http.authorizeRequests()
                .antMatchers("/login-error") // 不需要登录就可以访问
                .permitAll()
                //.antMatchers("/user/**").hasAnyRole("USER") // 需要具有ROLE_USER角色才能访问
                //.antMatchers("/admin/**").hasAnyRole("ADMIN") // 需要具有ROLE_ADMIN角色才能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // 访问需要登录才能访问的页面，如果未登录，会跳转到该地址来
                .loginProcessingUrl("/login/form")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailHander)*/

        ;
                http
                        .formLogin().loginPage("/login").loginProcessingUrl("/login/form")
                        .successHandler(myAuthenticationSuccessHandler)
                        .failureHandler(myAuthenticationFailHander)
                        .permitAll()  //表单登录，permitAll()表示这个不需要验证 登录页面，登录失败页面
                        .and()
                        .authorizeRequests()
                        .antMatchers("/login-error").permitAll()
                       /* .antMatchers("/index").permitAll()
                        .antMatchers("/whoim").hasRole("USER") //这就表示/whoim的这个资源需要有ROLE_ADMIN的这个角色才能访问。不然就会提示拒绝访问*/
                        .anyRequest().access("@rbacService.hasPermission(request,authentication)")    //必须经过认证以后才能访问
                        .and()
                        //
                        .csrf().disable();
    }


}