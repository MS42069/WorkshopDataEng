package com.fhwedel.softwareprojekt.v1.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        description =
                "Represents a combination of rooms suitable to host a course session "
                        + "(in the meaning of a logical AND connection). Provides besides the ID further data about the rooms.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRoomComboResDTO implements Serializable {
    @Schema(description = "List of rooms that make up the combination")
    private List<RoomBasicResDTO> rooms = new ArrayList<>();
}
