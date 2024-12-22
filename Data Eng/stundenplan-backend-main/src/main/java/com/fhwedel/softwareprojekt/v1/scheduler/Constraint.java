package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.model.Course;

/**
 * Represents the different constraints that are considered by the scheduler when checking the
 * {@link ClassScheduler#checkAdmissibility admissibility} and generating the {@link
 * ClassScheduler#findAllOptionsFor(Course) options}.
 */
public enum Constraint {

    /**
     * An employee may not have two events at the same time and the selected time slot must be
     * permitted as working time for the employee.
     */
    EMPLOYEE_AVAILABILITY(
            "Ein Mitarbeiter hat zur selben Zeit schon eine andere Veranstaltung "
                    + "oder der ausgewählte Zeitslot ist für einen Mitarbeiter als Arbeitszeit nicht freigegeben."),
    /**
     * Two events must not take place at the same time in the same room.
     */
    ROOM_AVAILABILITY(
            "Im ausgewählten Raum bzw. in den ausgewählten Räumen findet zur selben Zeit "
                    + "schon eine andere Veranstaltung statt."),
    /**
     * A degree semester may not have two events at the same time.
     */
    DEGREE_SEMESTER_CONFLICT(
            "Ein Fachbereich hat zur selben Zeit schon eine andere Veranstaltung."),
    /**
     * The time slots selected for the event must be of the specified block size.
     */
    BLOCK_SIZE("Der ausgewählte Zeitslot entspricht nicht der erwarteten Blockgröße."),
    /**
     * A course may not be scheduled more often than the defined number of (time) slots per week.
     */
    SLOTS_PER_WEEK(
            "Die Veranstaltung ist bereits vollständig verplant für die definierte Anzahl "
                    + "an Terminen pro Woche."),
    /**
     * A course event may only take place in a room or a combination of rooms that is suited for
     * this course (i.e. contained in {@link Course#getSuitedRooms()}.
     */
    ROOM_COMBINATION(
            "Der ausgewählte Raum bzw. die ausgewählte Raumkombination ist für die "
                    + "Veranstaltung nicht freigegeben."),
    /**
     * An event may only be scheduled in the time slots that are explicitly allowed for that course
     * (i.e. contained in {@link Course#getCourseTimeslots()}
     */
    COURSE_TIMESLOT("Der ausgewählte Zeitslot ist für die Veranstaltung nicht freigegeben."),
    /**
     * The relation to other courses (e.g. must-take place before) must be adhered to during
     * scheduling.
     */
    COURSE_RELATION(
            "Der ausgewählte Zeitslot ist durch eine \"Muss stattfinden Vor/Nach\" Bedingung blockiert"),
    EMPLOYEE_TIMESLOT_WISH("Ein Mitarbeiter hat für diesen Zeitslot als Hardconstraint eingetragen, dass dort keine " +
            "Veranstaltung stattfinden soll.");

    /**
     * Message to be displayed when the constraint is violated
     */
    private final String message;

    Constraint(String message) {
        this.message = message;
    }

    /**
     * Returns the default message associated with this constraint.
     *
     * @return the associated message.
     */
    public String getMessage() {
        return this.message;
    }
}
