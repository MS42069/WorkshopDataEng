package com.fhwedel.softwareprojekt.v1.model;

import java.time.DayOfWeek;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "worktimes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class WorkTime {
    @Id @GeneratedValue private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_employee_id")
    @ToString.Exclude // exclude in string representation to avoid recursive self-referencing
    private Employee employee;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private DayOfWeek weekday;

    @ManyToOne
    @JoinColumn(name = "fk_timeslot_id", nullable = false)
    private Timeslot timeslot;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkTime workTime)) return false;
        return Objects.equals(id, workTime.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
