package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@NamedEntityGraph(
        name = "subgraph-lecturers.courses",
        attributeNodes = {@NamedAttributeNode("courses"), @NamedAttributeNode("workTimes")})
@Entity
@Table(
        name = "employees",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fk_timetable_id", "abbreviation"}))
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employee implements IdentifiableEntity {

    @Id @GeneratedValue private UUID id;

    @Column
    @NotNull(message = "no firstname was given")
    private String firstname;

    @Column
    @NotNull(message = "no lastname was given")
    private String lastname;

    @Column
    @NotBlank(message = "no abbreviation was given")
    private String abbreviation;

    @ManyToOne
    @JoinColumn(name = "fk_employeeType_id", nullable = false)
    private EmployeeType employeeType;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<WorkTime> workTimes = new LinkedList<>(); // Default initialization with empty list

    @ManyToMany(mappedBy = "lecturers")
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
