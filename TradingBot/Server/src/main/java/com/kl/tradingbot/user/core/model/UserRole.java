package com.kl.tradingbot.user.core.model;

import com.kl.tradingbot.common.exception.ValidationException;
import com.kl.tradingbot.common.exception.ValidationException.ValidationDetail;
import com.kl.tradingbot.common.model.Builder;
import com.kl.tradingbot.common.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.core.style.ToStringCreator;

public class UserRole {

  private String id;
  private String authority;

  public UserRole(String id, String authority) {
    this.id = id;
    this.authority = authority;
  }

  public static UserRoleBuilder builder(String id, String authority) {
    return new UserRoleBuilder(id, authority);
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRole userRole = (UserRole) o;
    return id.equals(userRole.id) && authority.equals(userRole.authority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authority);
  }

  @Override
  public String toString() {
    return new ToStringCreator(this)
        .append("id", id)
        .append("authority", authority)
        .toString();
  }

  public static class UserRoleBuilder implements Builder<UserRole> {

    private final UserRole instance;

    public UserRoleBuilder(String id, String authority) {
      this.instance = new UserRole(id, authority);
    }

    @Override
    public UserRole build() throws ValidationException {
      List<ValidationDetail> validationDetails = validate();

      Validator.throwIfDetailsExist(validationDetails);

      return instance;
    }

    private List<ValidationException.ValidationDetail> validate() {
      List<ValidationException.ValidationDetail> validationDetails = new ArrayList<>();

      Validator.validateNotEmpty(instance.id, "id").
          ifPresent(validationDetails::add);
      Validator.validateNotEmpty(instance.authority, "authority")
          .ifPresent(validationDetails::add);

      return validationDetails;
    }
  }
}
