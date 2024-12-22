package com.fhwedel.softwareprojekt.v1.integration.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.SemesterTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
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
public class SemesterTypeIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Autowired private SemesterTypeConverter semesterTypeConverter;

    @AfterEach
    void rollbackDB() {
        semesterTypeRepository.deleteAll();
    }

    @Test
    void whenPostSemesterType_thenCorrectResponse() throws Exception {
        SemesterTypeReqDTO semesterTypeReqDTO =
                SemesterTypeReqDTO.builder().name("Wintersemester").build();
        // when
        String url = "/v1/semesterTypes";
        SemesterTypeResDTO response =
                mockMvcTestUtil.post(url, SemesterTypeResDTO.class, semesterTypeReqDTO);
        // then
        assertNotNull(response.getId());
        assertEquals(semesterTypeReqDTO.getName(), response.getName());
    }

    @Test
    public void givenSemesterTypes_whenGetSemesterTypes_thenReturnListOfSemesterTypes()
            throws Exception {
        // given
        SemesterType semesterType1 = new SemesterType();
        semesterType1.setName("typeTEST");

        SemesterType semesterType2 = new SemesterType();
        semesterType2.setName("typeTEST2");

        SemesterType semesterType3 = new SemesterType();
        semesterType3.setName("typeTEST3");

        semesterType1 = semesterTypeRepository.saveAndFlush(semesterType1);
        semesterType2 = semesterTypeRepository.saveAndFlush(semesterType2);
        semesterType3 = semesterTypeRepository.saveAndFlush(semesterType3);

        // when
        SemesterTypeResDTO[] response =
                mockMvcTestUtil.get("/v1/semesterTypes", SemesterTypeResDTO[].class);

        // then
        assertEquals(3, response.length);
        assertEquals(
                semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType1),
                response[0]);
        assertEquals(
                semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType2),
                response[1]);
        assertEquals(
                semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType3),
                response[2]);
    }

    @Test
    public void givenSemesterType_whenGetSemesterTypeById_thenReturnSemesterType()
            throws Exception {
        // given

        SemesterType semesterType1 = new SemesterType();
        semesterType1.setName("typeTEST");

        semesterType1 = semesterTypeRepository.saveAndFlush(semesterType1);

        SemesterTypeResDTO expected =
                semesterTypeConverter.convertSemesterTypeEntityToResponseDTO(semesterType1);

        // when
        String url = format("/v1/semesterTypes/%s", semesterType1.getId());
        SemesterTypeResDTO response = mockMvcTestUtil.get(url, SemesterTypeResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenSemesterType_whenPatchSemesterType_thenResponseOk() throws Exception {
        // given
        SemesterType semesterType = new SemesterType();
        semesterType.setName("typeTEST");

        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        SemesterTypeReqDTO requestDTO = SemesterTypeReqDTO.builder().name("updatedType").build();

        // when
        String url = format("/v1/semesterTypes/%s", semesterType.getId());
        SemesterTypeResDTO response =
                mockMvcTestUtil.patch(url, SemesterTypeResDTO.class, requestDTO);

        // then
        SemesterType storedSemesterType =
                semesterTypeRepository.findById(semesterType.getId()).orElse(new SemesterType());
        assertThat(storedSemesterType.getName()).isEqualTo("updatedType");
        assertEquals(requestDTO.getName(), response.getName());
    }

    @Test
    public void givenSemesterType_whenDeleteSemesterType_thenNoContentResponse() throws Exception {
        // given
        SemesterType semesterType = new SemesterType();
        semesterType.setName("typeTEST");

        semesterType = semesterTypeRepository.saveAndFlush(semesterType);

        // when
        String url = format("/v1/semesterTypes/%s", semesterType.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        // then
        assertThat(semesterTypeRepository.existsById(semesterType.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingSemesterType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/semesterTypes/%s", UUID.randomUUID());
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingSemesterType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/semesterTypes/%s", UUID.randomUUID());
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingSemesterType_thenNotFoundResponse() throws Exception {

        SemesterTypeReqDTO semesterTypeReqDTO = SemesterTypeReqDTO.builder().name("Winter").build();

        String url = format("/v1/semesterTypes/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, semesterTypeReqDTO)).andExpect(status().isNotFound());
    }

    @Test
    void givenRoom_whenPostSemesterTypeWithNonUniqueName_thenBadRequestResponse() throws Exception {
        // given
        SemesterType semesterType = new SemesterType();
        semesterType.setName("typeTEST");

        semesterTypeRepository.saveAndFlush(semesterType);

        SemesterTypeReqDTO newSemesterType = SemesterTypeReqDTO.builder().name("typeTEST").build();

        String url = "/v1/semesterTypes";
        mockMvc.perform(post(url, newSemesterType)).andExpect(status().is4xxClientError());
    }
}
