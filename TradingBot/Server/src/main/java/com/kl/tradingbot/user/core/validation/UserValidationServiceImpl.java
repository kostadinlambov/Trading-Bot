package com.kl.tradingbot.user.core.validation;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;
import com.kl.tradingbot.user.infrastructure.common.model.UserUpdateBindingModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {

  @Override
  public boolean isValid(User user) {
    return user != null;
  }

  @Override
  public boolean isValid(UserRegisterBindingModel userRegisterBindingModel) {
    return userRegisterBindingModel != null && isValid(userRegisterBindingModel.getPassword(),
        userRegisterBindingModel.getConfirmPassword());
  }

  @Override
  public boolean isValid(String firstParam, String secondParam) {
    return firstParam.equals(secondParam);
  }

  @Override
  public boolean isValid(UserUpdateBindingModel userUpdateBindingModel) {
    return userUpdateBindingModel != null;
  }

  @Override
  public boolean isValid(UserDetails userData) {
    return userData != null;
  }

}
