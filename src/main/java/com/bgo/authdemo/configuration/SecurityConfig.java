package com.bgo.authdemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity(debug = false)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/favicon.ico", "/webjars/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(
                new OrRequestMatcher(
                        new AntPathRequestMatcher("/"), // index page for public access
                        new AntPathRequestMatcher("/cfhealth"), // public cloudfoundry health endpoint
                        new AntPathRequestMatcher("/error"),
                        new AntPathRequestMatcher("/perf/**")
                ))
                .authorizeRequests().anyRequest().permitAll();
    }

}