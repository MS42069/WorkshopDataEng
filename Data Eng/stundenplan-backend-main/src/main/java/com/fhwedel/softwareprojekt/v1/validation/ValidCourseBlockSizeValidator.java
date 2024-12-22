package com.fhwedel.softwareprojekt.v1.validation;

import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for the {@link CourseReqDTO} class-level constraint that the blockSize must be less
 * than or equal to slotsPerWeek.
 */
public class ValidCourseBlockSizeValidator
        implements ConstraintValidator<ValidCourseBlockSize, CourseReqDTO> {

    /**
     * Validates the `CourseReqDTO` object against the constraint.
     *
     * @param value the `CourseReqDTO` object to be validated
     * @param context the context in which the validation is executed
     * @return `true` if the validation succeeds, `false` otherwise
     */
    @Override
    public boolean isValid(CourseReqDTO value, ConstraintValidatorContext context) {
        return value.getBlockSize() <= value.getSlotsPerWeek();
    }
}
