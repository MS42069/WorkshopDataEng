package com.fhwedel.softwareprojekt.v1.controller;

import static com.fhwedel.softwareprojekt.v1.MockMvcTestUtil.*;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationConfiguration;
import com.fhwedel.softwareprojekt.v1.controller.timetable.RoomController;
import com.fhwedel.softwareprojekt.v1.converter.RoomConverter;
import com.fhwedel.softwareprojekt.v1.converter.types.RoomTypeConverter;
import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.RoomResDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.RoomTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.service.RoomService;
import com.fhwedel.softwareprojekt.v1.service.SchedulerService;
import com.fhwedel.softwareprojekt.v1.service.types.RoomTypeService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoomController.class)
@ContextConfiguration(classes = SoftwareprojektApplicationConfiguration.class)
@WithMockUser(username = "test_account")
public class RoomControllerTest {

    private final UUID timetableId = UUID.randomUUID();
    @MockBean private RoomService roomService;
    @MockBean private RoomTypeService roomTypeService;
    @MockBean private RoomConverter roomConverter;
    @MockBean private RoomTypeConverter roomTypeConverter;
    @MockBean private SchedulerService schedulerService;
    @Autowired private MockMvc mockMvc;

    @Test
    public void whenPostAndValidRoom_thenCorrectResponse() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        RoomType rt = new RoomType();
        rt.setName("test");

        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Tom")
                        .abbreviation("test")
                        .identifier("id")
                        .capacity(10)
                        .timetable(timetable.getId())
                        .roomType(UUID.randomUUID())
                        .build();
        when(roomService.findAllByTimetableId(timetableId)).thenReturn(List.of());

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isOk());
    }

    @Test
    public void whenPostAndInvalidRoomName_thenBadRequestResponse() throws Exception {
        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("")
                        .abbreviation("Valid")
                        .identifier("id")
                        .capacity(10)
                        .roomType(UUID.randomUUID())
                        .build();

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidRoomAbbr_thenBadRequestResponse() throws Exception {
        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Valid")
                        .abbreviation("")
                        .identifier("id")
                        .capacity(10)
                        .roomType(UUID.randomUUID())
                        .build();

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidRoomIdentifier_thenBadRequestResponse() throws Exception {
        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Valid")
                        .abbreviation("ff")
                        .identifier("")
                        .capacity(10)
                        .roomType(UUID.randomUUID())
                        .build();

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNegativeRoomCapacity_thenBadRequestResponse() throws Exception {
        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Valid")
                        .abbreviation("VALID")
                        .identifier("id")
                        .capacity(-1)
                        .roomType(UUID.randomUUID())
                        .build();

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidNullRoomCapacity_thenBadRequestResponse() throws Exception {
        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Valid")
                        .abbreviation("VALID")
                        .identifier("id")
                        .capacity(null)
                        .roomType(UUID.randomUUID())
                        .build();

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostAndInvalidRoomType_thenBadRequestResponse() throws Exception {
        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Valid")
                        .abbreviation("VALID")
                        .identifier("id")
                        .capacity(1)
                        .roomType(null)
                        .build();

        String url = format("/v1/timetable/%s/rooms", timetableId);
        mockMvc.perform(post(url, roomReqDto)).andExpect(status().isBadRequest());
    }

    @Test
    public void givenRooms_whenGetAllRooms_thenReturnListOfRooms() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        RoomType pcPool = new RoomType();
        pcPool.setName("PC Pool");

        Room r1 = new Room();
        r1.setId(UUID.randomUUID());
        r1.setName("test");
        r1.setCapacity(0);
        r1.setAbbreviation("ts");
        r1.setIdentifier("id");
        r1.setRoomType(pcPool);
        r1.setTimetable(timetable);

        Room r2 = new Room();
        r2.setId(UUID.randomUUID());
        r2.setName("test2");
        r2.setCapacity(0);
        r2.setAbbreviation("ts2");
        r2.setIdentifier("id");
        r2.setRoomType(pcPool);
        r2.setTimetable(timetable);

        List<Room> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);

        RoomResDTO dtoR1 =
                RoomResDTO.builder()
                        .id(r1.getId())
                        .name(r1.getName())
                        .abbreviation(r1.getAbbreviation())
                        .capacity(r1.getCapacity())
                        .roomType(
                                roomTypeConverter.convertRoomTypeEntityToResponseDTO(
                                        r1.getRoomType()))
                        .build();
        RoomResDTO dtoR2 =
                RoomResDTO.builder()
                        .id(r2.getId())
                        .name(r2.getName())
                        .abbreviation(r2.getAbbreviation())
                        .capacity(r2.getCapacity())
                        .roomType(
                                roomTypeConverter.convertRoomTypeEntityToResponseDTO(
                                        r2.getRoomType()))
                        .build();
        List<RoomResDTO> responseDTOList = List.of(dtoR1, dtoR2);

        when(roomService.findAllByTimetableId(timetable.getId())).thenReturn(rooms);
        when(roomConverter.convertEntitiesToResponseDTOList(ArgumentMatchers.anyCollection()))
                .thenReturn(responseDTOList);

        String url = format("/v1/timetable/%s/rooms", timetableId);
        this.mockMvc
                .perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(rooms.size())))
                .andExpect(jsonPath("$[0].name", is(r1.getName())))
                .andExpect(jsonPath("$[1].name", is(r2.getName())));
    }

    @Test
    public void giveRoom_whenGetRoomById_thenReturnRoom() throws Exception {
        Room givenRoom = new Room();
        givenRoom.setId(UUID.randomUUID());
        givenRoom.setRoomType(null);
        givenRoom.setName("test");
        givenRoom.setCapacity(0);
        givenRoom.setAbbreviation("ts");
        givenRoom.setIdentifier("id");

        RoomResDTO responseDTO =
                RoomResDTO.builder()
                        .id(givenRoom.getId())
                        .name(givenRoom.getName())
                        .abbreviation(givenRoom.getAbbreviation())
                        .identifier(givenRoom.getIdentifier())
                        .capacity(givenRoom.getCapacity())
                        .roomType(null)
                        .build();

        when(roomService.findByID(any(UUID.class))).thenReturn(givenRoom);
        when(roomConverter.convertEntityToResponseDTO(any(Room.class))).thenReturn(responseDTO);

        String url = format("/v1/timetable/%s/rooms/%s", timetableId, UUID.randomUUID());
        this.mockMvc
                .perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(givenRoom.getName())))
                .andExpect(jsonPath("$.abbreviation", is(givenRoom.getAbbreviation())));
    }

    @Test
    public void whenEditRoom_thenReturnModifiedRoom() throws Exception {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        RoomType rt = new RoomType();
        rt.setName("test");
        rt.setId(UUID.randomUUID());

        Room updatedRoom = new Room();
        updatedRoom.setId(UUID.randomUUID());
        updatedRoom.setRoomType(null);
        updatedRoom.setName("test");
        updatedRoom.setCapacity(0);
        updatedRoom.setAbbreviation("ts");
        updatedRoom.setIdentifier("id");
        updatedRoom.setTimetable(timetable);

        RoomReqDTO requestDTO =
                RoomReqDTO.builder()
                        .name("Valid")
                        .abbreviation("VALID")
                        .identifier("id")
                        .capacity(1)
                        .roomType(rt.getId())
                        .timetable(timetable.getId())
                        .build();

        RoomTypeResDTO rtResDTO = RoomTypeResDTO.builder().name("updated").id(rt.getId()).build();

        RoomResDTO responseDTO =
                RoomResDTO.builder()
                        .id(updatedRoom.getId())
                        .name(updatedRoom.getName())
                        .abbreviation(updatedRoom.getAbbreviation())
                        .identifier(updatedRoom.getIdentifier())
                        .capacity(updatedRoom.getCapacity())
                        .roomType(rtResDTO)
                        .timetable(timetable.getId())
                        .build();

        when(roomService.updateRoom(any(UUID.class), any(RoomReqDTO.class), any(UUID.class)))
                .thenReturn(updatedRoom);
        when(roomConverter.convertEntityToResponseDTO(any(Room.class))).thenReturn(responseDTO);

        String url = format("/v1/timetable/%s/rooms/%s", timetableId, UUID.randomUUID());
        this.mockMvc
                .perform(patch(url, requestDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedRoom.getName())))
                .andExpect(jsonPath("$.abbreviation", is(updatedRoom.getAbbreviation())));
    }

    @Test
    public void whenDeleteRoom_thenReturnSuccessful() throws Exception {
        Room r1 = new Room();
        r1.setId(UUID.randomUUID());
        r1.setRoomType(null);
        r1.setName("test");
        r1.setCapacity(0);
        r1.setAbbreviation("ts");
        r1.setIdentifier("id");

        doNothing().when(roomService).deleteRoom(any(UUID.class));

        String url = format("/v1/timetable/%s/rooms/%s", timetableId, r1.getId());
        this.mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        verify(roomService, times(1)).deleteRoom(r1.getId());
    }
}
