package com.fhwedel.softwareprojekt.v1.util;

import com.fhwedel.softwareprojekt.v1.model.CourseRelation;

/**
 * Represents different types of {@link CourseRelation relations} between two courses, which can be
 * considered during scheduling.
 */
public enum RelationType {
    /**
     * Course A must take place before course B and vice versa course B must take place after Course
     * A.
     *
     * <p>The 'before' is to be understood in the context of a week (not in the meaning of 'directly
     * before'). I.d. An event of course A must be held in the weekly schedule strictly before any
     * event of course B, if any event of course B exists.
     */
    A_MUST_BE_BEFORE_B,
    /**
     * Two courses must be scheduled to run in parallel to each other. This means that events from
     * both courses can take place at the same time slots in the weekly schedule.
     */
    MUST_BE_PARALLEL,
    /**
     * Two courses may be scheduled parallel to each other, even if they are held by the same
     * employee or attended by the same degree semester cohorts.
     */
    MAY_BE_PARALLEL,
}
