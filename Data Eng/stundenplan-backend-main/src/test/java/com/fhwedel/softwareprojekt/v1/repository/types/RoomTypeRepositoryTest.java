package com.fhwedel.softwareprojekt.v1.repository.types;

import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class RoomTypeRepositoryTest {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    void saveSuccessfully() {
        RoomType roomType = new RoomType();
        roomType.setName("Freiluft");

        RoomType newRoomType = roomTypeRepository.save(roomType);

        assertNotNull(newRoomType);
        assertNotNull(newRoomType.getId());
    }

    @Test
    void whenSaveRoomTypeWithNoUniqueName_thenThrowException() {

        RoomType roomType = new RoomType();
        roomType.setName("test");
        roomType.setOnline(false);

        roomTypeRepository.saveAndFlush(roomType);

        RoomType same = new RoomType();
        same.setOnline(false);
        same.setName("test");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    roomTypeRepository.save(same);
                    roomTypeRepository.flush();
                });
    }

    @Test
    void updateRoomTypeSuccessfully() {

        RoomType roomType = new RoomType();
        roomType.setName("test");
        roomType.setOnline(false);

        roomType = roomTypeRepository.saveAndFlush(roomType);

        RoomType existingRoomType =
                roomTypeRepository.findById(roomType.getId())
                        .orElse(new RoomType());
        existingRoomType.setName("newName");

        RoomType updatedRoomType = roomTypeRepository.save(roomType);
        updatedRoomType.setOnline(false);
        roomTypeRepository.flush();

        assertEquals("newName", updatedRoomType.getName());
    }

    @Test
    void whenUpdateRoomTypeWithNoUniqueName_thenThrowException() {
        RoomType roomType1 = new RoomType();
        roomType1.setName("test");
        roomType1.setOnline(false);
        roomType1 = roomTypeRepository.saveAndFlush(roomType1);

        RoomType roomType2 = new RoomType();
        roomType2.setName("unique");
        roomType2.setOnline(false);
        roomTypeRepository.saveAndFlush(roomType2);

        RoomType existingRoomType =
                roomTypeRepository.findById(roomType1.getId())
                        .orElse(new RoomType());
        existingRoomType.setName("unique");

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    roomTypeRepository.save(existingRoomType);
                    roomTypeRepository.flush();
                });
    }
}
