package io.recheck.uoi.converter;

import io.recheck.external.systems.entity.CirdaxResourcesEnum;
import io.recheck.uoi.exceptions.EnumConversionFailedException;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCirdaxResourcesEnumConverter implements Converter<String, CirdaxResourcesEnum> {

    @SneakyThrows
    @Override
    public CirdaxResourcesEnum convert(String value) {
        try {
            return CirdaxResourcesEnum.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new EnumConversionFailedException(CirdaxResourcesEnum.class, value);
        }
    }
}
