package com.localweb.storeapp.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    public String message() default "Please provide valid email format!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
