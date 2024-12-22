package com.fhwedel.softwareprojekt.v1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseRelationServiceTest {

    @Mock private CourseRelationRepository courseRelationRepository;

    @InjectMocks private CourseRelationService underTest;

    @Test
    void saveCourseRelationCorrectly() {

        Course courseA = new Course();
        courseA.setId(UUID.randomUUID());
        courseA.setCasID("WS22A0001");
        courseA.setName("Course A");

        Course courseB = new Course();
        courseB.setId(UUID.randomUUID());
        courseB.setCasID("WS22B0001");
        courseB.setName("Course B");

        RelationType relationType = RelationType.MAY_BE_PARALLEL;

        // stub courseRelationRepository.save() by just returning the argument
        when(courseRelationRepository.save(any(CourseRelation.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        CourseRelation result = underTest.save(courseA, courseB, relationType);

        assertEquals(courseA, result.getCourseA());
        assertEquals(courseB, result.getCourseB());
        assertEquals(relationType, result.getRelationType());

        assertEquals(1, courseA.getCourseRelationsA().size());
        assertEquals(0, courseA.getCourseRelationsB().size());
        assertEquals(0, courseB.getCourseRelationsA().size());
        assertEquals(1, courseB.getCourseRelationsB().size());
    }
}
