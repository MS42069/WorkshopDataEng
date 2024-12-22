package com.fhwedel.softwareprojekt.v1.controller.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.SchoolTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.SchoolTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.service.types.SchoolTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SchoolTypeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class SchoolTypeControllerTest {
    @MockBean private SchoolTypeService schoolTypeService;
    @MockBean private SchoolTypeConverter schoolTypeConverter;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidSchoolType_thenCorrectResponse() throws Exception {
        SchoolTypeReqDTO schoolTypeReqDto = SchoolTypeReqDTO.builder().name("Uni").build();

        when(schoolTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/schoolTypes";
        mockMvc.perform(post(url, schoolTypeReqDto)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndBlankSchoolType_thenBadRequestResponse() throws Exception {
        SchoolTypeReqDTO schoolTypeReqDto = SchoolTypeReqDTO.builder().name("").build();

        when(schoolTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/schoolTypes";
        mockMvc.perform(post(url, schoolTypeReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidSchoolType_thenBadRequestResponse() throws Exception {
        SchoolTypeReqDTO schoolTypeReqDto = SchoolTypeReqDTO.builder().build();

        when(schoolTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/schoolTypes";
        mockMvc.perform(post(url, schoolTypeReqDto)).andExpect(status().isBadRequest());
    }
}
