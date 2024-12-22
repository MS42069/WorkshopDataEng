package com.fhwedel.softwareprojekt.v1.integration.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.SchoolTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
import com.fhwedel.softwareprojekt.v1.repository.types.SchoolTypeRepository;
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
public class SchoolTypeIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired private SchoolTypeRepository schoolTypeRepository;

    @Autowired private SchoolTypeConverter schoolTypeConverter;

    @AfterEach
    void rollbackDB() {
        schoolTypeRepository.deleteAll();
    }

    @Test
    void whenPostSchoolType_thenCorrectResponse() throws Exception {
        SchoolTypeReqDTO schoolTypeReqDto = SchoolTypeReqDTO.builder().name("VL").build();
        // when
        String url = "/v1/schoolTypes";
        SchoolTypeResDTO response =
                mockMvcTestUtil.post(url, SchoolTypeResDTO.class, schoolTypeReqDto);
        // then
        assertNotNull(response.getId());
        assertEquals(schoolTypeReqDto.getName(), response.getName());
    }

    @Test
    public void givenSchoolTypes_whenGetSchoolTypes_thenReturnListOfSchoolTypes() throws Exception {
        // given
        SchoolType schoolType1 = new SchoolType();
        schoolType1.setName("typeTEST");

        SchoolType schoolType2 = new SchoolType();
        schoolType2.setName("typeTEST2");

        SchoolType schoolType3 = new SchoolType();
        schoolType3.setName("typeTEST3");

        schoolType1 = schoolTypeRepository.saveAndFlush(schoolType1);
        schoolType2 = schoolTypeRepository.saveAndFlush(schoolType2);
        schoolType3 = schoolTypeRepository.saveAndFlush(schoolType3);

        // when
        SchoolTypeResDTO[] response =
                mockMvcTestUtil.get("/v1/schoolTypes", SchoolTypeResDTO[].class);

        // then
        assertEquals(3, response.length);
        assertEquals(schoolTypeConverter.convertEntityToResponseDTO(schoolType1), response[0]);
        assertEquals(schoolTypeConverter.convertEntityToResponseDTO(schoolType2), response[1]);
        assertEquals(schoolTypeConverter.convertEntityToResponseDTO(schoolType3), response[2]);
    }

    @Test
    public void givenSchoolType_whenGetSchoolTypeById_thenReturnSchoolType() throws Exception {
        // given

        SchoolType schoolType = new SchoolType();
        schoolType.setName("typeTEST");

        schoolType = schoolTypeRepository.saveAndFlush(schoolType);

        SchoolTypeResDTO expected = schoolTypeConverter.convertEntityToResponseDTO(schoolType);

        // when
        String url = format("/v1/schoolTypes/%s", schoolType.getId());
        SchoolTypeResDTO response = mockMvcTestUtil.get(url, SchoolTypeResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenSchoolType_whenPatchSchoolType_thenResponseOk() throws Exception {
        // given
        SchoolType schoolType = new SchoolType();
        schoolType.setName("typeTEST");

        schoolType = schoolTypeRepository.saveAndFlush(schoolType);

        SchoolTypeReqDTO requestDTO = SchoolTypeReqDTO.builder().name("updatedType").build();

        // when
        String url = format("/v1/schoolTypes/%s", schoolType.getId());
        SchoolTypeResDTO response = mockMvcTestUtil.patch(url, SchoolTypeResDTO.class, requestDTO);

        // then
        SchoolType storedSchoolType =
                schoolTypeRepository.findById(schoolType.getId()).orElse(new SchoolType());
        assertThat(storedSchoolType.getName()).isEqualTo("updatedType");
        assertEquals(requestDTO.getName(), response.getName());
    }

    @Test
    public void givenSchoolType_whenDeleteSchoolType_thenNoContentResponse() throws Exception {
        // given
        SchoolType schoolType = new SchoolType();
        schoolType.setName("typeTEST");

        schoolType = schoolTypeRepository.saveAndFlush(schoolType);

        // when
        String url = format("/v1/schoolTypes/%s", schoolType.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        // then
        assertThat(schoolTypeRepository.existsById(schoolType.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingSchoolType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/schoolTypes/%s", UUID.randomUUID());
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingSchoolType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/schoolTypes/%s", UUID.randomUUID());
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingSchoolType_thenNotFoundResponse() throws Exception {

        SchoolTypeReqDTO schoolTypeReqDto = SchoolTypeReqDTO.builder().name("Schule").build();

        String url = format("/v1/schoolTypes/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, schoolTypeReqDto)).andExpect(status().isNotFound());
    }

    @Test
    void givenSchool_whenPostSchoolWithNonUniqueName_thenBadRequestResponse() throws Exception {
        // given
        SchoolType schoolType = new SchoolType();
        schoolType.setName("typeTEST");

        schoolTypeRepository.saveAndFlush(schoolType);

        SchoolTypeReqDTO newSchoolType = SchoolTypeReqDTO.builder().name("typeTEST").build();

        String url = "/v1/schoolTypes";
        mockMvc.perform(post(url, newSchoolType)).andExpect(status().is4xxClientError());
    }
}
