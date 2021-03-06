package com.kl.tradingbot.user.core.validation;

import com.kl.tradingbot.user.core.model.UserRole;

public interface UserRoleValidationService {

  boolean isValid(UserRole role);
}
