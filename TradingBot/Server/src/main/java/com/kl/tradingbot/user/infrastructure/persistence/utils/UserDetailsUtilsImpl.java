package com.kl.tradingbot.user.infrastructure.persistence.utils;

import com.kl.tradingbot.user.infrastructure.persistence.repositories.UserRepositoryJpa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsUtilsImpl implements UserDetailsUtils {

  public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found";
  private final UserRepositoryJpa userRepositoryJpa;

  public UserDetailsUtilsImpl(UserRepositoryJpa userRepositoryJpa) {
    this.userRepositoryJpa = userRepositoryJpa;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return this.userRepositoryJpa.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE));
  }
}
