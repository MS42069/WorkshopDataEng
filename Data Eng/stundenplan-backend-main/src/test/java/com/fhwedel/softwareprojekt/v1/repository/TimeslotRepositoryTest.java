package com.fhwedel.softwareprojekt.v1.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

/** Unit Tests for the timeslot persistence layer, using an embedded in-memory database. */
@DataJpaTest
public class TimeslotRepositoryTest {

    @Autowired private TimeslotRepository timeslotRepository;

    @Autowired private TestEntityManager testEntityManager;

    @Autowired private TimetableRepository timetableRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Test
    void saveSuccessfully() {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);

        Timeslot newTimeslot = timeslotRepository.save(timeslot);
        assertNotNull(newTimeslot);
        assertNotNull(newTimeslot.getId());
    }

    @Test
    void whenSaveTimeslotWithNoUniqueIndex_thenThrowException() {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);

        Timeslot timeslotWithIdenticalStart = new Timeslot();
        timeslotWithIdenticalStart.setStartTime(LocalTime.parse("09:30:00"));
        timeslotWithIdenticalStart.setEndTime(LocalTime.parse("10:45:00"));
        timeslotWithIdenticalStart.setIndex(1);
        timeslotWithIdenticalStart.setTimetable(timetable);

        timeslotRepository.save(timeslot);
        timeslotRepository.flush();

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    timeslotRepository.save(timeslotWithIdenticalStart);
                    timeslotRepository.flush();
                });
    }

    @Test
    void updateTimeslotSuccessfully() {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);
        timeslot.setTimetable(timetable);
        timeslot = timeslotRepository.save(timeslot);

        Timeslot existingTimeslot =
                timeslotRepository.findById(timeslot.getId()).orElse(new Timeslot());
        existingTimeslot.setIndex(2);

        Timeslot updatedTSlot = timeslotRepository.save(timeslot);
        timeslotRepository.flush();

        assertEquals(2, updatedTSlot.getIndex());
    }

    @Test
    void whenUpdateTimeslotWithNoUniqueIndex_thenThrowException() {
        Timeslot timeslot = new Timeslot();
        timeslot.setStartTime(LocalTime.parse("08:00:00"));
        timeslot.setEndTime(LocalTime.parse("09:15:00"));
        timeslot.setIndex(1);

        Timeslot timeslot1 = new Timeslot();
        timeslot1.setStartTime(LocalTime.parse("09:30:00"));
        timeslot1.setEndTime(LocalTime.parse("10:45:00"));
        timeslot1.setIndex(2);

        timeslot = timeslotRepository.save(timeslot);
        timeslotRepository.save(timeslot1);

        Timeslot existingTimeslot =
                timeslotRepository.findById(timeslot.getId()).orElse(new Timeslot());
        existingTimeslot.setIndex(2);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    timeslotRepository.save(existingTimeslot);
                    timeslotRepository.flush();
                });
    }

    @Test
    void whenFindByTimetableOrderByStartTimeAsc_thenOrderCorrectly() {
        Timetable timetable = persistTimetable(TestDataUtil.createTimetableWS22());

        Timeslot ts2 = new Timeslot();
        ts2.setStartTime(LocalTime.parse("09:30:00"));
        ts2.setEndTime(LocalTime.parse("10:45:00"));
        ts2.setIndex(2);
        ts2.setTimetable(timetable);

        Timeslot ts1 = new Timeslot();
        ts1.setStartTime(LocalTime.parse("08:00:00"));
        ts1.setEndTime(LocalTime.parse("09:15:00"));
        ts1.setIndex(1);
        ts1.setTimetable(timetable);

        testEntityManager.persist(ts2);
        testEntityManager.persist(ts1);
        testEntityManager.flush();

        // when
        List<Timeslot> result =
                timeslotRepository.findByTimetableIdOrderByStartTimeAsc(timetable.getId());

        assertEquals(ts1.getId(), result.get(0).getId());
        assertEquals(ts2.getId(), result.get(1).getId());
    }

    private Timetable persistTimetable(Timetable timetable) {
        timetable.setSemesterType(semesterTypeRepository.saveAndFlush(timetable.getSemesterType()));
        timetable = timetableRepository.saveAndFlush(timetable);
        return timetable;
    }
}
