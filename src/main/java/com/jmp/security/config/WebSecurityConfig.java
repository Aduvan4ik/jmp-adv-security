package com.jmp.security.config;


import com.jmp.security.handler.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.jmp.security.entity.enums.Authorities.VIEW_ADMIN;
import static com.jmp.security.entity.enums.Authorities.VIEW_INFO;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAuthenticationFailureHandler customAuthenticationFailureHandler) throws Exception {
        return http.authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .antMatchers(HttpMethod.GET, "/about", "/login*", "/blocked").permitAll()
                                .antMatchers(HttpMethod.GET, "/info").hasAuthority(VIEW_INFO.name())
                                .antMatchers(HttpMethod.GET, "/admin", "/admin/**").hasAuthority(VIEW_ADMIN.name())
                                .antMatchers(HttpMethod.POST, "/admin/**").hasAuthority(VIEW_ADMIN.name())
                                .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll())
                .logout(formLogout -> formLogout
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logoutSuccess")
                        .permitAll())
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
