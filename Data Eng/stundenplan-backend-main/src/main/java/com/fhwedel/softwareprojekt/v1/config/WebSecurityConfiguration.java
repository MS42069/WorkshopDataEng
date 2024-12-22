package com.fhwedel.softwareprojekt.v1.config;

import com.fhwedel.softwareprojekt.v1.filter.AuthFilter;
import com.fhwedel.softwareprojekt.v1.handler.AuthEntryPointJwt;
import com.fhwedel.softwareprojekt.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Configuration class for web security settings, including authentication and authorization.
 */
@Configuration
public class WebSecurityConfiguration {

    /**
     * The entry point for handling unauthorized access.
     */
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * The user service for managing user details.
     */
    @Autowired
    private UserService userService;

    /**
     * Creates and configures an {@link AuthFilter} bean for handling JWT token authentication.
     *
     * @return a configured instance of {@link AuthFilter}
     */
    @Bean
    public AuthFilter authenticationJwtTokenFilter() {
        return new AuthFilter();
    }

    /**
     * Creates and configures a password encoder bean for encoding and decoding passwords.
     *
     * @return a configured instance of {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private final List<String> whitelist =
            List.of(
                    "/v1/auth/login-planer",
                    "/v1/auth/logout",
                    "/api/v1/wishes-app-activation",
                    "/api/v1/auth/login",
                    "/api/v1/auth/login-planer",
                    "/api/v1/auth/csrf",
                    "/api/v1/auth/logout",
                    "/api/swagger-ui/**",
                    "/api/docs",
                    "/api/v3/api-docs/swagger-config",
                    "/api/v3/api-docs");

    /**
     * Creates and configures a DAO authentication provider bean for authenticating users.
     *
     * @return a configured instance of {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder());

        return authProvider;
    }

    /**
     * Creates and configures an authentication manager bean.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return a configured instance of {@link AuthenticationManager}
     * @throws Exception if an exception occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http the HTTP security configuration
     * @return a configured instance of {@link SecurityFilterChain}
     * @throws Exception if an exception occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(whitelist.toArray(new String[0]))
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler);

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(
                authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
