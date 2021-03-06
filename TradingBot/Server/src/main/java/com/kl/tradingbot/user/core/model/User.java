package com.kl.tradingbot.user.core.model;

import com.kl.tradingbot.common.exception.ValidationException;
import com.kl.tradingbot.common.exception.ValidationException.ValidationDetail;
import com.kl.tradingbot.common.model.Builder;
import com.kl.tradingbot.common.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.core.style.ToStringCreator;

public class User {

  private final String id;
  private final String username;
  private final String email;
  private String password;
  private String firstName;
  private String lastName;
  private Set<UserRole> authorities = new HashSet<>();
  private String profilePicUrl;

  public User(String id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }

  public static UserBuilder builder(String id, String username, String email) {
    return new UserBuilder(id, username, email);
  }

  public String getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public Set<UserRole> getAuthorities() {
    return this.authorities;
  }

  public String getProfilePicUrl() {
    return this.profilePicUrl;
  }

  public void updateAuthorities(Set<UserRole> authorities) {
    this.authorities = authorities;
  }

  public void changePassword(String password) {
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
    User user = (User) o;
    return id.equals(user.id) && username.equals(user.username) && email.equals(user.email);
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
        .append("password", password)
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("authorities", authorities)
        .toString();
  }

  public static class UserBuilder implements Builder<User> {

    private final User instance;

    public UserBuilder(String id, String username, String email) {
      this.instance = new User(id, username, email);
    }

    public UserBuilder withPassword(String password) {
      instance.password = password;
      return this;
    }

    public UserBuilder withFirstName(String firstName) {
      instance.firstName = firstName;
      return this;
    }

    public UserBuilder withLastName(String lastName) {
      instance.lastName = lastName;
      return this;
    }

    public UserBuilder withProfilePicUrl(String profilePicUrl) {
      instance.profilePicUrl = profilePicUrl;
      return this;
    }

    public UserBuilder withAuthorities(Set<UserRole> authorities) {
      instance.authorities = authorities;
      return this;
    }

    @Override
    public User build() throws ValidationException {
      List<ValidationDetail> validationDetails = validate();
      Validator.throwIfDetailsExist(validationDetails);
      return instance;
    }

    private List<ValidationException.ValidationDetail> validate() {
      List<ValidationException.ValidationDetail> validationDetails = new ArrayList<>();
      Validator.validateNotEmpty(instance.username, "username")
          .ifPresent(validationDetails::add);
      Validator.validateNotEmpty(instance.email, "email")
          .ifPresent(validationDetails::add);

      return validationDetails;
    }
  }
}
