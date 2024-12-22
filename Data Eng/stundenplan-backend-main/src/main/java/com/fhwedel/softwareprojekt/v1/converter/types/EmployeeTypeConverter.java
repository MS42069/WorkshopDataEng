package com.fhwedel.softwareprojekt.v1.converter.types;

import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.types.EmployeeTypeResDTO;
import com.fhwedel.softwareprojekt.v1.model.types.EmployeeType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Converter class for converting EmployeeType objects between different representations. */
@Component
@RequiredArgsConstructor
public class EmployeeTypeConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts an EmployeeTypeReqDTO object to an EmployeeType entity.
     *
     * @param employeeReqDTO The EmployeeTypeReqDTO to be converted.
     * @return The converted EmployeeType entity.
     */
    public EmployeeType convertEmployeeTypeDtoToEntity(EmployeeTypeReqDTO employeeReqDTO) {
        return modelMapper.map(employeeReqDTO, EmployeeType.class);
    }

    /**
     * Converts an EmployeeType entity to an EmployeeTypeResDTO object.
     *
     * @param employeeType The EmployeeType entity to be converted.
     * @return The converted EmployeeTypeResDTO object.
     */
    public EmployeeTypeResDTO convertEmployeeTypeEntityToResponseDTO(EmployeeType employeeType) {
        return modelMapper.map(employeeType, EmployeeTypeResDTO.class);
    }

    /**
     * Converts a collection of EmployeeType entities to a list of EmployeeTypeResDTO objects.
     *
     * @param employeeTypes The collection of EmployeeType entities to be converted.
     * @return The list of converted EmployeeTypeResDTO objects.
     */
    public List<EmployeeTypeResDTO> convertEmployeeTypeEntitiesToResponseDTOList(
            Collection<EmployeeType> employeeTypes) {
        return employeeTypes.stream()
                .map(this::convertEmployeeTypeEntityToResponseDTO)
                .collect(Collectors.toList());
    }
}
