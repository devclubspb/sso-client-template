package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@EnableOAuth2Sso
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(value = 3)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {//@formatter:off
        http
                .sessionManagement()
                    .maximumSessions(100)
                        .sessionRegistry(sessionRegistry())
                        .expiredSessionStrategy(getSessionInformationExpiredStrategy())
                        .and()
                    .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/login**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin();
    }//@formatter:on

    private SessionInformationExpiredStrategy getSessionInformationExpiredStrategy() {
        return event -> {
            HttpServletRequest request = event.getRequest();
            HttpServletResponse response = event.getResponse();
            request.getSession();// creates a new session
            response.sendRedirect(request.getContextPath());
        };
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
