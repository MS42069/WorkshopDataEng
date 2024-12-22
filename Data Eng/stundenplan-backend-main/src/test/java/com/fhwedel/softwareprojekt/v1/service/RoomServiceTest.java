package com.fhwedel.softwareprojekt.v1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fhwedel.softwareprojekt.v1.converter.RoomConverter;
import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.service.types.RoomTypeService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    private final RoomConverter roomConverter = new RoomConverter(new ModelMapper());
    private final UUID timetableId = UUID.randomUUID();
    @Mock private RoomRepository roomRepository;
    @Mock private RoomTypeService roomTypeService;
    @Mock private TimetableService timetableService;
    @InjectMocks private RoomService underTest;

    @BeforeEach
    void setUp() {
        underTest =
                new RoomService(roomTypeService, roomRepository, timetableService, roomConverter);
    }

    @Test
    void findAllByTimetableId() {
        // when
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        underTest.findAllByTimetableId(UUID.randomUUID());
        // then
        verify(roomRepository).findByTimetableId(any(UUID.class));
    }

    @Test
    void findByIDWithNotExistingID_thenException() {
        Optional<Room> emptyOptional = Optional.empty();
        // when
        when(roomRepository.findById(any(UUID.class))).thenReturn(emptyOptional);

        UUID id = UUID.randomUUID();
        // then
        assertThatThrownBy(() -> underTest.findByID(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        HttpStatus.NOT_FOUND
                                + " \"entity 'class com.fhwedel.softwareprojekt.v1.model.Room' with id '"
                                + id
                                + "' could not be found");
    }

    @Test
    void findByID_thenReturnsRoom() {
        Room room = new Room();
        room.setAbbreviation("SR7");
        room.setRoomType(null);
        room.setName("Seminarraum7");
        room.setIdentifier("id");
        room.setCapacity(22);

        Optional<Room> optionalRoom = Optional.of(room);
        // when
        when(roomRepository.findById(any(UUID.class))).thenReturn(optionalRoom);

        UUID id = UUID.randomUUID();
        // then
        Room actualRoom = underTest.findByID(id);

        assertThat(actualRoom).isEqualTo(room);
    }

    @Test
    void save() {
        // given
        RoomReqDTO roomReqDTO =
                RoomReqDTO.builder()
                        .name("Seminarraum7")
                        .abbreviation("SR7")
                        .identifier("id")
                        .roomType(null)
                        .capacity(22)
                        .build();

        Room room = new Room();
        room.setAbbreviation("SR7");
        room.setRoomType(null);
        room.setName("Seminarraum7");
        room.setIdentifier("id");
        room.setCapacity(22);

        // when
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room actualRoom = underTest.save(roomReqDTO, timetableId);

        // then
        assertThat(actualRoom).isNotNull();
        assertThat(actualRoom.getName()).isEqualTo("Seminarraum7");
    }

    @Test
    void updateRoom() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("name");
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 12, 1));
        timetable.setSpecialEvents(List.of());
        timetable.setSemesterType(new SemesterType());
        timetable.setNumberOfWeeks(12);

        RoomReqDTO roomReqDTO =
                RoomReqDTO.builder()
                        .name("Seminarraum8")
                        .abbreviation("SR7")
                        .identifier("id")
                        .roomType(null)
                        .capacity(22)
                        .build();

        Room room = new Room();
        room.setAbbreviation("SR7");
        room.setRoomType(null);
        room.setName("Seminarraum7");
        room.setIdentifier("id");
        room.setCapacity(22);
        room.setTimetable(timetable);

        UUID uuid = UUID.randomUUID();

        // when
        when(roomRepository.findById(any(UUID.class))).thenReturn(Optional.of(room));

        Room actualRoom = underTest.updateRoom(uuid, roomReqDTO, timetableId);

        // then
        assertThat(actualRoom).isNotNull();
        assertThat(actualRoom.getName()).isEqualTo("Seminarraum8");
    }

    @Test
    void deleteRoom() {
        // given
        Room room = new Room();
        room.setAbbreviation("SR7");
        room.setIdentifier("id");
        room.setRoomType(null);
        room.setName("Seminarraum7");
        room.setCapacity(22);

        UUID uuid = UUID.randomUUID();

        // when
        when(roomRepository.findById(any(UUID.class))).thenReturn(Optional.of(room));

        doNothing().when(roomRepository).deleteById(any(UUID.class));

        underTest.deleteRoom(uuid);

        verify(roomRepository, times(1)).deleteById(uuid);
    }
}
