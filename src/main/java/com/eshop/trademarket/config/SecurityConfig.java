package com.eshop.trademarket.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login","/logout","/register/**").permitAll() 
            .antMatchers("/api-ui/**", "/api/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() 
            .antMatchers("/shop/**","/status/**").hasRole("SHOP")
            .antMatchers("/home/**").hasAnyRole("USER","SHOP")
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                // user is not logged in (401 Unauthorized)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Login Required");
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                // User with wrong Role (403 Forbidden)
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden - Insufficient Permissions");
            })
            .and()
            .logout().disable()
            .httpBasic();
            
        return http.build();
    }
}