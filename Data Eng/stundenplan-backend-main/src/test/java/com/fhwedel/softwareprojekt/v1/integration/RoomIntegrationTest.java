package com.fhwedel.softwareprojekt.v1.integration;

import com.fhwedel.softwareprojekt.v1.MockMvcTestUtil;
import com.fhwedel.softwareprojekt.v1.SoftwareprojektApplicationIntegrationConfiguration;
import com.fhwedel.softwareprojekt.v1.converter.RoomConverter;
import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.RoomResDTO;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
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

/**
 * Integration Tests for the Room Controller across all layers to test end-to-end behaviour.
 *
 * <p>The `@SpringBootTest` annotation tells Spring Boot to load the entire application context. The
 * use of <code>WebEnvironment.RANDOM_PORT</code> starts a server with a random port.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SoftwareprojektApplicationIntegrationConfiguration.class)
@WithMockUser("test_account")
public class RoomIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvcTestUtil mockMvcTestUtil;

    /**
     * Autowired repository gives direct access to the database, which is necessary in order to roll
     * back the database after each test (also helpful for setting up and verifying test situations)
     */
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;

    /**
     * Autowired converter spares us from having to convert manually between Entity and DTO
     */
    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @AfterEach
    void rollbackDB() {
        roomRepository.deleteAll();
        roomTypeRepository.deleteAll();
        timetableRepository.deleteAll();
        semesterTypeRepository.deleteAll();
    }

    @Test
    void whenPostRoom_thenCorrectResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Room")
                        .abbreviation("r")
                        .identifier("id")
                        .roomType(roomType.getId())
                        .capacity(10)
                        .timetable(timetable.getId())
                        .build();
        // when
        String url = format("/v1/timetable/%s/rooms", timetable.getId());
        RoomResDTO response = mockMvcTestUtil.post(url, RoomResDTO.class, roomReqDto);

        // then
        assertNotNull(response.getId());
        assertEquals(roomReqDto.getName(), response.getName());
    }

    @Test
    public void givenRooms_whenGetRooms_thenReturnListOfRooms() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timetable timetable2 = new Timetable();
        timetable2.setName("test-table2");
        timetable2.setSpecialEvents(new ArrayList<>());
        timetable2.setStartDate(LocalDate.of(2022, 1, 1));
        timetable2.setEndDate(LocalDate.of(2022, 1, 31));
        timetable2.setNumberOfWeeks(12);
        timetable2.setSemesterType(timetable.getSemesterType());

        timetable = timetableRepository.saveAndFlush(timetable);
        timetable2 = timetableRepository.saveAndFlush(timetable2);

        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("typeTEST");

        roomType = roomTypeRepository.saveAndFlush(roomType);

        // given
        Room roomOne = new Room();
        roomOne.setRoomType(roomType);
        roomOne.setName("Room One");
        roomOne.setCapacity(0);
        roomOne.setAbbreviation("r1");
        roomOne.setIdentifier("id");
        roomOne.setTimetable(timetable);

        Room roomTwo = new Room();
        roomTwo.setRoomType(roomType);
        roomTwo.setName("Room Two");
        roomTwo.setCapacity(0);
        roomTwo.setAbbreviation("r2");
        roomTwo.setIdentifier("id2");
        roomTwo.setTimetable(timetable);

        Room roomThree = new Room();
        roomThree.setRoomType(roomType);
        roomThree.setName("Room Three");
        roomThree.setCapacity(0);
        roomThree.setAbbreviation("r3");
        roomThree.setIdentifier("id3");
        roomThree.setTimetable(timetable2);

        roomOne = roomRepository.saveAndFlush(roomOne);
        roomTwo = roomRepository.saveAndFlush(roomTwo);
        roomRepository.saveAndFlush(roomThree);

        // when
        RoomResDTO[] response =
                mockMvcTestUtil.get(
                        format("/v1/timetable/%s/rooms", timetable.getId()), RoomResDTO[].class);

        // then
        assertEquals(2, response.length);
        assertEquals(roomConverter.convertEntityToResponseDTO(roomOne), response[0]);
        assertEquals(roomConverter.convertEntityToResponseDTO(roomTwo), response[1]);
    }

    @Test
    public void givenRoom_whenGetRoomById_thenReturnRoom() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setRoomType(roomType);
        room.setName("Room");
        room.setCapacity(0);
        room.setAbbreviation("r");
        room.setIdentifier("id");
        room.setTimetable(timetable);

        room = roomRepository.saveAndFlush(room);

        RoomResDTO expected = roomConverter.convertEntityToResponseDTO(room);

        // when
        String url = format("/v1/timetable/%s/rooms/%s", timetable.getId(), room.getId());
        RoomResDTO response = mockMvcTestUtil.get(url, RoomResDTO.class);

        // then
        assertEquals(expected, response);
    }

    @Test
    public void givenRoom_whenPatchRoomName_thenResponseOk() throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Room");
        room.setAbbreviation("r");
        room.setRoomType(roomType);
        room.setCapacity(0);
        room.setIdentifier("id");
        room.setTimetable(timetable);

        room = roomRepository.saveAndFlush(room);

        RoomReqDTO requestDTO = roomConverter.convertEntityToDto(room);
        requestDTO.setName("NewName");
        requestDTO.setTimetable(timetable.getId());
        requestDTO.setRoomType(roomType.getId());
        // when
        String url = format("/v1/timetable/%s/rooms/%s", timetable.getId(), room.getId());
        RoomResDTO response = mockMvcTestUtil.patch(url, RoomResDTO.class, requestDTO);

        // then
        Room storedRoom = roomRepository.findById(room.getId())
                .orElse(new Room());
        assertThat(storedRoom.getName()).isEqualTo("NewName");
        assertEquals(requestDTO.getName(), response.getName());
    }

    @Test
    public void givenRoom_whenDeleteRoom_thenNoContentResponse() throws Exception {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);
        roomType = roomTypeRepository.saveAndFlush(roomType);

        // given
        Room room = new Room();
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setRoomType(roomType);
        room.setCapacity(0);
        room.setIdentifier("id");
        room.setTimetable(timetable);

        room = roomRepository.saveAndFlush(room);

        // when
        String url = format("/v1/timetable/%s/rooms/%s", timetable.getId(), room.getId());
        mockMvc.perform(delete(url))
                .andExpect(status().isNoContent());

        // then
        assertThat(roomRepository.existsById(room.getId())).isFalse();
    }

    @Test
    public void whenGetNotExistingRoom_thenNotFoundResponse() throws Exception {
        String url = format("/v1/rooms/%s", UUID.randomUUID());
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteNotExistingRoom_thenNotFoundResponse() throws Exception {
        String url = format("/v1/rooms/%s", UUID.randomUUID());
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPatchNotExistingRoom_thenNotFoundResponse() throws Exception {
        persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        RoomReqDTO roomReqDto =
                RoomReqDTO.builder()
                        .name("Room One")
                        .abbreviation("r1")
                        .identifier("id")
                        .roomType(roomType.getId())
                        .capacity(10)
                        .build();

        String url = format("/v1/rooms/%s", UUID.randomUUID());
        mockMvc.perform(patch(url, roomReqDto))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenRoom_whenPostRoomWithNonUniqueTimetableIdentifierComb_thenBadRequestResponse()
            throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("typeTEST");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Room One");
        room.setAbbreviation("r1");
        room.setIdentifier("id");
        room.setRoomType(roomType);
        room.setCapacity(0);
        room.setTimetable(timetable);

        roomRepository.saveAndFlush(room);

        RoomReqDTO newRoom =
                RoomReqDTO.builder()
                        .name("Room Two")
                        .abbreviation("r2")
                        .identifier("id")
                        .roomType(roomType.getId())
                        .capacity(10)
                        .build();

        String url = format("/v1/timetable/%s/rooms/%s", timetable.getId(), room.getId());
        mockMvc.perform(post(url, newRoom))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void
    givenRooms_whenPatchRoomWithNonUniqueTimetableIdentifierComb_thenBadRequestResponse()
            throws Exception {
        // given
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setOnline(false);
        roomType.setName("typeTEST");

        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room roomOne = new Room();
        roomOne.setName("Room One");
        roomOne.setAbbreviation("r1");
        roomOne.setIdentifier("id");
        roomOne.setRoomType(roomType);
        roomOne.setCapacity(0);
        roomOne.setTimetable(timetable);

        Room roomTwo = new Room();
        roomTwo.setName("Room Two");
        roomTwo.setAbbreviation("r2");
        roomTwo.setIdentifier("id2");
        roomTwo.setRoomType(roomType);
        roomTwo.setCapacity(0);
        roomTwo.setTimetable(timetable);

        roomRepository.saveAndFlush(roomOne);
        roomTwo = roomRepository.saveAndFlush(roomTwo);

        RoomReqDTO roomTwoDTO = roomConverter.convertEntityToDto(roomTwo);
        // set a none-unique name
        roomTwoDTO.setIdentifier("id");

        // when
        String url = format("/v1/timetable/%s/rooms/%s", timetable.getId(), roomTwo.getId());
        mockMvc.perform(patch(url, roomTwoDTO))
                .andExpect(status().is4xxClientError());
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
