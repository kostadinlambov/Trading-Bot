package com.kl.tradingbot.common.exception.model;

public enum ErrorCodeEnum {
  NO_ERROR(0),
  WRONG_PARAMETER(1001),
  DB_ERROR(1002),
  TECHNICAL_ERROR(1099),
  NO_DATA_FOUND(1100),
  UNAUTHORIZED_SERVER(403),
  BAD_REQUEST_ERROR(404),
  SERVER_ERROR(500);

  private final int code;

  ErrorCodeEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
