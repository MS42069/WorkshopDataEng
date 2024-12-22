package com.fhwedel.softwareprojekt.v1.scheduler.conflict;

import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeTimeslotConstraint;
import com.fhwedel.softwareprojekt.v1.scheduler.Constraint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conflict encountered by the scheduler and provides additional information to a
 * violated {@link Constraint}
 */
@ToString
@Getter
@Builder
public class Conflict {

    /**
     * Constraint to which this conflict applies
     */
    private final Constraint constraint;

    /**
     * Describes the conflict
     */
    @Setter
    private String description;

    /**
     * The employees affected by the conflict
     */
    private final List<Employee> employees = new ArrayList<>();

    /**
     * The rooms affected by the conflict
     */
    private final List<Room> rooms = new ArrayList<>();

    /**
     * The Degree semesters affected by the conflict
     */
    private final List<DegreeSemester> degreeSemesters = new ArrayList<>();

    /**
     * The weekEvents for which a relation constraints causes a conflict
     */
    private final List<WeekEvent> weekEvents = new ArrayList<>();

    private final List<EmployeeTimeslotConstraint> employeeTimeslotConstraint = new ArrayList<>();

    /**
     * Creates a new conflict that violates the given constraint and is initialized with the
     * constraint's default message
     *
     * @param constraint the violated constraint
     */
    public Conflict(Constraint constraint) {
        this.constraint = constraint;
        // Initialize description to default message associated with the given constraint
        this.description = constraint.getMessage();
    }

    /**
     * Creates a new conflict that violates the given constraint and provides a custom description.
     *
     * @param constraint  the violated constraint.
     * @param description a custom description of the conflict.
     */
    public Conflict(Constraint constraint, String description) {
        this.constraint = constraint;
        this.description = description;
    }
}
