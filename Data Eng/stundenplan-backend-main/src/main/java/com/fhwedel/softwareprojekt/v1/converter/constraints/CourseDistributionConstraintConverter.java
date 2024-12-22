package com.fhwedel.softwareprojekt.v1.converter.constraints;

import com.fhwedel.softwareprojekt.v1.dto.constraints.CourseDistributionConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.CourseDistributionConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeCourseDistributionConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A converter class for converting between different representations of {@link
 * EmployeeCourseDistributionConstraint} constraints.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CourseDistributionConstraintConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an {@link EmployeeCourseDistributionConstraint} entity to a {@link
     * CourseDistributionConstraintReqDTO}.
     *
     * @param constraint The entity to convert
     * @return The converted {@link CourseDistributionConstraintReqDTO}
     */
    public CourseDistributionConstraintReqDTO convertToReqDTO(
            EmployeeCourseDistributionConstraint constraint) {
        return modelMapper.map(constraint, CourseDistributionConstraintReqDTO.class);
    }

    /**
     * Converts a {@link CourseDistributionConstraintReqDTO} to an {@link
     * EmployeeCourseDistributionConstraint} entity.
     *
     * @param dto The DTO to convert
     * @return The converted {@link EmployeeCourseDistributionConstraint} entity
     */
    public EmployeeCourseDistributionConstraint convertToEntity(
            CourseDistributionConstraintReqDTO dto) {
        return modelMapper.map(dto, EmployeeCourseDistributionConstraint.class);
    }

    /**
     * Converts an {@link EmployeeCourseDistributionConstraint} entity to a {@link
     * CourseDistributionConstraintResDTO}.
     *
     * @param constraint The entity to convert
     * @return The converted {@link CourseDistributionConstraintResDTO}
     */
    public CourseDistributionConstraintResDTO convertToResDTO(
            EmployeeCourseDistributionConstraint constraint) {
        return modelMapper.map(constraint, CourseDistributionConstraintResDTO.class);
    }

    /**
     * Converts a {@link CourseDistributionConstraintResDTO} to an {@link
     * EmployeeCourseDistributionConstraint} entity.
     *
     * @param dto The DTO to convert
     * @return The converted {@link EmployeeCourseDistributionConstraint} entity
     */
    public EmployeeCourseDistributionConstraint convertToEntity(
            CourseDistributionConstraintResDTO dto) {
        return modelMapper.map(dto, EmployeeCourseDistributionConstraint.class);
    }
}
