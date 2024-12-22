package com.fhwedel.softwareprojekt.v1.validation;

import com.fhwedel.softwareprojekt.v1.dto.TimeslotReqDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for the {@link TimeslotReqDTO} class-level constraint that the start time must be
 * before the end time. This validator checks if the `startTime` property of a timeslot request DTO
 * is before the `endTime` property. If this condition is met, the validation is considered
 * successful.
 */
public class ValidTimeslotTimeValidator
        implements ConstraintValidator<ValidTimeslotTime, TimeslotReqDTO> {

    /**
     * Validates the `TimeslotReqDTO` object against the constraint.
     *
     * @param timeslotReqDTO the `TimeslotReqDTO` object to be validated
     * @param constraintValidatorContext the context in which the validation is executed
     * @return `true` if the validation succeeds, `false` otherwise
     */
    @Override
    public boolean isValid(
            TimeslotReqDTO timeslotReqDTO, ConstraintValidatorContext constraintValidatorContext) {
        return timeslotReqDTO.getStartTime().isBefore(timeslotReqDTO.getEndTime());
    }
}
