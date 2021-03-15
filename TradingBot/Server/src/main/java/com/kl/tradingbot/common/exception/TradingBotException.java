package com.kl.tradingbot.common.exception;

import com.kl.tradingbot.common.exception.model.ErrorCodeEnum;

public class TradingBotException extends RuntimeException {

  private final ErrorCodeEnum errorCode;

  public TradingBotException(String message, ErrorCodeEnum errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public ErrorCodeEnum getErrorCode() {
    return this.errorCode;
  }
}
