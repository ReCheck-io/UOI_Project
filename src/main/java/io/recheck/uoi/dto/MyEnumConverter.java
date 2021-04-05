package io.recheck.uoi.dto;

import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.exceptions.ValidationErrorException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MyEnumConverter implements Converter<String, LEVEL> {

    @SneakyThrows
    @Override
    public LEVEL convert(String value) {
        try {
            return LEVEL.valueOf(value.toUpperCase());
        }catch (Exception e){
            throw new ValidationErrorException("This LEVEL is not recognized in the database.");
        }

    }
}