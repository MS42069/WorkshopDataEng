package com.fhwedel.softwareprojekt.v1.dto.course;

import com.fhwedel.softwareprojekt.v1.dto.IdWrapperDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Represents a combination of rooms suitable to host a course session "
                        + "(in the meaning of a logical AND Connection).")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRoomComboReqDTO implements Serializable {
    @Schema(
            description =
                    "Set of rooms (identified by their ID) that make up the combination, "
                            + "must contain at least one room; duplicate rooms will be ignored")
    @Size(min = 1)
    @NotNull
    @Builder.Default
    private Set<@NotNull @Valid IdWrapperDTO> rooms = new LinkedHashSet<>();
}
