package com.fhwedel.softwareprojekt.v1.model;

import java.time.DayOfWeek;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "course_timeslots")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CourseTimeslot {

    @Id @GeneratedValue private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_course_id")
    @ToString.Exclude // exclude in string representation to avoid recursive self-referencing
    private Course course;

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
        if (!(o instanceof CourseTimeslot that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
