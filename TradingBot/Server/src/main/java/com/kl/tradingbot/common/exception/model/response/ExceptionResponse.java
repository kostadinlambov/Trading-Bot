package com.kl.tradingbot.common.exception.model.response;

public class ExceptionResponse extends BaseResponse {

  private String details;

  public ExceptionResponse(String message, String details) {
    super(message, false);
    this.details = details;
  }

  public String getDetails() {
    return this.details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}