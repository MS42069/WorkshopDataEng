package com.fhwedel.softwareprojekt.v1.controller.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.EmployeeTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.service.types.EmployeeTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeTypeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class EmployeeTypeControllerTest {

    @MockBean private EmployeeTypeService employeeTypeService;
    @MockBean private EmployeeTypeConverter employeeTypeConverter;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidEmployeeType_thenCorrectResponse() throws Exception {
        EmployeeTypeReqDTO employeeTypeReqDTO =
                EmployeeTypeReqDTO.builder().name("Assistent").build();

        when(employeeTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/employeeTypes";
        mockMvc.perform(post(url, employeeTypeReqDTO)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndBlankEmployeeType_thenBadRequestResponse() throws Exception {
        EmployeeTypeReqDTO employeeTypeReqDTO = EmployeeTypeReqDTO.builder().name("").build();

        when(employeeTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/employeeTypes";
        mockMvc.perform(post(url, employeeTypeReqDTO)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidEmployeeType_thenBadRequestResponse() throws Exception {
        EmployeeTypeReqDTO employeeTypeReqDTO = EmployeeTypeReqDTO.builder().build();

        when(employeeTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/employeeTypes";
        mockMvc.perform(post(url, employeeTypeReqDTO)).andExpect(status().isBadRequest());
    }
}
