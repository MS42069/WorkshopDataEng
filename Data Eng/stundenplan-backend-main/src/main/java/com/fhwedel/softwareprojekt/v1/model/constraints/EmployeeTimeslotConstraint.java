package com.fhwedel.softwareprojekt.v1.model.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintType;
import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employee_timeslot_constraints")
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeTimeslotConstraint {
    @Id @GeneratedValue private UUID id;

    @Column @NotNull private String employeeAbbreviation;

    @Column
    @NotNull(message = "index must not be null")
    private Integer timeslotIndex;

    @Column
    @NotNull(message = "startTime must not be null")
    private LocalTime startTime;

    @Column
    @NotNull(message = "endTime must not be null")
    private LocalTime endTime;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek weekday;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private ConstraintType constraintType;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private ConstraintValue constraintValue;

    @Column private String reason;

    @Column @NotNull private Boolean isAccepted;
}
