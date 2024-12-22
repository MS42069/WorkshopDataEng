package com.fhwedel.softwareprojekt.v1.handler;

import static com.fhwedel.softwareprojekt.v1.errorHandling.APIError.DATABASE_CONSTRAINT_VIOLATION;
import static com.fhwedel.softwareprojekt.v1.errorHandling.APIError.VALIDATION_ERROR;

import com.fhwedel.softwareprojekt.v1.dto.ErrorDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.ProblemsDTO;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.SchedulerProblemException;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/** Controller Advice to handle exceptions for all controllers globally. */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles exceptions thrown by the database due to an insert or update attempt resulting in an
     * integrity constraint violation.
     *
     * @param ex the {@link DataIntegrityViolationException} to handle
     * @return {@link ErrorDTO} which describes the encountered error and contains a more detailed
     *     error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorDTO handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorDTO response = new ErrorDTO();

        log.error("Handling " + ex.getClass().getSimpleName() + ": " + ex.getMessage());

        String message = ex.getMostSpecificCause().getMessage();
        response.setError(DATABASE_CONSTRAINT_VIOLATION.getMessage());
        response.setMessage(message);

        return response;
    }

    /**
     * Exception handler for {@link MethodArgumentNotValidException}, i.e. validation errors thrown
     * when validation on an argument annotated {@code @Valid} fails.
     *
     * @param ex the {@link MethodArgumentNotValidException} to handle
     * @return {@link ErrorDTO} which describes the encountered error and contains a more detailed
     *     error message.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorDTO response = new ErrorDTO();

        log.error("Handling " + ex.getClass().getSimpleName() + "\n  " + ex.getMessage());

        // for each field error concatenate field name and error msg
        // for each global validation error concatenate object name and error msg
        List<String> globalErrorList =
                ex.getBindingResult().getGlobalErrors().stream()
                        .map(
                                error ->
                                        "'"
                                                + error.getObjectName()
                                                + "': "
                                                + error.getDefaultMessage())
                        .collect(Collectors.toCollection(ArrayList::new));

        // for each error associated with a field concatenate field name and error msg
        List<String> fieldErrorList =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        "'"
                                                + fieldError.getField()
                                                + "': "
                                                + fieldError.getDefaultMessage())
                        .toList();
        globalErrorList.addAll(fieldErrorList);

        String errorListAsString =
                globalErrorList.stream().collect(Collectors.joining(", ", "[", "]"));

        response.setError(VALIDATION_ERROR.getMessage());
        response.setMessage(
                "Invalid request body: Validation failed with "
                        + globalErrorList.size()
                        + " error(s): "
                        + errorListAsString);

        return response;
    }

    /**
     * Exception Handler for {@link ResponseStatusException} thrown by the application for various
     * reasons during the processing of requests. Serves mainly for logging purposes.
     *
     * @param ex the {@link ResponseStatusException} to handle
     */
    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusException(ResponseStatusException ex) {
        log.error("Request raised  {}", ex.toString());
        // rethrow to have a default handling by the framework
        throw ex;
    }

    /**
     * Exception handler for {@link SchedulerProblemException} thrown when a scheduling problem
     * occurs.
     *
     * @param ex the {@link SchedulerProblemException} to handle
     * @return {@link ProblemsDTO} containing information about the scheduling problem.
     */
    @ExceptionHandler(SchedulerProblemException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @Hidden
    public ProblemsDTO handleSchedulerProblemException(SchedulerProblemException ex) {
        log.error("Request raised {}", ex.toString());
        return ex.getProblems();
    }
}
