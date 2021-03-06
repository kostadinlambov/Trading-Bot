package com.kl.tradingbot.common.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ValidationException extends TradingBotException {

  private final List<ValidationDetail> details = new ArrayList<>();

  public ValidationException(ErrorCodeEnum errorCode) {
    super("There are validation errors", errorCode);
  }

  public List<ValidationDetail> getDetails() {
    return this.details;
  }

  public void addDetail(ValidationDetail detail) {
    this.details.add(detail);
  }

  public void addDetails(Collection<ValidationDetail> details) {
    this.details.addAll(details);
  }

  public static class ValidationDetail {

    private final String field;
    private final String error;

    public ValidationDetail(String field, String error) {
      this.field = field;
      this.error = error;
    }

    public String getMessage() {
      return String.format("Field %s invalid because: %s", field, error);
    }

    public String getField() {
      return this.field;
    }

    public String getError() {
      return this.error;
    }
  }
}
