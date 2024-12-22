package com.fhwedel.softwareprojekt.v1.model;

import com.fhwedel.softwareprojekt.v1.model.types.CourseType;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.*;

@NamedEntityGraph(
        name = "scheduler.graph.course.with.employees",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode(value = "lecturers", subgraph = "scheduler.graph.course.lecturers"),
        },
        subgraphs = {
            @NamedSubgraph(
                    name = "scheduler.graph.course.lecturers",
                    attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode(
                                value = "courses",
                                subgraph = "scheduler.graph.course.lecturers.courses")
                    }),
            @NamedSubgraph(
                    name = "scheduler.graph.course.lecturers.courses",
                    attributeNodes = {
                        @NamedAttributeNode("id"),
                    })
        })
@NamedEntityGraph(
        name = "scheduler.graph.course.with.semesters",
        attributeNodes = {
            @NamedAttributeNode("id"),
            @NamedAttributeNode(value = "semesters", subgraph = "scheduler.graph.course.semesters"),
        },
        subgraphs = {
            @NamedSubgraph(
                    name = "scheduler.graph.course.semesters",
                    attributeNodes = {
                        @NamedAttributeNode("id"),
                        @NamedAttributeNode(
                                value = "courses",
                                subgraph = "scheduler.graph.course.semesters.courses")
                    }),
            @NamedSubgraph(
                    name = "scheduler.graph.course.semesters.courses",
                    attributeNodes = {
                        @NamedAttributeNode("id"),
                    })
        })
@Entity
@Table(
        name = "courses",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fk_timetable_id", "casID"}))
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Course implements IdentifiableEntity {

    @Id @GeneratedValue private UUID id;

    // TODO what is casID?
    @Column
    @NotBlank(message = "casID can't be Blank")
    private String casID;

    @Column
    @NotBlank(message = "name can't be Blank")
    private String name;

    @Column private String abbreviation;

    @Column private String description;

    // TODO what is blockSize?
    @Column @NotNull @Positive private Integer blockSize;

    @Column @NotNull @Positive private Integer slotsPerWeek;

    @Column @NotNull @Positive private Integer weeksPerSemester;

    // TODO what is courseType?
    @ManyToOne
    @JoinColumn(name = "fk_roomType_id")
    private CourseType courseType;

    @JoinTable(
            name = "course_lecturers",
            joinColumns = @JoinColumn(name = "fk_course_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_employee_id"))
    @ManyToMany
    @ToString.Exclude
    private List<Employee> lecturers = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<RoomCombination> suitedRooms = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    @ToString.Exclude
    private List<DegreeSemester> semesters = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private List<WeekEvent> weekEvents =
            new ArrayList<>(); // all scheduled (week) dates of a course

    @OneToMany(mappedBy = "courseA", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<CourseRelation> courseRelationsA = new ArrayList<>();

    @OneToMany(mappedBy = "courseB", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<CourseRelation> courseRelationsB = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<CourseTimeslot> courseTimeslots =
            new LinkedList<>(); // Default initialization with empty list

    @ManyToOne
    @JoinColumn(name = "fk_timetable_id", nullable = false)
    private Timetable timetable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
