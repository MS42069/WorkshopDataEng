package com.fhwedel.softwareprojekt.v1.controller.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.SemesterTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SemesterTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.service.types.SemesterTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SemesterTypeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class SemesterTypeControllerTest {
    @MockBean private SemesterTypeService semesterTypeService;
    @MockBean private SemesterTypeConverter semesterTypeConverter;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidRoomType_thenCorrectResponse() throws Exception {
        SemesterTypeReqDTO semesterTypeReqDTO =
                SemesterTypeReqDTO.builder().name("Wintersemester").build();

        when(semesterTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/semesterTypes";
        mockMvc.perform(post(url, semesterTypeReqDTO)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndBlankSemesterType_thenBadRequestResponse() throws Exception {
        SemesterTypeReqDTO semesterTypeReqDTO = SemesterTypeReqDTO.builder().name("").build();

        when(semesterTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/semesterTypes";
        mockMvc.perform(post(url, semesterTypeReqDTO)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidSemesterType_thenBadRequestResponse() throws Exception {
        SemesterTypeReqDTO semesterTypeReqDTO = SemesterTypeReqDTO.builder().build();

        when(semesterTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/semesterTypes";
        mockMvc.perform(post(url, semesterTypeReqDTO)).andExpect(status().isBadRequest());
    }
}
