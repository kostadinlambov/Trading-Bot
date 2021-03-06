package com.kl.tradingbot.user.core.validation;

import static com.kl.tradingbot.user.core.common.UserConstants.PASSWORDS_MISMATCH_ERROR_MESSAGE;

import com.kl.tradingbot.common.exception.ErrorCodeEnum;
import com.kl.tradingbot.common.exception.ErrorMessageEnum;
import com.kl.tradingbot.common.exception.TradingBotException;
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
    if (userRegisterBindingModel == null) {
      throw new TradingBotException(ErrorMessageEnum.VALIDATION_ERROR.getMessage(),
          ErrorCodeEnum.WRONG_PARAMETER);
    }

    return true;
  }

  @Override
  public boolean isValid(String firstParam, String secondParam) {
    if (!firstParam.equals(secondParam)) {
      throw new TradingBotException(ErrorMessageEnum.VALIDATION_ERROR.getMessage(),
          ErrorCodeEnum.WRONG_PARAMETER);
    }

    return true;
  }

  @Override
  public boolean isValid(UserUpdateBindingModel userUpdateBindingModel) {
    return userUpdateBindingModel != null;
  }

  @Override
  public boolean isValid(UserDetails userData) {
    return userData != null;
  }

  @Override
  public void isPasswordMatching(String password, String confirmPassword) {
    if (!password.equals(confirmPassword)) {
      throw new TradingBotException(PASSWORDS_MISMATCH_ERROR_MESSAGE,
          ErrorCodeEnum.WRONG_PARAMETER);
    }
  }
}
