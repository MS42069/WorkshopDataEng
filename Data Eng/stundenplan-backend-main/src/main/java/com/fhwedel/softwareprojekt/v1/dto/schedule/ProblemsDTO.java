package com.fhwedel.softwareprojekt.v1.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fhwedel.softwareprojekt.v1.dto.TimeslotResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseBasicResDTO;
import com.fhwedel.softwareprojekt.v1.dto.course.RoomBasicResDTO;
import com.fhwedel.softwareprojekt.v1.scheduler.Constraint;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "DTO returned if an event cannot be scheduled because this would lead "
                        + "to an inconsistency (i.e. one or more constraints are violated).")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemsDTO implements Serializable {

    @Schema(description = "The event whose scheduling led to the problem")
    private WeekEventDTO event;

    @Builder.Default private List<ProblemDTO> problems = new ArrayList<>();

    /**
     * Returns a set containing all violated constraints by mapping the listed problems to their
     * constraints.
     *
     * @return set of all violated constraints.
     */
    @Schema(hidden = true)
    @JsonIgnore
    public Set<Constraint> getViolatedConstraints() {
        return this.problems.stream()
                .map(ProblemDTO::getViolatedConstraint)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "ProblemsDTO(\n"
                + "  event="
                + event
                + ",\n  problems="
                + convertProblemsToString()
                + "\n  )";
    }

    private String convertProblemsToString() {
        return problems.stream()
                .map(ProblemDTO::toString)
                .collect(Collectors.joining("\n", "[", "]"));
    }

    @Schema(
            description =
                    "Provides detailed data about a week event, which represents a weekly date on which "
                            + "a course may be scheduled")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class WeekEventDTO implements Serializable {
        private CourseBasicResDTO course;
        private DayOfWeek weekday;
        private Integer week;
        @Builder.Default private List<TimeslotResDTO> timeslots = new ArrayList<>();
        @Builder.Default private List<RoomBasicResDTO> rooms = new ArrayList<>();
    }
}
