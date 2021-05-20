package io.recheck.uoi.exceptions;

import java.util.Arrays;

public class EnumConversionFailedException extends Exception {

    public EnumConversionFailedException(Class<?> enumType, String value) {
        super(String.format("Value '%s' is not of type '%s'. Allowed values are: %s",
                        value,enumType.getSimpleName(), Arrays.toString(enumType.getEnumConstants())));
    }
}
