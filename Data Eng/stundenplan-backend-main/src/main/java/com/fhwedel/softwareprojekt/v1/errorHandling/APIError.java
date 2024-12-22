package com.fhwedel.softwareprojekt.v1.errorHandling;

/** Enum representing different API error types. */
public enum APIError {
    /**
     * Error indicating that an entity with a specific ID is already in use and therefore cannot be
     * deleted.
     */
    DELETE_CONSTRAINT_VIOLATION(
            "entity with id '%s' is already in use and therefore cannot be deleted"),

    /** Error indicating that an entity with a specific ID could not be found. */
    ENTITY_NOT_FOUND("entity '%s' with id '%s' could not be found"),

    /** Error indicating an internal server error. */
    INTERNAL_SERVER_ERROR("Internal Server Error occurred! Try Again!"),

    /** Error indicating a validation error. */
    VALIDATION_ERROR("Validation Error"),

    /** Error indicating a database constraint violation. */
    DATABASE_CONSTRAINT_VIOLATION("Database Constraint Violation");

    /** The error message associated with the API error. */
    private final String message;

    /**
     * Constructor for initializing an APIError with a specific error message.
     *
     * @param message The error message associated with the API error.
     */
    APIError(String message) {
        this.message = message;
    }

    /**
     * Get the error message associated with the API error.
     *
     * @return the error message
     */
    public String getMessage() {
        return this.message;
    }
}
