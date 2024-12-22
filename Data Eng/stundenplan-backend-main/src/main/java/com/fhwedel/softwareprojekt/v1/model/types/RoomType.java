package com.fhwedel.softwareprojekt.v1.model.types;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roomTypes", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class RoomType {
    @Id @GeneratedValue private UUID id;

    @NotBlank(message = "no room-name was given")
    private String name;

    @NotNull() private Boolean online;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomType roomType)) {
            return false;
        }
        return Objects.equals(id, roomType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
