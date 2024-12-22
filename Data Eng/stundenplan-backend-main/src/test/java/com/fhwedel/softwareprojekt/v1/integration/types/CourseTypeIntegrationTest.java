package com.fhwedel.softwareprojekt.v1.integration.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.CourseTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import com.fhwedel.softwareprojekt.v1.repository.types.CourseTypeRepository;
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
public class CourseTypeIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired private CourseTypeRepository courseTypeRepository;

    @Autowired private CourseTypeConverter courseTypeConverter;

    @AfterEach
    void rollbackDB() {
        courseTypeRepository.deleteAll();
    }

    @Test
    void whenPostCourseType_thenCorrectResponse() throws Exception {
        CourseTypeReqDTO courseTypeReqDto = CourseTypeReqDTO.builder().name("VL").build();
        // when
        String url = "/v1/courseTypes";
        CourseTypeResDTO response =
                mockMvcTestUtil.post(url, CourseTypeResDTO.class, courseTypeReqDto);
        // then
        assertNotNull(response.getId());
        assertEquals(courseTypeReqDto.getName(), response.getName());
    }

    @Test
    public void givenCourseTypes_whenGetCourseTypes_thenReturnListOfCourseTypes() throws Exception {
        // given
        CourseType courseType1 = new CourseType();
        courseType1.setName("typeTEST");

        CourseType courseType2 = new CourseType();
        courseType2.setName("typeTEST2");

        CourseType courseType3 = new CourseType();
        courseType3.setName("typeTEST3");

        courseType1 = courseTypeRepository.saveAndFlush(courseType1);
        courseType2 = courseTypeRepository.saveAndFlush(courseType2);
        courseType3 = courseTypeRepository.saveAndFlush(courseType3);

        // when
        CourseTypeResDTO[] response =
                mockMvcTestUtil.get("/v1/courseTypes", CourseTypeResDTO[].class);

        // then
        assertEquals(3, response.length);
        assertEquals(courseTypeConverter.convertEntityToResponseDTO(courseType1), response[0]);
        assertEquals(courseTypeConverter.convertEntityToResponseDTO(courseType2), response[1]);
        assertEquals(courseTypeConverter.convertEntityToResponseDTO(courseType3), response[2]);
    }

    @Test
    public void givenCourseType_whenGetCourseTypeById_thenReturnCourseType() throws Exception {
        // given

        CourseType courseType = new CourseType();
        courseType.setName("typeTEST");

        courseType = courseTypeRepository.saveAndFlush(courseType);

        CourseTypeResDTO expected = courseTypeConverter.convertEntityToResponseDTO(courseType);

        // when
        String url = format("/v1/courseTypes/%s", courseType.getId());
        CourseTypeResDTO response = mockMvcTestUtil.get(url, CourseTypeResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenCourseType_whenPatchCourseType_thenResponseOk() throws Exception {
        // given
        CourseType courseType = new CourseType();
        courseType.setName("typeTEST");

        courseType = courseTypeRepository.saveAndFlush(courseType);

        CourseTypeReqDTO requestDTO = CourseTypeReqDTO.builder().name("updatedType").build();

        // when
        String url = format("/v1/courseTypes/%s", courseType.getId());
        CourseTypeResDTO response = mockMvcTestUtil.patch(url, CourseTypeResDTO.class, requestDTO);

        // then
        CourseType storedCourseType =
                courseTypeRepository.findById(courseType.getId()).orElse(new CourseType());
        assertThat(storedCourseType.getName()).isEqualTo("updatedType");
        assertEquals(requestDTO.getName(), response.getName());
    }

    @Test
    public void givenCourseType_whenDeleteCourseType_thenNoContentResponse() throws Exception {
        // given
        CourseType courseType = new CourseType();
        courseType.setName("typeTEST");

        courseType = courseTypeRepository.saveAndFlush(courseType);

        // when
        String url = format("/v1/courseTypes/%s", courseType.getId());
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        // then
        assertThat(courseTypeRepository.existsById(courseType.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingCourseType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/courseTypes/%s", UUID.randomUUID());
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingCourseType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/courseTypes/%s", UUID.randomUUID());
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingCourseType_thenNotFoundResponse() throws Exception {

        CourseTypeReqDTO courseTypeReqDto = CourseTypeReqDTO.builder().name("Uebung").build();

        String url = format("/v1/courseTypes/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, courseTypeReqDto)).andExpect(status().isNotFound());
    }

    @Test
    void givenCourse_whenPostCourseWithNonUniqueName_thenBadRequestResponse() throws Exception {
        // given
        CourseType courseType = new CourseType();
        courseType.setName("typeTEST");

        courseTypeRepository.saveAndFlush(courseType);

        CourseTypeReqDTO newCourseType = CourseTypeReqDTO.builder().name("typeTEST").build();

        String url = "/v1/courseTypes";
        mockMvc.perform(post(url, newCourseType)).andExpect(status().is4xxClientError());
    }
}
