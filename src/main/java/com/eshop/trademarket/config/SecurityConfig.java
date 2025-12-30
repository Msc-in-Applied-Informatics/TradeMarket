package com.eshop.trademarket.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
    	//return new BCryptPasswordEncoder();
    	return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login","/register/**").permitAll() 
            .antMatchers("/api-ui/**", "/api/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() 
            .antMatchers("/product/**","/status/**").hasRole("SHOP")
            .antMatchers("/home/**","/logout","/search").hasAnyRole("CITIZEN","SHOP")
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                // 401 Unauthorized  
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                
                Map<String, Object> data = new HashMap<>();
                data.put("status", "error");
                data.put("code", 401);
                data.put("message", "Unauthorized - No active session found");
                
                response.getWriter().write(objectMapper.writeValueAsString(data));
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                // 403 Forbidden 
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                
                Map<String, Object> data = new HashMap<>();
                data.put("status", "error");
                data.put("code", 403);
                data.put("message", "Forbidden - Insufficient Permissions");
                
                response.getWriter().write(objectMapper.writeValueAsString(data));
            })
            .and()
            .logout().disable()
            .httpBasic();
            
        return http.build();
    }
}