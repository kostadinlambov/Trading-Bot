package com.kl.tradingbot.user.core.validation;

import com.kl.tradingbot.user.core.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidationServiceImpl implements UserRoleValidationService {

  @Override
  public boolean isValid(UserRole role) {
    return role != null;
  }
}
