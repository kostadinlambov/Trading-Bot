package com.kl.tradingbot.user.infrastructure.persistence.impl;

import com.kl.tradingbot.user.core.model.UserRole;
import com.kl.tradingbot.user.core.ports.repository.UserRoleRepository;
import com.kl.tradingbot.user.infrastructure.persistence.converters.UserRoleConverter;
import com.kl.tradingbot.user.infrastructure.persistence.repositories.UserRoleRepositoryJpa;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserRoleRepositoryImpl implements UserRoleRepository {

  private final UserRoleRepositoryJpa roleRepositoryJpa;
  private final UserRoleConverter userRoleConverter;

  public UserRoleRepositoryImpl(UserRoleRepositoryJpa roleRepositoryJpa,
      UserRoleConverter userRoleConverter) {
    this.roleRepositoryJpa = roleRepositoryJpa;
    this.userRoleConverter = userRoleConverter;
  }

  @Override
  public Optional<UserRole> getByAuthority(String authority) {
    return this.roleRepositoryJpa
        .getByAuthority(authority)
        .map(userRoleConverter::mapToModel);
  }
}
