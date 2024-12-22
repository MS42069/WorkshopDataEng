package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.util.SpecialEventType;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "special_events")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SpecialEvent {

    @Id @GeneratedValue private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Column @NotNull private LocalDate startDate;

    @Column @NotNull private LocalDate endDate;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private SpecialEventType specialEventType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialEvent that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
