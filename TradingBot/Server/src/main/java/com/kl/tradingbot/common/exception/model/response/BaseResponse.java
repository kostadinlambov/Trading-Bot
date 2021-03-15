package com.kl.tradingbot.common.exception.model.response;

import java.time.LocalDateTime;

public abstract class BaseResponse {

  private LocalDateTime timestamp;
  private String message;
  private boolean success;

  public BaseResponse(String message, boolean success) {
    this.timestamp = LocalDateTime.now();
    this.message = message;
    this.success = success;
  }

  public LocalDateTime getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
