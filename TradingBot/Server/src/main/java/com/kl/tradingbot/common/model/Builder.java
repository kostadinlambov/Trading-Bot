package com.kl.tradingbot.common.model;

import com.kl.tradingbot.common.exception.ValidationException;

/**
 * Generic class representing a builder that is capable of constructing an object of the given type
 *
 * @param <T> the data type this builder is responsible of creating
 */
public interface Builder<T> {

  /**
   * Validates the input data and builds the data object
   *
   * @return the completed data object
   * @throws ValidationException Contains all validation errors that were detected during
   *                             construction
   */
  T build() throws ValidationException;
}
