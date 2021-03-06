package com.kl.tradingbot.user.infrastructure.persistence.repositories;

import com.kl.tradingbot.user.infrastructure.persistence.entities.UserRoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepositoryJpa extends JpaRepository<UserRoleEntity, String> {

  UserRoleEntity findByAuthority(String authority);

  Optional<UserRoleEntity> getByAuthority(String authority);
}
