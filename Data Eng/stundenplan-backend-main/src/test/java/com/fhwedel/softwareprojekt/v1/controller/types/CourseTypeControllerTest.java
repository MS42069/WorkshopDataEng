package com.fhwedel.softwareprojekt.v1.controller.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.CourseTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.CourseTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.service.types.CourseTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CourseTypeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class CourseTypeControllerTest {
    @MockBean private CourseTypeService courseTypeService;
    @MockBean private CourseTypeConverter courseTypeConverter;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidCourseType_thenCorrectResponse() throws Exception {
        CourseTypeReqDTO courseTypeReqDto =
                CourseTypeReqDTO.builder().name("Vorlesung *schnarch*").build();

        when(courseTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/courseTypes";
        mockMvc.perform(post(url, courseTypeReqDto)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndBlankCourseType_thenBadRequestResponse() throws Exception {
        CourseTypeReqDTO courseTypeReqDto = CourseTypeReqDTO.builder().name("").build();

        when(courseTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/courseTypes";
        mockMvc.perform(post(url, courseTypeReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidCourseType_thenBadRequestResponse() throws Exception {
        CourseTypeReqDTO courseTypeReqDto = CourseTypeReqDTO.builder().build();

        when(courseTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/courseTypes";
        mockMvc.perform(post(url, courseTypeReqDto)).andExpect(status().isBadRequest());
    }
}
