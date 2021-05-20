package io.recheck.uoi.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
public @interface CountryConstraint {

    String message() default "The country acronym you provided '${validatedValue}' is not recognized";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
