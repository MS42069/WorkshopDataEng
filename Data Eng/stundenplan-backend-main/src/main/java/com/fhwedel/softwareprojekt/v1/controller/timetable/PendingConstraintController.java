package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.dto.AcceptDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.EmployeeCheckTimeslotConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.service.constraints.TimeslotConstraintService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/employee-worktime-constraints")
@RequiredArgsConstructor
@Slf4j
public class PendingConstraintController {

    private final TimeslotConstraintService timeslotConstraintService;

    @GetMapping
    public List<EmployeeCheckTimeslotConstraintResDTO> getPendingConstraints() {
        return timeslotConstraintService.getPendingConstraints();
    }

    @PostMapping(path = "/{constraintId}")
    public void acceptPendingConstraint(
            @RequestBody AcceptDTO obj, @PathVariable UUID constraintId) {
        timeslotConstraintService.handleAcceptOrDecline(constraintId, obj.getAccept());
    }
}
