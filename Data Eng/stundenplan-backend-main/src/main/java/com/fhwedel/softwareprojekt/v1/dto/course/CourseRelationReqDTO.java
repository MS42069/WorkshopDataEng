package com.fhwedel.softwareprojekt.v1.dto.course;

import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Contains for each different relationship-type a list of courses to which the 'owner' "
                        + "course of this DTO has a corresponding relationship.\n"
                        + "Serves as Request DTO that is sent to the server to create and remove course relationships of a "
                        + "specific course.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRelationReqDTO implements Serializable {

    @Schema(
            description =
                    "Set of courses (or their ID's) that may take place in parallel to the owner course of this DTO. "
                            + "Duplicates will be ignored. Default: empty set")
    @NotNull
    @Builder.Default
    private Set<@NotNull IdWrapperDTO> mayBeParallelTo = new HashSet<>();

    @Schema(
            description =
                    "Set of courses (or their ID's) before which the owner course must take place. "
                            + "The 'before' is to be understood in the context of a week: "
                            + "i.e. this course must be held in the weekly schedule before the referenced courses.")
    @NotNull
    @Builder.Default
    private Set<@NotNull IdWrapperDTO> mustBeHeldBefore = new HashSet<>();

    @Schema(
            description =
                    "Set of courses (or their ID's) after which the owner course must take place.")
    @NotNull
    @Builder.Default
    private Set<@NotNull IdWrapperDTO> mustBeHeldAfter = new HashSet<>();
}
