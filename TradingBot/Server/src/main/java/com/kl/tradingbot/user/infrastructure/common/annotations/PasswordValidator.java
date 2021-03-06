package com.kl.tradingbot.user.infrastructure.common.annotations;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

  private int minLength;
  private int maxLength;
  private boolean containsOnlyLettersAndDigits;

  @Override
  public void initialize(Password password) {
    this.minLength = password.minLength();
    this.maxLength = password.maxLength();
    this.containsOnlyLettersAndDigits = password.containsOnlyLettersAndDigits();
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    String regex = "^[a-zA-Z0-9]+$";

    if (password.length() < this.minLength || password.length() > this.maxLength) {
      return false;
    }

    return Pattern.matches(regex, password) || !this.containsOnlyLettersAndDigits;
  }

}
