package com.project.blog.config;

import com.project.blog.security.CustomerUserDetailsService;
import com.project.blog.security.JwtAuthenticationEnteryPoint;
import com.project.blog.security.JwtAuthenticationFilter;
import com.project.blog.security.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class SecurityConfig{

    private CustomerUserDetailsService customerUserDetailsService;
    private JwtAuthenticationEnteryPoint jwtAuthenticationEnteryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private String publicUrls [] = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-config/**"

    };


    @Autowired
    public SecurityConfig(CustomerUserDetailsService customerUserDetailsService, JwtAuthenticationEnteryPoint jwtAuthenticationEnteryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.jwtAuthenticationEnteryPoint = jwtAuthenticationEnteryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authz -> authz.requestMatchers(publicUrls).permitAll()
                                .requestMatchers(HttpMethod.GET).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandlingConfigurer-> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(this.jwtAuthenticationEnteryPoint);
                })
                .sessionManagement(sessionManagementConfigurer -> {
                    sessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        http
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customerUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}