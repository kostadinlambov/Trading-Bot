package com.kl.tradingbot.user.core.ports.repository;

import com.kl.tradingbot.user.core.model.UserRole;
import java.util.Optional;

public interface UserRoleRepository {

  Optional<UserRole> getByAuthority(String authority);
}
