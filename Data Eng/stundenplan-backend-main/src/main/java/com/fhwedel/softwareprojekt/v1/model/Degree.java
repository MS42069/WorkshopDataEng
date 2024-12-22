package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.model.types.SchoolType;
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
@Table(name = "degrees")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Degree {

    @Id @GeneratedValue private UUID id;

    @Column
    @NotBlank(message = "name can't be Blank")
    private String name;

    @Column
    @NotBlank(message = "shortName can't be Blank")
    private String shortName;

    @Column @NotNull @Positive private Integer semesterAmount;

    @ManyToOne
    @JoinColumn(name = "fk_roomType_id")
    private SchoolType schoolType;

    @Column
    @NotBlank(message = "studyRegulation can't be Blank")
    private String studyRegulation;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.REMOVE)
    private List<DegreeSemester> semesters;

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Degree degree)) return false;
        return Objects.equals(id, degree.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
