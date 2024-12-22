package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.course.*;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** A converter class for mapping between different representations of Course objects. */
@Component
@RequiredArgsConstructor
@Slf4j
public class CourseConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a {@link Course} entity to a detailed response DTO ({@link CourseDetailResDTO}).
     *
     * @param course The course entity to convert
     * @return The detailed response DTO
     */
    public CourseDetailResDTO convertCourseEntityToDetailResDTO(Course course) {
        CourseDetailResDTO dto = modelMapper.map(course, CourseDetailResDTO.class);
        dto.setTimetable(course.getTimetable().getId());

        // Set to prevent a relation from being inserted twice
        Set<CourseRelation> courseRelations = new HashSet<>();
        courseRelations.addAll(course.getCourseRelationsA());
        courseRelations.addAll(course.getCourseRelationsB());

        CourseRelationResDTO courseRelationResDTO =
                convertCourseRelationsToResDTO(course, courseRelations);
        dto.setCourseRelations(courseRelationResDTO);

        log.debug("Converted course to {}", dto);

        return dto;
    }

    /**
     * Converts a {@link Course} entity to a DTO for course planning ({@link CourseToPlanResDTO}).
     *
     * @param course The course entity to convert
     * @param amountToPlan The amount of slots to plan for the course
     * @param degreeOfFreedom The degree of freedom for the course
     * @return The course planning response DTO
     */
    public CourseToPlanResDTO convertEntityToCourseToPlanResDTO(
            Course course, int amountToPlan, int degreeOfFreedom) {
        CourseToPlanResDTO resDTO = modelMapper.map(course, CourseToPlanResDTO.class);
        resDTO.setAmountOfSlotsToPlan(amountToPlan);
        resDTO.setDegreeOfFreedom(degreeOfFreedom);
        return resDTO;
    }

    /**
     * Converts a {@link RoomCombination} entity to a response DTO ({@link CourseRoomComboResDTO}).
     *
     * @param roomCombo The room combination entity to convert
     * @return The response DTO
     */
    public CourseRoomComboResDTO convertRoomComboEntityToResDTO(RoomCombination roomCombo) {
        return modelMapper.map(roomCombo, CourseRoomComboResDTO.class);
    }

    /**
     * Converts a list of {@link Course} entities to a list of detailed response DTOs ({@link
     * CourseDetailResDTO}).
     *
     * @param courses The list of course entities to convert
     * @return The list of detailed response DTOs
     */
    public List<CourseDetailResDTO> convertCourseEntitiesToDetailResDtoList(List<Course> courses) {
        return courses.stream()
                .map(this::convertCourseEntityToDetailResDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a {@link CourseReqDTO} to an instance of {@link Course}. Collections such as
     * lecturers and suited rooms for which no meaningful mapping exists are initialized with an
     * empty collection. Ensures that if no course abbreviation or a blank one is specified in the
     * DTO, the name is set as default abbreviation.
     *
     * @param courseReqDTO DTO to convert
     * @return fully converted instance of {@link Course}
     */
    public Course convertCourseReqDtoToEntity(CourseReqDTO courseReqDTO) {
        Course course = modelMapper.map(courseReqDTO, Course.class);
        log.debug("Mapped CourseReqDTO to {}", course);
        // A meaningful mapping for lecturers and suited rooms would require querying the database,
        // ModelMapper
        // still tries and may create instances, therefore an explicit init with empty lists is
        // necessary
        course.setSuitedRooms(new ArrayList<>());
        course.setLecturers(new ArrayList<>());

        // ensure that if no abbrev is given, the abbrev defaults to the course name
        course.setAbbreviation(convertCourseReqDtoToAbbrev(courseReqDTO));

        return course;
    }

    /**
     * Converts a {@link CourseReqDTO} to a course abbreviation. If no abbreviation is given or it
     * is blank, the course name is used as the default abbreviation.
     *
     * @param courseReqDTO The DTO to extract the abbreviation from
     * @return The course abbreviation
     */
    public String convertCourseReqDtoToAbbrev(CourseReqDTO courseReqDTO) {
        String abbreviation = courseReqDTO.getAbbreviation();
        // ensure that if no abbrev is given, the abbrev defaults to the course name
        if (courseReqDTO.getAbbreviation() == null
                || courseReqDTO.getAbbreviation().length() == 0) {
            abbreviation = (courseReqDTO.getName());
        }
        return abbreviation;
    }

    /**
     * Converts a set of course relations to a response DTO ({@link CourseRelationResDTO}).
     *
     * @param course The course whose relations are being converted
     * @param courseRelations The set of course relations
     * @return The response DTO containing course relations
     */
    CourseRelationResDTO convertCourseRelationsToResDTO(
            Course course, Collection<CourseRelation> courseRelations) {
        CourseRelationResDTO dto = new CourseRelationResDTO();

        // sort each course relation into the correct list
        for (CourseRelation courseRelation : courseRelations) {
            addCourseRelationToResDTO(course, courseRelation, dto);
        }
        // sort result lists alphanumerically by name
        sortByCourseName(dto.getMayBeParallelTo());
        sortByCourseName(dto.getMustBeHeldBefore());
        sortByCourseName(dto.getMustBeHeldAfter());

        log.debug("Converted relations of course '{}' to {}", course.getName(), dto);

        return dto;
    }

    /**
     * Converts a {@link Course} entity to a basic response DTO ({@link CourseBasicResDTO}).
     *
     * @param course The course entity to convert
     * @return The basic response DTO
     */
    public CourseBasicResDTO convertCourseEntityToBasicResDTO(Course course) {
        return modelMapper.map(course, CourseBasicResDTO.class);
    }

    /**
     * Adds the course relation to the given {@link CourseRelationResDTO} by converting and
     * inserting the course, which is related to the "owner" course, into the corresponding DTO
     * list.
     *
     * <p>Note: The "owner" is the course whose relations are to be returned as DTO.
     *
     * @param owner the course that is the "owner" of the relationship
     * @param courseRelation the course relation
     * @param dto the DTO to which the course relation is to be added
     */
    private void addCourseRelationToResDTO(
            Course owner, CourseRelation courseRelation, CourseRelationResDTO dto) {
        switch (courseRelation.getRelationType()) {
            case MAY_BE_PARALLEL -> {
                Course relatedCourse = getRelatedCourse(owner, courseRelation);
                dto.getMayBeParallelTo().add(convertCourseEntityToBasicResDTO(relatedCourse));
            }
            case MUST_BE_PARALLEL -> {
                // do nothing
            }
            case A_MUST_BE_BEFORE_B -> {
                // courseA equals courseB?
                if (courseRelation
                        .getCourseA()
                        .getId()
                        .equals(courseRelation.getCourseB().getId())) {
                    // add course to both lists
                    dto.getMustBeHeldBefore()
                            .add(convertCourseEntityToBasicResDTO(courseRelation.getCourseA()));
                    dto.getMustBeHeldAfter()
                            .add(convertCourseEntityToBasicResDTO(courseRelation.getCourseA()));

                    // Is owner course equal to courseA in the relationship?
                } else if (owner.getId().equals(courseRelation.getCourseA().getId())) {
                    // owner course is courseA, then courseB has to be added to the "mustHeldBefore"
                    // list
                    Course courseB = courseRelation.getCourseB();
                    dto.getMustBeHeldBefore().add(convertCourseEntityToBasicResDTO(courseB));
                } else {
                    // otherwise owner course is courseB, then courseA hast to be added to the
                    // "mustBeHeldAfter" list
                    Course courseA = courseRelation.getCourseA();
                    dto.getMustBeHeldAfter().add(convertCourseEntityToBasicResDTO(courseA));
                }
            }
        }
    }

    /**
     * Sorts the given list of {@link CourseBasicResDTO} in ascending order by the course names.
     *
     * @param courseBasicResDTOList the list to sort
     */
    private void sortByCourseName(List<CourseBasicResDTO> courseBasicResDTOList) {
        Comparator<CourseBasicResDTO> comparator = Comparator.comparing(CourseBasicResDTO::getName);
        courseBasicResDTOList.sort(comparator);
    }

    /**
     * Returns the course of the relation that is not equal the given one. Given Course has to be
     * part of the relation for the result to be meaningful.
     *
     * @param course the given course
     * @param courseRelation the course relation
     * @return courseB of the CourseRelation if courseA is given, otherwise courseA
     */
    private Course getRelatedCourse(Course course, CourseRelation courseRelation) {
        assert course.getId().equals(courseRelation.getCourseA().getId())
                || course.getId().equals(courseRelation.getCourseB().getId());

        if (course.getId().equals(courseRelation.getCourseA().getId())) {
            return courseRelation.getCourseB();
        } else {
            return courseRelation.getCourseA();
        }
    }
}
