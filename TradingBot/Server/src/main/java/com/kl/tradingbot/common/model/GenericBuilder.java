package com.kl.tradingbot.common.model;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class GenericBuilder<T> {

  private T instance;

  public GenericBuilder(Class<T> clazz) {
    try {
      instance = clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public GenericBuilder<T> with(Consumer<T> setter) {
    setter.accept(instance);
    return this;
  }

  public T build() {
    return instance;
  }

  public static <T> GenericBuilder<T> builder(Class<T> clazz) {
    return new GenericBuilder<>(clazz);
  }
}
