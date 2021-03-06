package com.kl.tradingbot.user.infrastructure.common.model;

import com.kl.tradingbot.user.infrastructure.common.annotations.Password;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.core.style.ToStringCreator;

public class UserLoginBindingModel implements Serializable {

  private static final String INVALID_CREDENTIALS_MESSAGE = "Incorrect credentials.";

  private String username;
  private String password;

  public UserLoginBindingModel() {
  }

  @Pattern(regexp = "^([a-zA-Z0-9]+)$")
  @Size(min = 4, max = 16, message = INVALID_CREDENTIALS_MESSAGE)
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Password(minLength = 4, maxLength = 16, containsOnlyLettersAndDigits = true, message = INVALID_CREDENTIALS_MESSAGE)
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserLoginBindingModel that = (UserLoginBindingModel) o;
    return username.equals(that.username) && password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    return new ToStringCreator(this)
        .append("username", username)
        .append("password", password)
        .toString();
  }
}
