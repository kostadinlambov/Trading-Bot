package com.kl.tradingbot.user.core.services;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.common.model.UserCreateViewModel;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;
import java.util.Optional;

public interface UserService {

  UserCreateViewModel createUser(UserRegisterBindingModel userRegisterBindingModel);

  Optional<User> getByUsernameValidation(String username);

  Optional<User> getByEmailValidation(String email);

}
