package com.kl.tradingbot.user.core.converters;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;

public final class UserMapper {

  private UserMapper() {
  }


  public static User mapToUser(UserRegisterBindingModel userLoginBindingModel) {
    if (userLoginBindingModel == null) {
      return null;
    }

    return null;
  }
}
