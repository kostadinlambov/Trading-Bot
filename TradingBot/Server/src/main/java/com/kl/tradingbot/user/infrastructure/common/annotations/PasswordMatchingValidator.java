package com.kl.tradingbot.user.infrastructure.common.annotations;

import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {

  @Override
  public void initialize(PasswordMatching constraintAnnotation) {

  }

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    if (o instanceof UserRegisterBindingModel) {
      UserRegisterBindingModel user = (UserRegisterBindingModel) o;
      return user.getPassword().equals(user.getConfirmPassword());
    }
    return false;
  }
}
