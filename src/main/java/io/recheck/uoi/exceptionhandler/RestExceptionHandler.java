package io.recheck.uoi.exceptionhandler;

import io.recheck.uoi.exceptions.NodeNotFoundException;
import io.recheck.uoi.exceptions.ValidationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralError(Exception ex) {
        log.error("", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, see the debug message for more.");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NodeNotFoundException.class)
    public ResponseEntity<Object> handleNNFError(Exception ex) {
        log.error("", ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "This node is not existing in the database.");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler( {
            ValidationErrorException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> handleValidationError(Exception ex) {
        log.error("", ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, "A validation error has been encountered.");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        log.error("", ex);

        StringBuilder message = new StringBuilder();

        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Set<String> fieldsSet = fieldErrors.stream()
                .map(violation -> violation.getField() + ": " + violation.getDefaultMessage())
                .collect(Collectors.toSet());
        Set<String> globalSet = globalErrors.stream()
                .map(violation -> violation.getObjectName() + ": " + violation.getDefaultMessage())
                .collect(Collectors.toSet());

        message.append("Argument Not Valid:");
        if (!globalSet.isEmpty()) {
            for (String violation : globalSet) {
                message.append(" ");
                message.append(violation);
                message.append(";");
            }
        }
        if (!fieldsSet.isEmpty()) {
            for (String violation : fieldsSet) {
                message.append(" ");
                message.append(violation);
                message.append(";");
            }
        }

        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, message.toString());
        return buildResponseEntity(apiError);
    }


}