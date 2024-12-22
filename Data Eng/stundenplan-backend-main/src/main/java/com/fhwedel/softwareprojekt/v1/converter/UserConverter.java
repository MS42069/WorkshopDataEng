package com.fhwedel.softwareprojekt.v1.converter;

import com.fhwedel.softwareprojekt.v1.dto.UserDTO;
import com.fhwedel.softwareprojekt.v1.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A component class that provides methods for converting User entities to UserDTO objects and vice
 * versa.
 */
@Component
@RequiredArgsConstructor
public class UserConverter {

    /** The ModelMapper instance used for mapping between different representations of objects. */
    private final ModelMapper modelMapper;

    /**
     * Converts a User entity to a UserDTO object.
     *
     * @param user The User entity to be converted.
     * @return The converted UserDTO object.
     */
    public UserDTO convertEntityToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
