package com.kl.tradingbot.common.exception.model.response;

public class SuccessResponse extends BaseResponse {

  private Object payload;

  public SuccessResponse(String message, Object payload) {
    super(message, true);
    this.payload = payload;
  }

  public Object getPayload() {
    return this.payload;
  }

  public void setPayload(Object payload) {
    this.payload = payload;
  }
}