package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import com.fhwedel.softwareprojekt.v1.repository.types.RoomTypeRepository;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Tests for the room persistence layer, using an embedded in-memory database.
 */
@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private SemesterTypeRepository semesterTypeRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    void saveSuccessfully() {
        Room room = new Room();
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("id");
        room.setRoomType(null);
        room.setCapacity(40);

        Room newRoom = roomRepository.save(room);

        assertNotNull(newRoom);
        assertNotNull(newRoom.getId());
    }

    @Test
    void whenSaveRoomWithNoUniqueTimetableIdentifierComb_thenThrowException() {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setName("test");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("id");
        room.setRoomType(roomType);
        room.setCapacity(40);
        room.setTimetable(timetable);

        Room roomWithIdenticalAbbrev = new Room();
        roomWithIdenticalAbbrev.setName("Hörsaal 2");
        roomWithIdenticalAbbrev.setAbbreviation("HS02");
        roomWithIdenticalAbbrev.setIdentifier("id");
        roomWithIdenticalAbbrev.setRoomType(roomType);
        roomWithIdenticalAbbrev.setCapacity(40);
        roomWithIdenticalAbbrev.setTimetable(timetable);

        roomRepository.save(room);
        roomRepository.flush();

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    roomRepository.save(roomWithIdenticalAbbrev);
                    roomRepository.flush();
                });
    }

    @Test
    void updateRoomSuccessfully() {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        RoomType roomType = new RoomType();
        roomType.setOnline(true);
        roomType.setName("test");

        roomType = roomTypeRepository.saveAndFlush(roomType);

        Room room = new Room();
        room.setName("Hörsaal 1");
        room.setAbbreviation("HS01");
        room.setIdentifier("id");
        room.setRoomType(roomType);
        room.setCapacity(40);
        room.setTimetable(timetable);

        room = roomRepository.save(room);

        Room existingRoom = roomRepository.findById(room.getId())
                .orElse(new Room());
        existingRoom.setAbbreviation("HS02");

        Room updatedRoom = roomRepository.save(room);
        roomRepository.flush();

        assertEquals("HS02", updatedRoom.getAbbreviation());
    }

    @Test
    void whenUpdateRoomWithNoUniqueAbbrev_thenThrowException() {
        Room roomHS01 = new Room();
        roomHS01.setName("Hörsaal 1");
        roomHS01.setAbbreviation("HS01");
        roomHS01.setIdentifier("id");
        roomHS01.setRoomType(null);
        roomHS01.setCapacity(40);

        Room roomHS02 = new Room();
        roomHS02.setName("Hörsaal 2");
        roomHS02.setAbbreviation("HS02");
        roomHS02.setIdentifier("id2");
        roomHS02.setRoomType(null);
        roomHS02.setCapacity(40);

        roomHS01 = roomRepository.save(roomHS01);
        roomRepository.save(roomHS02);

        Room existingRoom = roomRepository.findById(roomHS01.getId())
                .orElse(new Room());
        existingRoom.setAbbreviation("HS02");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    roomRepository.save(existingRoom);
                    roomRepository.flush();
                });
    }

    @Test
    void whenUpdateRoomWithNoUniqueName_thenThrowException() {
        Room roomHS01 = new Room();
        roomHS01.setName("Hörsaal 1");
        roomHS01.setAbbreviation("HS01");
        roomHS01.setIdentifier("id");
        roomHS01.setRoomType(null);
        roomHS01.setCapacity(40);

        Room roomHS02 = new Room();
        roomHS02.setName("Hörsaal 2");
        roomHS02.setAbbreviation("HS02");
        roomHS02.setIdentifier("id2");
        roomHS02.setRoomType(null);
        roomHS02.setCapacity(40);

        roomHS01 = roomRepository.save(roomHS01);
        roomRepository.save(roomHS02);

        Room existingRoom = roomRepository.findById(roomHS01.getId())
                .orElse(new Room());
        existingRoom.setName("Hörsaal 2");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    roomRepository.save(existingRoom);
                    roomRepository.flush();
                });
    }

    @Test
    void whenUpdateRoomWithNoUniqueIdentifier_thenThrowException() {
        Room roomHS01 = new Room();
        roomHS01.setName("Hörsaal 1");
        roomHS01.setAbbreviation("HS01");
        roomHS01.setIdentifier("id");
        roomHS01.setRoomType(null);
        roomHS01.setCapacity(40);

        Room roomHS02 = new Room();
        roomHS02.setName("Hörsaal 2");
        roomHS02.setAbbreviation("HS02");
        roomHS02.setIdentifier("id2");
        roomHS02.setRoomType(null);
        roomHS02.setCapacity(40);

        roomHS01 = roomRepository.save(roomHS01);
        roomRepository.save(roomHS02);

        Room existingRoom = roomRepository.findById(roomHS01.getId())
                .orElse(new Room());
        existingRoom.setIdentifier("id2");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    roomRepository.save(existingRoom);
                    roomRepository.flush();
                });
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
