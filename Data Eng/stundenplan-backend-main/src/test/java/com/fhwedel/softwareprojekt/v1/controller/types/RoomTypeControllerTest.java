package com.fhwedel.softwareprojekt.v1.controller.types;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.RoomTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.service.types.RoomTypeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoomTypeController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class RoomTypeControllerTest {

    @MockBean private RoomTypeService roomTypeService;
    @MockBean private RoomTypeConverter roomTypeConverter;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidRoomType_thenCorrectResponse() throws Exception {
        RoomTypeReqDTO roomTypeReqDto = RoomTypeReqDTO.builder().name("Lecture Hall").build();

        when(roomTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/roomTypes";
        mockMvc.perform(post(url, roomTypeReqDto)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndBlankRoomType_thenBadRequestResponse() throws Exception {
        RoomTypeReqDTO roomTypeReqDto = RoomTypeReqDTO.builder().name("").build();

        when(roomTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/roomTypes";
        mockMvc.perform(post(url, roomTypeReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidRoomType_thenBadRequestResponse() throws Exception {
        RoomTypeReqDTO roomTypeReqDto = RoomTypeReqDTO.builder().build();

        when(roomTypeService.findAll()).thenReturn(List.of());

        String url = "/v1/roomTypes";
        mockMvc.perform(post(url, roomTypeReqDto)).andExpect(status().isBadRequest());
    }
}
