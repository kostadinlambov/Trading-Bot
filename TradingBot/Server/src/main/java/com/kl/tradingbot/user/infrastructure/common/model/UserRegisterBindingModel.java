package com.kl.tradingbot.user.infrastructure.common.model;

import com.kl.tradingbot.user.infrastructure.common.annotations.Password;
import com.kl.tradingbot.user.infrastructure.common.annotations.PasswordMatching;
import com.kl.tradingbot.user.infrastructure.common.annotations.UniqueEmail;
import com.kl.tradingbot.user.infrastructure.common.annotations.UniqueUsername;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.core.style.ToStringCreator;

@PasswordMatching
public class UserRegisterBindingModel implements Serializable {

  public static final String USER_INVALID_EMAIL_MESSAGE = "Invalid e-mail address.";
  public static final String USER_INVALID_USERNAME_MESSAGE = "Username should be at least 4 and maximum 16 characters long.";
  public static final String USER_INVALID_FIRST_NAME_MESSAGE = "First Name must start with a capital letter and must contain only letters.";
  public static final String USER_INVALID_LAST_NAME_MESSAGE = "Last Name must start with a capital letter and must contain only letters.";
  public static final String USER_INVALID_PASSWORD_MESSAGE = "Invalid Password format.";

  private String username;
  private String email;
  private String password;
  private String confirmPassword;
  private String firstName;
  private String lastName;
  private String profilePicUrl;

  public UserRegisterBindingModel() {
  }

  @Pattern(regexp = "^([a-zA-Z0-9]+)$")
  @Size(min = 4, max = 16, message = USER_INVALID_USERNAME_MESSAGE)
  @UniqueUsername
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = USER_INVALID_EMAIL_MESSAGE)
  @UniqueEmail
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Password(minLength = 4, maxLength = 16, containsOnlyLettersAndDigits = true, message = USER_INVALID_PASSWORD_MESSAGE)
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Pattern(regexp = "^[A-Z]([a-zA-Z]+)?$", message = USER_INVALID_FIRST_NAME_MESSAGE)
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Pattern(regexp = "^[A-Z]([a-zA-Z]+)?$", message = USER_INVALID_LAST_NAME_MESSAGE)
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getProfilePicUrl() {
    return this.profilePicUrl;
  }

  public void setProfilePicUrl(String profilePicUrl) {
    this.profilePicUrl = profilePicUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRegisterBindingModel that = (UserRegisterBindingModel) o;
    return username.equals(that.username) && email.equals(that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, email);
  }

  @Override
  public String toString() {
    return new ToStringCreator(this)
        .append("username", username)
        .append("email", email)
        .append("password", password)
        .append("confirmPassword", confirmPassword)
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("profilePicUrl", profilePicUrl)
        .toString();
  }

}
