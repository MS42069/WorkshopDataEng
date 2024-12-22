package com.fhwedel.softwareprojekt.v1.validation;

import com.fhwedel.softwareprojekt.v1.dto.course.CourseReqDTO;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom Annotation to validate the {@link CourseReqDTO} class-level constraint that the blockSize
 * must be less than or equal to slotsPerWeek.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidCourseBlockSizeValidator.class)
public @interface ValidCourseBlockSize {
    String message() default
            "Invalid block size: blockSize must be less than or equal to slotsPerWeek";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
