package com.fhwedel.softwareprojekt.v1.integration;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.delete;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.dto.CredentialsDTO;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@Transactional
@Sql(scripts = "/user.sql")
public class AuthIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void whenLoginSuccessfully_thenResponseWithUser() throws Exception {

        // given
        CredentialsDTO credentials =
                CredentialsDTO.builder().username("test_account").password("test").build();

        // when
        mockMvc.perform(post("/v1/auth/login-planer", credentials))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("ACCESS_TOKEN"))
                .andExpect(jsonPath("$.displayName", is("Test")));
    }

    @Test
    void whenLoginFails_thenResponseWithUnauthorized() throws Exception {
        // given
        CredentialsDTO credentials =
                CredentialsDTO.builder().username("test_account").password("test2").build();

        // when
        mockMvc.perform(post("/v1/auth/login-planer", credentials))
                .andExpect(cookie().doesNotExist("ACCESS_TOKEN"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void whenLogout_thenDeleteAccessToken() throws Exception {
        // given
        CredentialsDTO credentials =
                CredentialsDTO.builder().username("test_account").password("test").build();

        Cookie accessToken =
                mockMvc.perform(post("/v1/auth/login-planer", credentials))
                        .andExpect(cookie().value("ACCESS_TOKEN", is(not(emptyString()))))
                        .andReturn()
                        .getResponse()
                        .getCookie("ACCESS_TOKEN");

        // when
        mockMvc.perform(delete("/v1/auth/logout").cookie(accessToken))
                .andExpect(cookie().value("ACCESS_TOKEN", ""))
                .andExpect(status().isOk());
    }
}
