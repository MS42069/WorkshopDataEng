package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for managing course relations. */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseRelationService {

    /** The repository for managing course relations. */
    private final CourseRelationRepository courseRelationRepository;

    /**
     * Saves a new course relation between two courses with the specified relation type.
     *
     * @param courseA The first course in the relation.
     * @param courseB The second course in the relation.
     * @param relationType The type of relation between the two courses.
     * @return The saved course relation.
     */
    public CourseRelation save(Course courseA, Course courseB, RelationType relationType) {
        log.debug(
                "Creating new {} course relation for courseA={} and courseB={}",
                relationType,
                courseA.getId(),
                courseB.getId());

        CourseRelation courseRelation = new CourseRelation();
        courseRelation.setRelationType(relationType);
        courseRelation.setCourseA(courseA);
        courseRelation.setCourseB(courseB);

        courseRelation = courseRelationRepository.save(courseRelation);

        courseA.getCourseRelationsA().add(courseRelation);
        courseB.getCourseRelationsB().add(courseRelation);

        log.debug("Created and saved new course relation {}", courseRelation);

        return courseRelation;
    }
}
