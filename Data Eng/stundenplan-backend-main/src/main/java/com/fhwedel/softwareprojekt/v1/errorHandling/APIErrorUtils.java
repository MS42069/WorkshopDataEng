package com.fhwedel.softwareprojekt.v1.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Utility class for handling API errors and generating appropriate exceptions. */
public final class APIErrorUtils {

    /**
     * Handles a constraint violation error by throwing a {@link ResponseStatusException} with a
     * {@link HttpStatus#CONFLICT} status and a formatted error message.
     *
     * @param entityId The identifier of the entity associated with the constraint violation.
     */
    public static void handleConstraintViolation(String entityId) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                String.format(APIError.DELETE_CONSTRAINT_VIOLATION.getMessage(), entityId));
    }
}
