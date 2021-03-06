package com.kl.tradingbot.user.core.converters;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;

public final class UserMapper {

  private UserMapper() {
  }

  public static User mapToUser(UserRegisterBindingModel in) {
    if (in == null) {
      return null;
    }

    return User.builder(null, in.getUsername(), in.getEmail())
        .withFirstName(in.getFirstName())
        .withLastName(in.getLastName())
        .withPassword(in.getPassword())
        .withProfilePicUrl(in.getProfilePicUrl())
        .build();

  }
}
