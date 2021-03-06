package com.kl.tradingbot.user.infrastructure.persistence.converters;

import com.kl.tradingbot.common.model.GenericBuilder;
import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.infrastructure.persistence.entities.UserEntity;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  private final UserRoleConverter userRoleConverter;

  public UserConverter(UserRoleConverter userRoleConverter) {
    this.userRoleConverter = userRoleConverter;
  }

  public User mapToModel(UserEntity in) {
    if (in == null) {
      return null;
    }

    return User.builder(in.getId(), in.getUsername(), in.getEmail())
        .withFirstName(in.getFirstName())
        .withLastName(in.getLastName())
        .withPassword(in.getPassword())
        .withProfilePicUrl(in.getProfilePicUrl())
        .withAuthorities(in.getAuthorities()
            .parallelStream()
            .map(userRoleConverter::mapToModel)
            .collect(Collectors.toSet())
        ).build();
  }

  public UserEntity mapToEntity(User in) {

    return GenericBuilder.builder(UserEntity.class)
        .with(out -> out.setId(in.getId()))
        .with(out -> out.setUsername(in.getUsername()))
        .with(out -> out.setFirstName(in.getFirstName()))
        .with(out -> out.setLastName(in.getLastName()))
        .with(out -> out.setEmail(in.getEmail()))
        .with(out -> out.setPassword(in.getPassword()))
        .with(out -> out.setProfilePicUrl(in.getProfilePicUrl()))
        .with(out -> out.setAuthorities(in.getAuthorities()
            .parallelStream()
            .map(userRoleConverter::mapToEntity)
            .collect(Collectors.toSet())))
        .build();
  }
}
