package com.kl.tradingbot.user.infrastructure.persistence.converters;

import com.kl.tradingbot.common.model.GenericBuilder;
import com.kl.tradingbot.user.core.model.UserRole;
import com.kl.tradingbot.user.infrastructure.persistence.entities.UserRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRoleConverter {

  public UserRole mapToModel(UserRoleEntity in) {
    if (in == null) {
      return null;
    }

    return UserRole.builder(in.getId(), in.getAuthority()).build();
  }

  public UserRoleEntity mapToEntity(UserRole in) {
    if (in == null) {
      return null;
    }

    return GenericBuilder.builder(UserRoleEntity.class)
        .with(out -> out.setId(in.getId()))
        .with(out -> out.setAuthority(in.getAuthority()))
        .build();
  }
}
