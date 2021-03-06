package com.kl.tradingbot.user.infrastructure.persistence.converters;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  public User mapToModel(UserEntity in) {
    if (in == null) {
      return null;
    }

    return null;
  }

  public UserEntity mapToEntity(User user) {
    return null;
  }
}
