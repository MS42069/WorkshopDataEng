package com.fhwedel.softwareprojekt.v1.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "roomcombinations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RoomCombination {

    @Id @GeneratedValue private UUID id;

    @JoinTable(
            name = "roomcombinations_rooms",
            joinColumns = @JoinColumn(name = "fk_roomcombination_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_rooms_id"))
    @ManyToMany
    @Size(min = 1)
    @NotNull
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "fk_event_id", nullable = false)
    @ToString.Exclude
    private Course course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomCombination that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
