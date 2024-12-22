package com.fhwedel.softwareprojekt.v1;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fhwedel.softwareprojekt.v1.service.UserService;
import com.fhwedel.softwareprojekt.v1.util.AuthUtil;
import com.fhwedel.softwareprojekt.v1.util.JwtUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
public class SoftwareprojektApplicationIntegrationConfiguration {
    @Bean
    public static MockMvc mockMvc(WebApplicationContext context) {
        return MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Bean
    JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    AuthUtil authUtil() {
        return new AuthUtil();
    }

    @Bean
    UserService userService() {
        return new UserService();
    }
}
