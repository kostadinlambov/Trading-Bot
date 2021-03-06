package com.kl.tradingbot.user.infrastructure.common.model;

import static com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel.USER_INVALID_EMAIL_MESSAGE;
import static com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel.USER_INVALID_FIRST_NAME_MESSAGE;
import static com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel.USER_INVALID_LAST_NAME_MESSAGE;
import static com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel.USER_INVALID_USERNAME_MESSAGE;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.style.ToStringCreator;

public class UserUpdateBindingModel implements Serializable {

  private static final String ID_REQUIRED_MESSAGE = "Id is required.";

  private String id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String profilePicUrl;

  public UserUpdateBindingModel() {
  }

  @NotNull(message = ID_REQUIRED_MESSAGE)
  @Length(min = 1, message = ID_REQUIRED_MESSAGE)
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Pattern(regexp = "^([a-zA-Z0-9]+)$")
  @Size(min = 4, max = 16, message = USER_INVALID_USERNAME_MESSAGE)
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = USER_INVALID_EMAIL_MESSAGE)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
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
    UserUpdateBindingModel that = (UserUpdateBindingModel) o;
    return id.equals(that.id) && username.equals(that.username) && email.equals(that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email);
  }

  @Override
  public String toString() {
    return new ToStringCreator(this)
        .append("id", id)
        .append("username", username)
        .append("email", email)
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("profilePicUrl", profilePicUrl)
        .toString();
  }

}
