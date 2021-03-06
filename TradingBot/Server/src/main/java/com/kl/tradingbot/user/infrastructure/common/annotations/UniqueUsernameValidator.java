package com.kl.tradingbot.user.infrastructure.common.annotations;

import com.kl.tradingbot.user.core.services.UserService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  private final UserService userService;

  @Autowired
  public UniqueUsernameValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void initialize(UniqueUsername username) {
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return this.userService.getByUsernameValidation(username).isEmpty();
  }
}

