package com.kl.tradingbot.common.model;

import com.kl.tradingbot.common.exception.ValidationException;
import com.kl.tradingbot.common.validation.Validator;
import java.util.List;
import java.util.function.Supplier;

/**
 * Special type of builder that is used to create "wrapper objects" wrapper objects contain only a
 * single value and are used for the purpose of ensuring proper validation of this value
 *
 * @param <T> the type of the wrapper object that will be constructed
 */
public abstract class SingleValueBuilder<T> implements Builder<T> {

  private final T instance;

  /**
   * Creates a builder that carries a wrapper object constructed according to the passed supplier
   *
   * @param instanceConstructor the constructor of the wrapper object
   */
  protected SingleValueBuilder(Supplier<T> instanceConstructor) {
    instance = instanceConstructor.get();
  }

  protected T getInstance() {
    return instance;
  }

  @Override
  public T build() throws ValidationException {
    List<ValidationException.ValidationDetail> validationDetails = validate();
    Validator.throwIfDetailsExist(validationDetails);
    return instance;
  }

  /**
   * Validation logic for the wrapper object.
   *
   * @return the result list containing all found validation errors. If validation is successful,
   * this list will be empty
   */
  protected abstract List<ValidationException.ValidationDetail> validate();

}
