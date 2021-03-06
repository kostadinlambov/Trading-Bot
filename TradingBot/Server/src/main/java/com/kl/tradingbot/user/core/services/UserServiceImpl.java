package com.kl.tradingbot.user.core.services;


import com.kl.tradingbot.common.exception.ErrorCodeEnum;
import com.kl.tradingbot.common.exception.ErrorMessageEnum;
import com.kl.tradingbot.common.exception.TradingBotException;
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
//        this.loggerService.createLog("POST", userServiceModel.getUsername(), "users", "register");

    if (!this.userValidation.isValid(userRegisterBindingModel)) {
      throw new TradingBotException(INVALID_CREDENTIALS, ErrorCodeEnum.SERVER_ERROR);
    }

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
    return this.roleRepository.getByAuthority(authority)
        .filter(userRoleValidation::isValid)
        .orElseThrow(() -> new TradingBotException(USER_ROLE_NOT_FOUND_ERROR_MESSAGE,
            ErrorCodeEnum.DB_ERROR));
  }

  //    @Override
//    public boolean updateUser(UserServiceModel userServiceModel, String loggedInUserId) throws Exception {
//        if (!userValidation.isValid(userServiceModel)) {
//            throw new Exception(SERVER_ERROR_MESSAGE);
//        }
//
//        User userToEdit = this.userRepository.findById(userServiceModel.getId()).orElse(null);
//        User loggedInUser = this.userRepository.findById(loggedInUserId).orElse(null);
//
//        if (!userValidation.isValid(userToEdit) || !userValidation.isValid(loggedInUser)) {
//            throw new Exception(SERVER_ERROR_MESSAGE);
//        }
//
//        if (!userServiceModel.getId().equals(loggedInUserId)) {
//            String userAuthority = this.getUserAuthority(loggedInUserId);
//            if (!("ROOT").equals(userAuthority) && !("ADMIN").equals(userAuthority)) {
//                throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
//            }
//        }
//
//        User userEntity = this.modelMapper.map(userServiceModel, User.class);
//        userEntity.setPassword(userToEdit.getPassword());
//        userEntity.setAuthorities(userToEdit.getAuthorities());
//
//        return this.userRepository.save(userEntity) != null;
//    }
//
//    @Override
//    public UserServiceModel updateUserOnlineStatus(String userName, boolean changeToOnline) throws Exception {
//        User user = this.userRepository.findByUsername(userName)
//                .filter(userValidation::isValid)
//                .orElseThrow(() -> new CustomException(SERVER_ERROR_MESSAGE));
//
//        if(changeToOnline){
//            user.setOnline(true);
//        }else {
//            user.setOnline(false);
//        }
//
//        User updatedUser = this.userRepository.save(user);
//
//        if (updatedUser != null) {
//            return this.modelMapper.map(updatedUser, UserServiceModel.class);
//        }
//
//        throw new CustomException(SERVER_ERROR_MESSAGE);
//    }
//
//
//    @Override
//    public List<UserServiceModel> getAllUsers(String userId) throws Exception {
//        User userById = this.userRepository.findById(userId).orElse(null);
//
//        if (!userValidation.isValid(userById)) {
//            throw new Exception(SERVER_ERROR_MESSAGE);
//        }
//
//        List<UserRole> userRoles = this.getUserRoles(userById);
//
//        if (userRoles.size() > 0) {
//            return this.userRepository
//                    .findAll()
//                    .stream()
//                    .map(x -> this.modelMapper.map(x, UserServiceModel.class))
//                    .collect(Collectors.toList());
//        }
//
//        throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
//    }
//
//    @Override
//    public UserDetailsViewModel getById(String id) throws Exception {
//        User user = this.userRepository.findById(id)
//                .filter(userValidation::isValid)
//                .orElseThrow(Exception::new);
//
//        return this.modelMapper.map(user, UserDetailsViewModel.class);
//    }
//
//    @Override
//    public UserEditViewModel editById(String id) throws Exception {
//        User user = this.userRepository.findById(id)
//                .filter(userValidation::isValid)
//                .orElseThrow(Exception::new);
//
//        return this.modelMapper.map(user, UserEditViewModel.class);
//    }
//

//    @Override
//    public boolean deleteUserById(String id) throws Exception {
//       this.userRepository.findById(id)
//                .filter(userValidation::isValid)
//                .orElseThrow(Exception::new);
//
//        try {
//            this.userRepository.deleteById(id);
//            return true;
//        } catch (Exception e) {
//            throw new Exception(SERVER_ERROR_MESSAGE);
//        }
//    }

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
