package com.kl.tradingbot.user.infrastructure.persistence.entitiesSeed;

import com.kl.tradingbot.user.infrastructure.persistence.entities.UserRoleEntity;
import com.kl.tradingbot.user.infrastructure.persistence.repositories.UserRoleRepositoryJpa;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntitiesSeedExecutor {

  private final UserRoleRepositoryJpa roleRepository;

  @Autowired
  public EntitiesSeedExecutor(UserRoleRepositoryJpa roleRepository) {
    this.roleRepository = roleRepository;
  }

  @PostConstruct
  public void insertEntities() {

    // Role initialisation
    if (roleRepository.count() == 0L) {
      UserRoleEntity role1 = new UserRoleEntity();
      UserRoleEntity role2 = new UserRoleEntity();
      UserRoleEntity role3 = new UserRoleEntity();
      role1.setAuthority("ADMIN");
      role2.setAuthority("USER");
      role3.setAuthority("ROOT");
      roleRepository.save(role1);
      roleRepository.save(role2);
      roleRepository.save(role3);
    }
  }
}
