package com.kl.tradingbot.common.validation;

import com.kl.tradingbot.common.exception.ValidationException;
import com.kl.tradingbot.common.exception.ValidationException.ValidationDetail;
import com.kl.tradingbot.common.exception.model.ErrorCodeEnum;
import com.kl.tradingbot.common.model.Builder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * Provides helper method for validating business data
 */
public final class Validator {

  private Validator() {
  }

  /**
   * Validate the input String, if it is not null and not empty (trims leading and tailing blanks).
   * If the input is invalid, a {@link ValidationDetail} containing the error information will be
   * returned
   *
   * @param value    input String to validate
   * @param property the name of the property, as it should be named in the error message
   * @return Validation detail when validation fails, empty optional otherwise
   */
  public static Optional<ValidationDetail> validateNotEmpty(String value,
      String property) {
    if (value == null || value.isBlank()) {
      return Optional.of(new ValidationDetail(property, "Is mandatory"));
    }
    return Optional.empty();
  }

  /**
   * Validate if the input object is not null. If it is null, a {@link ValidationDetail} containing
   * the error information will be returned
   *
   * @param value    the input Object to validate
   * @param property the name of the property, as it should be named in the error message
   * @return Validation detail when validation fails, empty optional otherwise
   */
  public static Optional<ValidationDetail> validateNotNull(Object value, String property) {
    if (Objects.isNull(value)) {
      return Optional.of(new ValidationDetail(property, "Is mandatory"));
    }
    return Optional.empty();
  }

  /**
   * Validate that the input string does not exceed a maximum length. If the input is invalid, a
   * {@link ValidationDetail} containing the error information will be returned
   *
   * @param value     input String to validate
   * @param property  the name of the property, as it should be named in the error message
   * @param maxLength the maximum length the string should not exceed
   * @return Validation detail when validation fails, empty optional otherwise
   */
  public static Optional<ValidationDetail> validateLength(String value,
      String property, int maxLength) {
    if (value != null && value.length() > maxLength) {
      String message = String.format(
          "%s is too long. Must be less or equal than %d characters, but got: %d", property,
          maxLength, value.length());
      return Optional.of(new ValidationDetail(property, message));
    }
    return Optional.empty();
  }

  /**
   * Checks if the passed collection of input details is not empty. If it contains elements, a
   * {@link ValidationException} will be thrown. This exception contains the passed collection
   *
   * @param details input collection to check
   * @throws ValidationException thrown if the input collection contains elements
   */
  public static void throwIfDetailsExist(Collection<ValidationDetail> details)
      throws ValidationException {
    if (!details.isEmpty()) {
      ValidationException ve = new ValidationException(ErrorCodeEnum.WRONG_PARAMETER);
      ve.addDetails(details);
      throw ve;
    }
  }

  /**
   * Wrapper to handle the following task: Get value from a builder and assign it to a property. If
   * a {@link ValidationException} is thrown, its content (the validation details) is passed to a
   * collector for those details. If the passed builder is null, a single validation detail is
   * returned
   *
   * @param setter         the value from the builder will be set here
   * @param builder        the builder to construct the value
   * @param property       the name of the property to be put in the error message, when the builder
   *                       does not exist
   * @param detailConsumer the collector for exception details, if one occurs
   * @param <T>            Type constructed by the builder
   */
  public static <T> void assignOrHandleValidationDetails(
      Consumer<T> setter,
      Builder<T> builder,
      String property,
      Consumer<List<ValidationDetail>> detailConsumer) {
    if (builder == null) {
      detailConsumer
          .accept(Collections.singletonList(new ValidationDetail(property, "Is mandatory")));
    } else {
      try {
        setter.accept(builder.build());
      } catch (ValidationException ve) {
        detailConsumer.accept(ve.getDetails());
      }
    }
  }

  /**
   * Wrapper to handle the following task: Get value from a builder and assign it to a property. If
   * a {@link ValidationException} is thrown, its content (the validation details) is passed to a
   * collector for those details. Does not fail, if the builder is null
   *
   * @param setter         the value from the builder will be set here
   * @param builder        the builder to construct the value
   * @param detailConsumer the collector for exception details, if one occurs
   * @param <T>            Type constructed by the builder
   */
  public static <T> void assignOrHandleValidationDetails(
      Consumer<T> setter,
      Builder<T> builder,
      Consumer<List<ValidationDetail>> detailConsumer) {
    if (builder != null) {
      try {
        setter.accept(builder.build());
      } catch (ValidationException ve) {
        detailConsumer.accept(ve.getDetails());
      }
    }
  }
}
