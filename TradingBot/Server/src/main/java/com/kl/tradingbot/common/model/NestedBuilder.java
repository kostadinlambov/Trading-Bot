package com.kl.tradingbot.common.model;

/**
 * A special type of builder. This builder carries a link to a parent builder an thus allows to be
 * used in fluent nested builder chains
 *
 * @param <T> the type of the parent builder
 * @param <S> the data type this builder is responsible of creating
 */
public abstract class NestedBuilder<T, S> implements Builder<S> {

  private T parentBuilder;

  /**
   * Constructs a builder with no parent connection to be used in standalone scenarios
   */
  protected NestedBuilder() {
  }

  /**
   * Creates a builder with a link to its parent
   *
   * @param parentBuilder the paren builder
   */
  protected NestedBuilder(T parentBuilder) {
    this.parentBuilder = parentBuilder;
  }

  /**
   * This method finishes using this builder and allows a return to the parent builder. Useful for
   * fluent APIs with nested builders
   *
   * @return the parent builser
   * @throws IllegalStateException If this method is called, but the builder was originally created
   *                               with no parent
   */
  public T done() {
    if (parentBuilder == null) {
      throw new IllegalStateException("Return to parent builder without parent being passed");
    }
    return parentBuilder;
  }

}
