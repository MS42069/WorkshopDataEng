package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.util.RelationType;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "course_relations",
        uniqueConstraints =
                @UniqueConstraint(
                        columnNames = {"relation_type", "fk_course_a_id", "fk_course_b_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CourseRelation {

    @Id @GeneratedValue private UUID id;

    @Column(name = "relation_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RelationType relationType;

    @ManyToOne
    @JoinColumn(name = "fk_course_a_id", nullable = false)
    private Course courseA;

    @ManyToOne
    @JoinColumn(name = "fk_course_b_id", nullable = false)
    private Course courseB;

    @Override
    public String toString() {
        // just for better logging
        return "CourseRelation("
                + "id="
                + id
                + ", relationType="
                + relationType
                + ", courseA="
                + courseA.getId()
                + ", courseB="
                + courseB.getId()
                + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseRelation that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
