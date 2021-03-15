package com.kl.tradingbot.user.core.services;


import com.kl.tradingbot.common.exception.TradingBotException;
import com.kl.tradingbot.common.exception.model.ErrorCodeEnum;
import com.kl.tradingbot.common.exception.model.ErrorMessageEnum;
import com.kl.tradingbot.user.core.converters.UserMapper;
import com.kl.tradingbot.user.core.model.User;
import com.kl.tradingbot.user.core.model.UserRole;
import com.kl.tradingbot.user.core.ports.repository.UserRepository;
import com.kl.tradingbot.user.core.ports.repository.UserRoleRepository;
import com.kl.tradingbot.user.core.validation.UserRoleValidationService;
import com.kl.tradingbot.user.core.validation.UserValidationService;
import com.kl.tradingbot.user.infrastructure.common.model.UserCreateViewModel;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  public static final String INVALID_CREDENTIALS = "Incorrect credentials";
  public static final String USER_ROLE_NOT_FOUND_ERROR_MESSAGE = "UserRole not found";

  private final UserRepository userRepository;
  private final UserRoleRepository roleRepository;
  private final UserValidationService userValidation;
  private final UserRoleValidationService userRoleValidation;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      UserRoleRepository roleRepository,
      UserValidationService userValidation,
      UserRoleValidationService userRoleValidation,

      ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userValidation = userValidation;
    this.userRoleValidation = userRoleValidation;
    this.modelMapper = modelMapper;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public UserCreateViewModel createUser(UserRegisterBindingModel userRegisterBindingModel) {
    userValidation.isPasswordMatching(userRegisterBindingModel.getPassword(),
        userRegisterBindingModel.getConfirmPassword());
    userValidation.isValid(userRegisterBindingModel);

    User user = UserMapper.mapToUser(userRegisterBindingModel);

    user.changePassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
    user.updateAuthorities(setUserRoles());

    try {
      User savedUser = this.userRepository.saveAndFlush(user);

      if (savedUser != null) {
        return this.modelMapper.map(savedUser, UserCreateViewModel.class);
      }
    } catch (PersistenceException pe) {
      throw new TradingBotException(pe.getMessage(), ErrorCodeEnum.DB_ERROR);
    }

    throw new TradingBotException(ErrorMessageEnum.SERVER_ERROR.getMessage(),
        ErrorCodeEnum.SERVER_ERROR);
  }

  @Override
  public Optional<User> getByEmailValidation(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> getByUsernameValidation(String username) {
    return this.userRepository.findByUsername(username);
  }

  private Set<UserRole> setUserRoles() {
    UserRole userRole = getUserRoleByAuthority("USER");
    UserRole rootRole = getUserRoleByAuthority("ROOT");

    Set<UserRole> roles = new HashSet<>();
    if (this.userRepository.isUserRepoEmpty()) {
      roles.add(rootRole);
    } else {
      roles.add(userRole);
    }

    return roles;
  }

  private UserRole getUserRoleByAuthority(String authority) {
    Optional<UserRole> byAuthority = this.roleRepository.getByAuthority(authority);
    return byAuthority
        .filter(userRoleValidation::isValid)
        .orElseThrow(() -> new TradingBotException(USER_ROLE_NOT_FOUND_ERROR_MESSAGE,
            ErrorCodeEnum.DB_ERROR));
  }

  private List<UserRole> getUserRoles(User userById) {
    return userById
        .getAuthorities()
        .stream().filter(userRole ->
            userRole.getAuthority().equals("ROOT")
                || userRole.getAuthority().equals("ADMIN"))
        .collect(Collectors.toList());
  }

  private Set<UserRole> getAuthorities(String authority) {
    Set<UserRole> userAuthorities = new HashSet<>();

    roleRepository.getByAuthority(authority)
        .ifPresent(userAuthorities::add);

    return userAuthorities;
  }

  private String getUserAuthority(String userId) {
    return this
        .userRepository
        .findById(userId)
        .get()
        .getAuthorities()
        .stream()
        .findFirst()
        .get()
        .getAuthority();
  }
}
