package com.fhwedel.softwareprojekt.v1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedEntityGraph(
        name = "subgraph-semester.courses",
        attributeNodes = {
            @NamedAttributeNode("courses"),
        })
@Entity
@Table(name = "degreeSemesters")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DegreeSemester implements IdentifiableEntity {
    @Id @GeneratedValue private UUID id;

    @Column @NotNull @Positive private Integer semesterNumber;

    @Column private String extensionName;

    @Column @NotNull @PositiveOrZero private Integer attendees;

    @ManyToOne
    @JoinColumn(name = "fk_degree_id")
    private Degree degree;

    @ManyToMany
    @JoinTable(
            name = "degreesemester_course",
            joinColumns = @JoinColumn(name = "fk_degreesemester_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_course_id"))
    private List<Course> courses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DegreeSemester that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
