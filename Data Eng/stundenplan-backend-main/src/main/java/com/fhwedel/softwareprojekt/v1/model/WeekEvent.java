package com.fhwedel.softwareprojekt.v1.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a weekly event date on which a course is held. Assigns to a course a specific day of
 * the week, a block of timeslots and a combination of rooms in which the event of the course is to
 * take place.
 */
@NamedEntityGraph(
        name = "scheduler.graph.weekevent.with.rooms",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("weekday"),
            @NamedAttributeNode(value = "rooms", subgraph = "scheduler.graph.weekevent.rooms"),
        },
        subgraphs = {
            @NamedSubgraph(
                    name = "scheduler.graph.weekevent.rooms",
                    attributeNodes = {
                        @NamedAttributeNode("id"),
                    })
        })
@NamedEntityGraph(
        name = "scheduler.graph.weekevent.with.timeslots",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode("weekday"),
            @NamedAttributeNode(
                    value = "timeslots",
                    subgraph = "scheduler.graph.weekevent.timeslots"),
        },
        subgraphs = {
            @NamedSubgraph(
                    name = "scheduler.graph.weekevent.timeslots",
                    attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode("startTime"),
                        @NamedAttributeNode("endTime"),
                    }),
        })
@NamedEntityGraph(
        name = "scheduler.graph.weekevent.with.course",
        attributeNodes = {
            @NamedAttributeNode(value = "course", subgraph = "scheduler.graph.weekevent.course"),
        },
        subgraphs = {
            @NamedSubgraph(
                    name = "scheduler.graph.weekevent.course",
                    attributeNodes = {
                        @NamedAttributeNode(
                                value = "lecturers"
                                //										subgraph = "scheduler.graph.weekevent.course.lecturers"
                                ),
                        //								@NamedAttributeNode(
                        //										value="semesters"
                        ////										subgraph = "scheduler.graph.weekevent.course.semesters"
                        //								),
                    }),
            //				@NamedSubgraph(
            //						name = "scheduler.graph.weekevent.course.lecturers",
            //						attributeNodes = {
            //								@NamedAttributeNode("id"),
            //						}
            //				),
            //				@NamedSubgraph(
            //						name = "scheduler.graph.weekevent.course.semesters",
            //						attributeNodes = {
            //								@NamedAttributeNode("id"),
            //						}
            //				)
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course_events")
@ToString
public class WeekEvent {

    @Id @GeneratedValue private UUID id;

    @Column
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private DayOfWeek weekday;

    @Column @NotNull private Integer week;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // @NotBlank(message = "specify if the room is online or offline")
    // private WeekEventType weekEventType;

    @ManyToMany
    @JoinTable(
            name = "week_events_timeslots",
            joinColumns = @JoinColumn(name = "week_event_id"),
            inverseJoinColumns = @JoinColumn(name = "timeslot_id"))
    @ToString.Exclude
    private List<Timeslot> timeslots =
            new ArrayList<>(); // assumption that the time slots are in a block

    @ManyToMany
    @JoinTable(
            name = "week_events_rooms",
            joinColumns = @JoinColumn(name = "week_event_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekEvent weekEvent)) return false;
        return Objects.equals(id, weekEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
