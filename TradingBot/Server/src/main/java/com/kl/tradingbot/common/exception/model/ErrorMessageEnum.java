package com.kl.tradingbot.common.exception.model;

public enum ErrorMessageEnum {
  SERVER_ERROR("Server Error"),
  UNAUTHORIZED_SERVER_ERROR("Unauthorized"),
  VALIDATION_ERROR("Validation error"),
  BAD_REQUEST_ERROR("Bad Request"),
  DB_ERROR("Persistence error");

  private final String message;

  ErrorMessageEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

}



