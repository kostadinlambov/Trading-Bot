package com.kl.tradingbot.user.core.ports.repository;

import com.kl.tradingbot.user.core.model.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean isUserRepoEmpty();

  User saveAndFlush(User user);

  Optional<User> findById(String userId);
}
