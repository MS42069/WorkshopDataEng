package com.fhwedel.softwareprojekt.v1.validation;

import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom Annotation to validate the {@link Timeslot} class-level constraint that the start time
 * must be before the end time.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidTimeslotTimeValidator.class)
public @interface ValidTimeslotTime {
    String message() default "Invalid Timeslot: start time must be before end time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
