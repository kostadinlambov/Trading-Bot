package com.kl.tradingbot.user.infrastructure.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.springframework.stereotype.Component;

@Component
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

  String message() default "Username already exists.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
