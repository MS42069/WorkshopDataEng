package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.model.types.SemesterType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timetables")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Timetable {

    @Id @GeneratedValue private UUID id;

    @Column @NotNull private LocalDate startDate;

    @Column @NotNull private LocalDate endDate;

    @Column @Positive @NotNull private Integer numberOfWeeks;

    @Column
    @NotBlank(message = "name can't be blank")
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_semesterType_id", nullable = false)
    private SemesterType semesterType;

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.REMOVE)
    private List<SpecialEvent> specialEvents = new ArrayList<>();

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.REMOVE)
    private List<WeekEvent> weekSchedule = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timetable timetable)) return false;
        return Objects.equals(id, timetable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
