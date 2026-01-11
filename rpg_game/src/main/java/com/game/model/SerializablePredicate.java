package com.game.model;

import java.io.Serializable;
import java.util.function.Predicate;

public interface SerializablePredicate<T> extends Predicate<T>, Serializable {

  public class ConstantTrue<T> implements SerializablePredicate<T> {
    @Override
    public boolean test(T t) {
      return true;
    }
  }
}
