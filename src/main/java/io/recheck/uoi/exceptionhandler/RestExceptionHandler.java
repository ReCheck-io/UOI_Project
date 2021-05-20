package io.recheck.uoi.exceptionhandler;

import io.recheck.uoi.exceptions.EnumConversionFailedException;
import io.recheck.uoi.exceptions.NodeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

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

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        log.error("", ex);

        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ApiValidationError> fieldsSet = fieldErrors.stream()
                .map(violation -> {
                    String violationMessage = violation.getDefaultMessage();
                    if (violation.contains(Exception.class)) {
                        Exception nestedException = violation.unwrap(Exception.class);
                        Throwable rootCause = findCauseRootCause(nestedException);
                        if (rootCause instanceof EnumConversionFailedException) {
                            violationMessage = rootCause.getMessage();
                        }
                    }
                    return new ApiValidationError(violation.getField(), violationMessage);
                })
                .collect(Collectors.toList());
        List<ApiValidationError> globalSet = globalErrors.stream()
                .map(violation -> new ApiValidationError(violation.getObjectName(), violation.getDefaultMessage()))
                .collect(Collectors.toList());

        fieldsSet.addAll(globalSet);


        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, "Argument(s) Not Valid", fieldsSet);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private Throwable findCauseRootCause(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}