package io.recheck.uoi.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {

    private static final Set<String> ISO_COUNTRIES = new HashSet<>(Arrays.asList(Locale.getISOCountries()));

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(country)) {
            return true;
        }
        return ISO_COUNTRIES.contains(country.toUpperCase());
    }

}
