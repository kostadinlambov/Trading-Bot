package com.kl.tradingbot.user.infrastructure.persistence.repositories;

import com.kl.tradingbot.user.infrastructure.persistence.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByUsername(String username);

  UserEntity findByEmailAndPassword(String email, String password);

  UserEntity findAllByFirstName(String firstName);


  @Query(value = "" +
      "SELECT u FROM UserEntity AS u " +
      "WHERE u.id <> :id AND " +
      "(LOWER(u.firstName) LIKE CONCAT('%', :searchSymbols, '%') OR " +
      "LOWER(u.lastName) LIKE CONCAT('%', :searchSymbols, '%'))  ")
  List<UserEntity> findAllUsersLike(@Param(value = "id") String id,
      @Param(value = "searchSymbols") String searchSymbols);

}
