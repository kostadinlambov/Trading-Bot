package com.kl.tradingbot.user.infrastructure.persistence.converters;

import com.kl.tradingbot.user.core.model.UserRole;
import com.kl.tradingbot.user.infrastructure.persistence.entities.UserRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRoleConverter {

  public UserRole mapToModel(UserRoleEntity in) {
    if (in == null) {
      return null;
    }

    return null;
  }
}
