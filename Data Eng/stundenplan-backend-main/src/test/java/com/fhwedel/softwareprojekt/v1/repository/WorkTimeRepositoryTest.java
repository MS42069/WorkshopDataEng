package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

/** Unit Tests for the {@link WorkTime} persistence layer, using an embedded in-memory database. */
@DataJpaTest
class WorkTimeRepositoryTest {

    @Autowired private WorkTimeRepository worktimeRepository;

    /**
     * Autowired Test-EntityManger, helpful for setting up persistence layer tests, especially when
     * having multi-entity-relationships.
     */
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void givenWeekdayAndTimeslot_whenSaveWorkTime_thenSuccess() {
        // given
        SemesterType semesterType = new SemesterType();
        semesterType.setName("WS");
        semesterType = testEntityManager.persist(semesterType);

        Timetable timetable = new Timetable();
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        timetable.setSemesterType(semesterType);
        timetable = testEntityManager.persist(timetable);

        Timeslot nineToTen = new Timeslot();
        nineToTen.setStartTime(LocalTime.of(9, 0));
        nineToTen.setEndTime(LocalTime.of(10, 0));
        nineToTen.setIndex(0);
        nineToTen.setTimetable(timetable);

        nineToTen = testEntityManager.persist(nineToTen);

        // when
        WorkTime workTime = new WorkTime();
        workTime.setTimeslot(nineToTen);
        workTime.setWeekday(DayOfWeek.MONDAY);

        workTime = worktimeRepository.saveAndFlush(workTime);
        // then
        assertNotNull(workTime);
        assertNotNull(workTime.getId());
    }

    @Test
    void whenSaveWorkTimeAndWeekdayIsNull_thenThrowForeignKeyViolation() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        Timeslot nineToTen = new Timeslot();
        nineToTen.setStartTime(LocalTime.of(9, 0));
        nineToTen.setEndTime(LocalTime.of(10, 0));
        nineToTen.setIndex(0);
        nineToTen.setTimetable(timetable);

        nineToTen = testEntityManager.persist(nineToTen);

        // when
        WorkTime workTime = new WorkTime();
        workTime.setTimeslot(nineToTen);
        workTime.setWeekday(null);
        assertThatThrownBy(() -> worktimeRepository.saveAndFlush(workTime))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void whenSaveWorkTimeAndTimeslotIsNull_thenThrowForeignKeyViolation() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        // when
        WorkTime workTime = new WorkTime();
        workTime.setTimeslot(null);
        workTime.setWeekday(DayOfWeek.MONDAY);
        assertThatThrownBy(() -> worktimeRepository.saveAndFlush(workTime))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void whenSaveWorkTimeAndTimeslotDoesNotExist_thenThrowForeignKeyViolation() {
        // given
        Timetable timetable = new Timetable();
        timetable.setId(UUID.randomUUID());
        timetable.setName("test-table");
        timetable.setSpecialEvents(new ArrayList<>());
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));

        // when
        WorkTime workTime = new WorkTime();
        workTime.setWeekday(DayOfWeek.MONDAY);
        assertThatThrownBy(() -> worktimeRepository.saveAndFlush(workTime))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
