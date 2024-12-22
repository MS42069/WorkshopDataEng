package com.fhwedel.softwareprojekt.v1.model.types;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employeeTypes", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeType {

    @Id @GeneratedValue private UUID id;

    @NotBlank(message = "no employee-name was given")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeType employeeType)) {
            return false;
        }
        return Objects.equals(id, employeeType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
