package com.fhwedel.softwareprojekt.v1.integration.types;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.types.RoomTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.delete;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.get;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.patch;
import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.post;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class RoomTypeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomTypeConverter roomTypeConverter;

    @AfterEach
    void rollbackDB() {
        roomTypeRepository.deleteAll();
    }

    @Test
    void whenPostRoomType_thenCorrectResponse() throws Exception {
        RoomTypeReqDTO roomTypeReqDto = RoomTypeReqDTO.builder()
                .name("Lecture Hall")
                .build();
        // when
        String url = "/v1/roomTypes";
        RoomTypeResDTO response = mockMvcTestUtil.post(url, RoomTypeResDTO.class, roomTypeReqDto);
        // then
        assertNotNull(response.getId());
        assertEquals(roomTypeReqDto.getName(), response.getName());
    }

    @Test
    public void givenRoomTypes_whenGetRoomTypes_thenReturnListOfRoomTypes() throws Exception {
        // given
        RoomType roomType1 = new RoomType();
        roomType1.setOnline(false);
        roomType1.setName("typeTEST");

        RoomType roomType2 = new RoomType();
        roomType2.setOnline(false);
        roomType2.setName("typeTEST2");

        RoomType roomType3 = new RoomType();
        roomType3.setOnline(false);
        roomType3.setName("typeTEST3");

        roomType1 = roomTypeRepository.saveAndFlush(roomType1);
        roomType2 = roomTypeRepository.saveAndFlush(roomType2);
        roomType3 = roomTypeRepository.saveAndFlush(roomType3);

        // when
        RoomTypeResDTO[] response = mockMvcTestUtil.get("/v1/roomTypes", RoomTypeResDTO[].class);

        // then
        assertEquals(3, response.length);
        assertEquals(roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType1), response[0]);
        assertEquals(roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType2), response[1]);
        assertEquals(roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType3), response[2]);
    }

    @Test
    public void givenRoomType_whenGetRoomTypeById_thenReturnRoomType() throws Exception {
        // given

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        RoomTypeResDTO expected = roomTypeConverter.convertRoomTypeEntityToResponseDTO(roomType);

        // when
        String url = format("/v1/roomTypes/%s", roomType.getId());
        RoomTypeResDTO response = mockMvcTestUtil.get(url, RoomTypeResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenRoomType_whenPatchRoomType_thenResponseOk() throws Exception {
        // given
        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("typeTEST");

        roomType = roomTypeRepository.saveAndFlush(roomType);

        RoomTypeReqDTO requestDTO = RoomTypeReqDTO.builder()
                .name("updatedType")
                .build();

        // when
        String url = format("/v1/roomTypes/%s", roomType.getId());
        RoomTypeResDTO response = mockMvcTestUtil.patch(url, RoomTypeResDTO.class, requestDTO);

        // then
        RoomType storedRoomType =
                roomTypeRepository.findById(roomType.getId())
                        .orElse(new RoomType());
        assertThat(storedRoomType.getName()).isEqualTo("updatedType");
        assertEquals(requestDTO.getName(), response.getName());
    }

    @Test
    public void givenRoomType_whenDeleteRoomType_thenNoContentResponse() throws Exception {
        // given
        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        // when
        String url = format("/v1/roomTypes/%s", roomType.getId());
        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());

        // then
        assertThat(roomTypeRepository.existsById(roomType.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingRoomType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/roomTypes/%s", UUID.randomUUID());
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingRoomType_thenNotFoundResponse() throws Exception {
        String url = format("/v1/roomTypes/%s", UUID.randomUUID());
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingRoomType_thenNotFoundResponse() throws Exception {

        RoomTypeReqDTO roomTypeReqDto = RoomTypeReqDTO.builder()
                .name("Pool")
                .build();

        String url = format("/v1/roomTypes/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, roomTypeReqDto))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenRoom_whenPostRoomWithNonUniqueName_thenBadRequestResponse() throws Exception {
        // given
        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);
        roomTypeRepository.saveAndFlush(roomType);

        RoomTypeReqDTO newRoomType = RoomTypeReqDTO.builder()
                .name("typeTEST")
                .build();

        String url = "/v1/roomTypes";
        mockMvc.perform(post(url, newRoomType))
                .andExpect(status().is4xxClientError());
    }
}
