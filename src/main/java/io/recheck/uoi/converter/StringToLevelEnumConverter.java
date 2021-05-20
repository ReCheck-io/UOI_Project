package io.recheck.uoi.converter;

import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.exceptions.EnumConversionFailedException;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLevelEnumConverter implements Converter<String, LEVEL> {

    @SneakyThrows
    @Override
    public LEVEL convert(String value) {
        try {
            return LEVEL.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new EnumConversionFailedException(LEVEL.class, value);
        }
    }
}