package com.kl.tradingbot.user.infrastructure.entrypoint.rest;

import static com.kl.tradingbot.user.core.common.UserConstants.SUCCESSFUL_REGISTER_MESSAGE;

import com.kl.tradingbot.common.exception.model.response.SuccessResponse;
import com.kl.tradingbot.user.core.services.UserService;
import com.kl.tradingbot.user.infrastructure.common.model.UserRegisterBindingModel;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<SuccessResponse> registerUser(
      @RequestBody @Valid UserRegisterBindingModel userRegisterBindingModel) {
    SuccessResponse successResponse = new SuccessResponse(SUCCESSFUL_REGISTER_MESSAGE,
        userService.createUser(userRegisterBindingModel));
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }
}
