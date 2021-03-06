package com.kl.tradingbot.user.core.common;

public final class UserConstants {

  // Error Messages
  public static final String SERVER_ERROR_MESSAGE = "Server Error";
  public static final String UNAUTHORIZED_SERVER_ERROR_MESSAGE = "Unauthorized!";
  public static final String VALIDATION_ERROR_MESSAGE = "Validation error.";
  // User Error Messages
  public static final String PASSWORDS_MISMATCH_ERROR_MESSAGE = "Passwords do not match.";
  public static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Incorrect email or password";
  //    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found.";
  public static final String USER_FAILURE_PROMOTING_MESSAGE = "Failure promoting user!";
  public static final String USER_FAILURE_DEMOTING_MESSAGE = "Failure demoting user!";
  public static final String USER_FAILURE_CHANGING_ROOT_AUTHORITY_MESSAGE = "You can't change ROOT authority!";
  public static final String USER_FAILURE_PROMOTING_ADMIN_MESSAGE = "There is no role, higher than Admin!";
  public static final String USER_FAILURE_DEMOTING_USER_MESSAGE = "There is no role, lower than USER!";
  // User Successful Response Messages
  public static final String SUCCESSFUL_REGISTER_MESSAGE = "You have been successfully registered.";
  public static final String SUCCESSFUL_LOGIN_MESSAGE = "You have successfully logged in.";
  public static final String SUCCESSFUL_LOGOUT_MESSAGE = "You have been successfully logged out.";
  public static final String SUCCESSFUL_USER_DEMOTED_MESSAGE = "User demoted successfully.";
  public static final String SUCCESSFUL_USER_PROMOTED_MESSAGE = "User promoted successfully.";
  public static final String SUCCESSFUL_USER_PROFILE_EDIT_MESSAGE = "User Profile have been successfully edited.";
  public static final String SUCCESSFUL_USER_DELETE_MESSAGE = "User have been successfully deleted.";

  private UserConstants() {
  }


}
