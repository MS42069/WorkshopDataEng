package com.fhwedel.softwareprojekt.v1.integration.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.EmployeeTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import com.fhwedel.softwareprojekt.v1.repository.types.EmployeeTypeRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class EmployeeTypeIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /** Autowired converter spares us from having to convert manually between Entity and DTO */
    @Autowired private EmployeeTypeRepository employeeTypeRepository;

    @Autowired private EmployeeTypeConverter employeeTypeConverter;

    @AfterEach
    void rollbackDB() {
        employeeTypeRepository.deleteAll();
    }

    @Test
    void whenPostEmployeeType_thenCorrectResponse() throws Exception {
        EmployeeTypeReqDTO employeeTypeReqDTO =
                EmployeeTypeReqDTO.builder().name("Assistent").build();
        // when
        String url = "/v1/employeeTypes";
        EmployeeTypeResDTO response =
                mockMvcTestUtil.post(url, EmployeeTypeResDTO.class, employeeTypeReqDTO);
        // then
        assertNotNull(response.getId());
        assertEquals(employeeTypeReqDTO.getName(), response.getName());
    }

    @Test
    public void givenEmployeeTypes_whenGetEmployeeTypes_thenReturnListOfEmployeeTypes()
            throws Exception {
        // given
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("typeTEST");

        EmployeeType employeeType2 = new EmployeeType();
        employeeType2.setName("typeTEST2");

        EmployeeType employeeType3 = new EmployeeType();
        employeeType3.setName("typeTEST3");

        employeeType = employeeTypeRepository.saveAndFlush(employeeType);
        employeeType2 = employeeTypeRepository.saveAndFlush(employeeType2);
        employeeType3 = employeeTypeRepository.saveAndFlush(employeeType3);

        // when
        EmployeeTypeResDTO[] response =
                mockMvcTestUtil.get("/v1/employeeTypes", EmployeeTypeResDTO[].class);

        // then
        assertEquals(3, response.length);
        assertEquals(
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType),
                response[0]);
        assertEquals(
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType2),
                response[1]);
        assertEquals(
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType3),
                response[2]);
    }

    @Test
    public void givenEmployeeType_whenGetEmployeeTypeById_thenReturnEmployeeType()
            throws Exception {
        // given

        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("typeTEST");

        employeeType = employeeTypeRepository.saveAndFlush(employeeType);

        EmployeeTypeResDTO expected =
                employeeTypeConverter.convertEmployeeTypeEntityToResponseDTO(employeeType);

        // when
        String url = format("/v1/employeeTypes/%s", employeeType.getId());
        EmployeeTypeResDTO response = mockMvcTestUtil.get(url, EmployeeTypeResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenEmployeeType_whenPatchEmployeeType_thenResponseOk() throws Exception {
        // given
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("typeTEST");

        employeeType = employeeTypeRepository.saveAndFlush(employeeType);

        EmployeeTypeReqDTO requestDTO = EmployeeTypeReqDTO.builder().name("updatedType").build();

        // when
        String url = format("/v1/employeeTypes/%s", employeeType.getId());
        EmployeeTypeResDTO response =
                mockMvcTestUtil.patch(url, EmployeeTypeResDTO.class, requestDTO);

        // then
        EmployeeType stored =
                employeeTypeRepository.findById(employeeType.getId()).orElse(new EmployeeType());
        assertThat(stored.getName()).isEqualTo("updatedType");
        assertEquals(requestDTO.getName(), response.getName());
    }

    @Test
    public void givenEmployeeType_whenDeleteEmployeeType_thenNoContentResponse() throws Exception {
        // given
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("typeTEST");

        employeeType = employeeTypeRepository.saveAndFlush(employeeType);

        // when
        String url = format("/v1/employeeTypes/%s", employeeType.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        // then
        assertThat(employeeTypeRepository.existsById(employeeType.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingEmployeeType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/employeeTypes/%s", UUID.randomUUID());
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingEmployeeType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/employeeTypes/%s", UUID.randomUUID());
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingEmployeeType_thenNotFoundResponse() throws Exception {

        EmployeeTypeReqDTO employeeTypeReqDTO = EmployeeTypeReqDTO.builder().name("Test").build();

        String url = format("/v1/employeeTypes/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, employeeTypeReqDTO)).andExpect(status().isNotFound());
    }

    @Test
    void givenEmployeeType_whenPostEmployeeTypeWithNonUniqueName_thenBadRequestResponse()
            throws Exception {
        // given
        EmployeeType employeeType = new EmployeeType();
        employeeType.setName("typeTEST");

        employeeTypeRepository.saveAndFlush(employeeType);

        EmployeeTypeReqDTO employeeTypeReqDTO =
                EmployeeTypeReqDTO.builder().name("typeTEST").build();

        String url = "/v1/employeeTypes";
        mockMvc.perform(post(url, employeeTypeReqDTO)).andExpect(status().is4xxClientError());
    }
}
