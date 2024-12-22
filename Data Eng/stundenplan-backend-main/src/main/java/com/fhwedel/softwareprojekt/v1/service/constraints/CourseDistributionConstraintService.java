package com.fhwedel.softwareprojekt.v1.service.constraints;

import com.fhwedel.softwareprojekt.v1.converter.constraints.CourseDistributionConstraintConverter;
import com.fhwedel.softwareprojekt.v1.dto.constraints.CourseDistributionConstraintReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.constraints.CourseDistributionConstraintResDTO;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeCourseDistributionConstraint;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeCourseDistributionConstraintRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/** Service class for managing EmployeeCourseDistributionConstraint operations. */
@Service
@RequiredArgsConstructor
public class CourseDistributionConstraintService {

    /** Repository for EmployeeCourseDistributionConstraint entities. */
    private final EmployeeCourseDistributionConstraintRepository
            employeeCourseDistributionConstraintRepository;

    /** Converter for CourseDistributionConstraint entities and DTOs. */
    private final CourseDistributionConstraintConverter courseDistributionConstraintConverter;

    /** ModelMapper for mapping DTOs to entities. */
    private final ModelMapper modelMapper;

    /**
     * Handles the saving of CourseDistributionConstraint entities.
     *
     * @param dto The DTO containing CourseDistributionConstraint information to be saved.
     */
    public void handleSave(CourseDistributionConstraintReqDTO dto) {
        if (dto != null && dto.getEmployeeAbbreviation() != null) {
            if (dto.getConstraintValue() != null) {
                saveOrUpdate(dto);
            } else {
                delete(dto);
            }
        }
    }

    /**
     * Saves or updates a CourseDistributionConstraint entity based on the DTO.
     *
     * @param dto The DTO containing CourseDistributionConstraint information to be saved or
     *     updated.
     */
    private void saveOrUpdate(CourseDistributionConstraintReqDTO dto) {

        EmployeeCourseDistributionConstraint constraint =
                employeeCourseDistributionConstraintRepository
                        .findByEmployeeAbbreviation(dto.getEmployeeAbbreviation())
                        .orElseGet(EmployeeCourseDistributionConstraint::new);

        modelMapper.map(dto, constraint);
        employeeCourseDistributionConstraintRepository.save(constraint);
    }

    /**
     * Deletes a CourseDistributionConstraint entity based on the DTO.
     *
     * @param dto The DTO containing CourseDistributionConstraint information to be deleted.
     */
    private void delete(CourseDistributionConstraintReqDTO dto) {

        Optional<EmployeeCourseDistributionConstraint> constraint =
                employeeCourseDistributionConstraintRepository.findByEmployeeAbbreviation(
                        dto.getEmployeeAbbreviation());
        constraint.ifPresent(employeeCourseDistributionConstraintRepository::delete);
    }

    /**
     * Retrieves a CourseDistributionConstraintResDTO based on the employee's abbreviation.
     *
     * @param abbreviation The abbreviation of the employee.
     * @return A CourseDistributionConstraintResDTO containing constraint information.
     */
    public CourseDistributionConstraintResDTO getResDTO(String abbreviation) {
        return employeeCourseDistributionConstraintRepository
                .findByEmployeeAbbreviation(abbreviation)
                .map(courseDistributionConstraintConverter::convertToResDTO)
                .orElseGet(CourseDistributionConstraintResDTO::new);
    }
}
