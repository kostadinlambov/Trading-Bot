package com.kl.tradingbot.user.core.validation;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;
import com.kl.tradingbot.user.infrastructure.common.model.UserUpdateBindingModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserValidationService {

  boolean isValid(User user);

  boolean isValid(UserRegisterBindingModel userRegisterBindingModel);

  boolean isValid(String firstParam, String secondParam);

  boolean isValid(UserUpdateBindingModel userUpdateBindingModel);

  boolean isValid(UserDetails userData);

  void isPasswordMatching(String password, String confirmPassword);
}
