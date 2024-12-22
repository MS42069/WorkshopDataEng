package com.fhwedel.softwareprojekt.v1;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fhwedel.softwareprojekt.v1.repository.UserRepository;
import com.fhwedel.softwareprojekt.v1.service.UserService;
import com.fhwedel.softwareprojekt.v1.util.AuthUtil;
import com.fhwedel.softwareprojekt.v1.util.JwtUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
public class SoftwareprojektApplicationConfiguration {
    @MockBean JwtUtil jwtUtil;

    @MockBean AuthUtil authUtil;

    @MockBean UserService userService;

    @MockBean UserRepository userRepository;

    @Bean
    public static MockMvc mockMvc(WebApplicationContext context) {
        return MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
}
