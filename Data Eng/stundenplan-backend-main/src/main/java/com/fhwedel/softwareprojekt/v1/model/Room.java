package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.model.types.RoomType;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fk_timetable_id", "identifier"}))
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Room implements IdentifiableEntity {

    @Id @GeneratedValue private UUID id;

    @NotBlank(message = "no name was given")
    private String name;

    @NotBlank(message = "no abbreviation was given")
    private String abbreviation;

    @NotBlank(message = "no identifier was given")
    private String identifier;

    @Column @PositiveOrZero @NotNull private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "fk_roomType_id", nullable = false)
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room room)) {
            return false;
        }
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
