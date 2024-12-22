package com.fhwedel.softwareprojekt.v1.model;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "timeslots",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fk_timetable_id", "index"}))
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class Timeslot implements IdentifiableEntity {
    @Id @GeneratedValue private UUID id;

    @Column @NotNull private LocalTime startTime;

    @Column @NotNull private LocalTime endTime;

    @PositiveOrZero @NotNull private Integer index;

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeslot timeslot)) return false;
        return Objects.equals(id, timeslot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
