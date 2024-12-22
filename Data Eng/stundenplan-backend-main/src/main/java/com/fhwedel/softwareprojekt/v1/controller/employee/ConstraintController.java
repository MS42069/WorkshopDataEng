package com.fhwedel.softwareprojekt.v1.controller.employee;

import com.fhwedel.softwareprojekt.v1.dto.constraints.ConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.ConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.service.constraints.ConstraintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller class for managing preference profile constraints. */
@RestController
@RequestMapping("/v1/preference-profile")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "constraints", description = "Everything about constraints")
public class ConstraintController {

    /** The constraint service for managing preference profile constraints. */
    private final ConstraintService constraintService;

    /**
     * Retrieves the constraints for a preference profile based on the employee abbreviation.
     *
     * @param employeeAbbreviation The abbreviation of the employee
     * @return The constraints associated with the preference profile
     */
    @GetMapping("/{employeeAbbreviation}")
    public ConstraintResDTO getConstraintsForPreferenceProfile(
            @PathVariable("employeeAbbreviation") String employeeAbbreviation) {
        return constraintService.getConstraintsForPreferenceProfile(employeeAbbreviation);
    }

    /**
     * Handles the saving of constraint entities for a preference profile.
     *
     * @param constraintDTO The constraint data to be saved
     */
    @Transactional
    @PostMapping("/save")
    public void saveEntities(@Valid @RequestBody ConstraintReqDTO constraintDTO) {
        constraintService.handleSave(constraintDTO);
    }
}
