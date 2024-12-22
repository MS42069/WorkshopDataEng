package com.fhwedel.softwareprojekt.v1.repository;

import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRelationRepository extends JpaRepository<CourseRelation, UUID> {
    // Note: Return type may be an Optional, because unique constraint guarantees unambiguity
    Optional<CourseRelation> findByRelationTypeAndCourseA_IdAndCourseB_Id(
            RelationType relationType, UUID courseA, UUID courseB);

    List<CourseRelation> findByCourseA_Id(UUID courseA);

    List<CourseRelation> findByCourseB_Id(UUID courseB);

    /**
     * Finds all course relations belonging to course A or to course B.
     *
     * @param courseA ID of course A.
     * @param courseB ID of course B.
     * @return all course relations associated with course A or course B.
     */
    List<CourseRelation> findByCourseA_IdOrCourseB_Id(UUID courseA, UUID courseB);
}
