package com.qht.crm.security;

import com.qht.crm.service.EmployeeService;
import com.qht.crm.service.RefreshTokenService;
import com.qht.crm.utils.RequestLoggingFilter;
import com.qht.crm.security.jwt.JwtConfiguration;
import com.qht.crm.security.jwt.JwtUsernameAndPasswordAuthorizationFilter;
import com.qht.crm.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKey;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    private final JwtConfiguration jwtConfiguration;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private RequestLoggingFilter requestLoggingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println(">>> SecurityConfig: Starting configure(HttpSecurity)");

        JwtUsernameAndPasswordAuthenticationFilter authenticationFilter =
            new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, employeeService, refreshTokenService, secretKey, "");

        JwtUsernameAndPasswordAuthorizationFilter authorizationFilter =
            new JwtUsernameAndPasswordAuthorizationFilter(secretKey, jwtConfiguration);

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(management -> management
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Register RequestLoggingFilter BEFORE JwtUsernameAndPasswordAuthenticationFilter
            .addFilterBefore(requestLoggingFilter, JwtUsernameAndPasswordAuthenticationFilter.class)
            // Register JWT Authentication Filter
            .addFilter(authenticationFilter)
            // Register JWT Authorization Filter AFTER Authentication Filter
            .addFilterAfter(authorizationFilter, JwtUsernameAndPasswordAuthenticationFilter.class);

        System.out.println(">>> SecurityConfig: Filters registered successfully");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        System.out.println(">>> SecurityConfig: Configuring DaoAuthenticationProvider");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(employeeService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(">>> SecurityConfig: Configuring AuthenticationManagerBuilder");
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    CorsConfigurationSource corsConfigurationSource() {
        System.out.println(">>> SecurityConfig: Creating CorsConfigurationSource");
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowOrigins = Arrays.asList(
            "http://app.qht.com", "http://app.qht.com:8080", "http://app.qht.com:8081",
            "https://app.qht.com", "https://app.qht.com:8080",
            "https://www.app.qht.com", "https://www.app.qht.com:8080", "http://localhost:4200",
            "https://telephony.hairmedindia.com"
        );
        configuration.setAllowedOrigins(allowOrigins);
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        System.out.println(">>> SecurityConfig: Configuring WebSecurity ignoring patterns");
        web.ignoring().antMatchers("/api/v1/whatsappwebhook/**");
        web.ignoring().antMatchers("/api/v1/organization-app/**");
    }
}
