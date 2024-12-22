package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Unit Tests for the {@link com.fhwedel.softwareprojekt.v1.model.SpecialEvent} persistence layer,
 * using an embedded in-memory database.
 */
@DataJpaTest
public class SpecialEventRepositoryTest {

    @Autowired private SpecialEventRepository specialEventRepository;

    /**
     * Autowired Test-EntityManger, helpful for setting up persistence layer tests, especially when
     * having multi-entity-relationships.
     */
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void givenTimeslotAndDates_whenSaveSpecialEvent_thenSuccess() {
        // given
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        // when
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(startDate);
        specialEvent.setEndDate(endDate);
        specialEvent.setTimetable(timetable);

        specialEvent = specialEventRepository.saveAndFlush(specialEvent);
        // then
        assertNotNull(specialEvent);
        assertNotNull(specialEvent.getId());
    }

    @Test
    void whenSaveSpecialEventAndStartDateIsNull_thenThrowConstraintViolationException() {
        // given
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        Timetable timetable = persistTimetable(TestDataUtil.createTimetableSS23());

        // when
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(null);
        specialEvent.setEndDate(endDate);
        specialEvent.setTimetable(timetable);

        assertThatThrownBy(() -> specialEventRepository.saveAndFlush(specialEvent))
                // then
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void whenSaveSpecialEventAndStartTimeslotIsNull_thenThrowDataIntegrityViolationException() {
        // given
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);

        Timeslot nineToTen = new Timeslot();
        nineToTen.setStartTime(LocalTime.of(9, 0));
        nineToTen.setEndTime(LocalTime.of(10, 0));
        nineToTen.setIndex(0);

        Timeslot elevenToTwelve = new Timeslot();
        elevenToTwelve.setStartTime(LocalTime.of(11, 0));
        elevenToTwelve.setEndTime(LocalTime.of(12, 0));
        elevenToTwelve.setIndex(1);

        testEntityManager.persist(nineToTen);
        testEntityManager.persist(elevenToTwelve);

        // when
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(startDate);
        specialEvent.setEndDate(endDate);

        assertThatThrownBy(() -> specialEventRepository.saveAndFlush(specialEvent))
                // then
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(testEntityManager.persist(timetable.getSemesterType()));
        timetable = testEntityManager.persist(timetable);
        return timetable;
    }
}
