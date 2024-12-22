package com.fhwedel.softwareprojekt.v1.model.constraints;

import com.fhwedel.softwareprojekt.v1.model.enums.ConstraintValue;
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
@Table(name = "course_distribution_constraints")
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeCourseDistributionConstraint {
    @Id @GeneratedValue private UUID id;

    @Column @NotNull private String employeeAbbreviation;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private ConstraintValue constraintValue;
}
