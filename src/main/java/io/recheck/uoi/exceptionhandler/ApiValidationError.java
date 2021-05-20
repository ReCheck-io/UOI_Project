package io.recheck.uoi.exceptionhandler;

import lombok.Data;

@Data
public class ApiValidationError extends ApiSubError {
    private String field;
    private String message;

    ApiValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}