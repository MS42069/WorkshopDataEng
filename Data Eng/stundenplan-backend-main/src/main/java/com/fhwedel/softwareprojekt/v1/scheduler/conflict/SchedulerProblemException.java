package com.fhwedel.softwareprojekt.v1.scheduler.conflict;

import com.fhwedel.softwareprojekt.v1.dto.schedule.ProblemsDTO;
import lombok.Getter;
import lombok.ToString;

/** Custom exception class representing a scheduler problem with associated details. */
@Getter
@ToString
public class SchedulerProblemException extends RuntimeException {

    /** The ProblemsDTO associated with the exception. */
    private final ProblemsDTO problems;

    /**
     * Constructs a new SchedulerProblemException with the specified ProblemsDTO.
     *
     * @param problems The ProblemsDTO representing the problems encountered.
     */
    public SchedulerProblemException(ProblemsDTO problems) {
        this.problems = problems;
    }

    /**
     * Constructs a new SchedulerProblemException with the specified detail message and ProblemsDTO.
     *
     * @param message The detail message.
     * @param problems The ProblemsDTO representing the problems encountered.
     */
    public SchedulerProblemException(String message, ProblemsDTO problems) {
        super(message);
        this.problems = problems;
    }
}
