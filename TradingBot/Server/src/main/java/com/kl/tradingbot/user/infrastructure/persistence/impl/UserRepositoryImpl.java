package com.kl.tradingbot.user.infrastructure.persistence.impl;

import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.core.ports.repository.UserRepository;
import com.kl.tradingbot.user.infrastructure.persistence.converters.UserConverter;
import com.kl.tradingbot.user.infrastructure.persistence.entities.UserEntity;
import com.kl.tradingbot.user.infrastructure.persistence.repositories.UserRepositoryJpa;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

  private final UserRepositoryJpa userRepositoryJpa;
  private final UserConverter userConverter;

  @Autowired
  public UserRepositoryImpl(UserRepositoryJpa userRepositoryJpa,
      UserConverter userConverter) {
    this.userRepositoryJpa = userRepositoryJpa;
    this.userConverter = userConverter;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepositoryJpa.findByEmail(email).map(userConverter::mapToModel);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepositoryJpa.findByUsername(username).map(userConverter::mapToModel);
  }

  @Override
  public boolean isUserRepoEmpty() {
    return userRepositoryJpa.findAll().isEmpty();
  }

  @Override
  public User saveAndFlush(User user) {
    UserEntity userEntity = userConverter.mapToEntity(user);

    UserEntity savedUserEntity = this.userRepositoryJpa.saveAndFlush(userEntity);

    return userConverter.mapToModel(savedUserEntity);
  }

  @Override
  public Optional<User> findById(String userId) {
    return this.userRepositoryJpa.findById(userId).map(userConverter::mapToModel);
  }

}
