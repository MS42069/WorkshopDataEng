package com.fhwedel.softwareprojekt.v1.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fhwedel.softwareprojekt.v1.dto.RoomReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.RoomResDTO;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class RoomConverterTest {

    private final RoomConverter roomConverter = new RoomConverter(new ModelMapper());

    @Test
    void convertDtoToEntity() {
        RoomReqDTO roomReqDTO =
                RoomReqDTO.builder()
                        .name("Seminarraum7")
                        .abbreviation("SR7")
                        .identifier("id")
                        .roomType(null)
                        .capacity(22)
                        .build();

        Room room = roomConverter.convertDtoToEntity(roomReqDTO);

        assertThat(room).isNotNull();
        assertThat(room.getName()).isEqualTo("Seminarraum7");
    }

    @Test
    void convertEntityToDto() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Room r1 = new Room();
        r1.setId(UUID.randomUUID());
        r1.setRoomType(null);
        r1.setName("test");
        r1.setCapacity(0);
        r1.setAbbreviation("ts");
        r1.setIdentifier("id");
        r1.setTimetable(timetable);
        RoomReqDTO convertDTO = roomConverter.convertEntityToDto(r1);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntityToResponseDTO() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Room r1 = new Room();
        r1.setId(UUID.randomUUID());
        r1.setRoomType(null);
        r1.setName("test");
        r1.setCapacity(0);
        r1.setAbbreviation("ts");
        r1.setIdentifier("id");
        r1.setTimetable(timetable);
        RoomResDTO convertDTO = roomConverter.convertEntityToResponseDTO(r1);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.getName()).isEqualTo("test");
    }

    @Test
    void convertEntitiesToResponseDTOList() {
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);

        Room r1 = new Room();
        r1.setId(UUID.randomUUID());
        r1.setRoomType(null);
        r1.setName("test");
        r1.setCapacity(0);
        r1.setAbbreviation("ts");
        r1.setIdentifier("id");
        r1.setTimetable(timetable);
        Room r2 = new Room();
        r2.setId(UUID.randomUUID());
        r2.setRoomType(null);
        r2.setName("test2");
        r2.setCapacity(0);
        r2.setAbbreviation("ts2");
        r2.setIdentifier("id");
        r2.setTimetable(timetable);

        List<Room> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);

        List<RoomResDTO> convertDTO = roomConverter.convertEntitiesToResponseDTOList(rooms);
        assertThat(convertDTO).isNotNull();
        assertThat(convertDTO.size()).isEqualTo(2);
    }
}
