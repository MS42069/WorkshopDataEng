package com.fhwedel.softwareprojekt.v1.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fhwedel.softwareprojekt.v1.model.SpecialEvent;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import com.fhwedel.softwareprojekt.v1.repository.types.SemesterTypeRepository;
import com.fhwedel.softwareprojekt.v1.testutil.TestDataUtil;
import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Persistence Layer for testing the creation and deletion of {@link
 * com.fhwedel.softwareprojekt.v1.model.Timetable timetable} and associating them with {@link
 * com.fhwedel.softwareprojekt.v1.model.SpecialEvent special events}.
 */
@DataJpaTest
public class TimetableRepositoryTest {

    @Autowired private TimetableRepository timetableRepository;
    @Autowired private SpecialEventRepository specialEventRepository;

    @Autowired private SemesterTypeRepository semesterTypeRepository;

    @Test
    void whenSaveTimetableWithoutSpecialEvents_thenSuccessfulPersistence() {
        // given
        Timetable timetable = new Timetable();
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        timetable.setName("name");
        timetable.setSemesterType(persistSemesterType(TestDataUtil.createSemesterTypeWS()));
        // when
        timetable = timetableRepository.saveAndFlush(timetable);
        // then
        assertNotNull(timetable);
        assertNotNull(timetable.getId());
    }

    @Test
    void whenSaveTimetableWithSpecialEvents_thenSuccessfulPersistence() {
        // given
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));
        Timetable timetable = new Timetable();
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setSemesterType(persistSemesterType(TestDataUtil.createSemesterTypeWS()));
        timetable.setNumberOfWeeks(12);
        timetable.setName("name");
        specialEvent.setTimetable(timetable);
        timetable.setSpecialEvents(List.of(specialEvent));
        // when
        timetable = timetableRepository.saveAndFlush(timetable);
        specialEventRepository.saveAndFlush(specialEvent);
        // then
        assertNotNull(timetable);
        assertNotNull(timetable.getId());
        assertNotNull(timetable.getSpecialEvents().get(0).getId());
    }

    @Test
    void whenRemoveTimetableWithSpecialEvents_thenCascadeRemove() {
        // given
        SpecialEvent specialEvent = new SpecialEvent();
        specialEvent.setSpecialEventType(SpecialEventType.FREE);
        specialEvent.setStartDate(LocalDate.of(2022, 1, 1));
        specialEvent.setEndDate(LocalDate.of(2022, 1, 31));
        Timetable timetable = new Timetable();
        timetable.setStartDate(LocalDate.of(2022, 1, 1));
        timetable.setEndDate(LocalDate.of(2022, 1, 31));
        timetable.setNumberOfWeeks(12);
        timetable.setName("name");
        timetable.setSemesterType(persistSemesterType(TestDataUtil.createSemesterTypeWS()));
        specialEvent.setTimetable(timetable);
        timetable.setSpecialEvents(List.of(specialEvent));
        timetable = timetableRepository.saveAndFlush(timetable);
        specialEventRepository.saveAndFlush(specialEvent);
        assertNotNull(timetable);
        assertNotNull(timetable.getId());
        assertNotNull(timetable.getSpecialEvents().get(0).getId());

        // when
        timetableRepository.delete(timetable);

        // then
        assertThat(timetableRepository.findAll().size()).isEqualTo(0);
        assertThat(specialEventRepository.findAll().size()).isEqualTo(0);
    }

    private SemesterType persistSemesterType(SemesterType semesterType) {
        return semesterTypeRepository.saveAndFlush(semesterType);
    }
}
