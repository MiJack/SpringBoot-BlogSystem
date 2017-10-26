package com.mijack.sbbs.config;

import com.mijack.sbbs.auth.filter.RestfulApiAuthenticationProcessingFilter;
import com.mijack.sbbs.auth.handler.LoginAuthenticationSuccessHandler;
import com.mijack.sbbs.auth.provider.FormAuthenticationProvider;
import com.mijack.sbbs.auth.provider.RestfulApiAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    RestfulApiAuthenticationProvider restfulApiAuthenticationProvider;
    @Autowired
    FormAuthenticationProvider formAuthenticationProvider;
    @Autowired
    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index.html").permitAll()
                .antMatchers("/login.html", "/register.html", "/search.html").permitAll()
                .antMatchers("/api/**").permitAll()
        ;
        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login.html?error")
                .successHandler(loginAuthenticationSuccessHandler)
                .authenticationDetailsSource(request -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("username", request.getParameter("username"));
                    map.put("email", request.getParameter("email"));
                    map.put("password", request.getParameter("password"));
                    return map;
                })
                .and()
                .rememberMe().key("remember-me")
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        ;
        //api接口不做csrf处理
        http.csrf().ignoringAntMatchers("/api/**");
        http.addFilterBefore(restfulApiAuthenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(restfulApiAuthenticationProvider)
                .authenticationProvider(formAuthenticationProvider)
        .userDetailsService(userDetailsService);
    }

    RestfulApiAuthenticationProcessingFilter restfulApiAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        RestfulApiAuthenticationProcessingFilter filter = new RestfulApiAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

}
