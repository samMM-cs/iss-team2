package com.game.model;

import java.io.Serializable;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY)
public interface SerializablePredicate<T> extends Predicate<T>, Serializable {

  public class ConstantTrue<T> implements SerializablePredicate<T> {
    @Override
    public boolean test(T t) {
      return true;
    }
  }

  public class CanChangeMap<T extends GameState> implements SerializablePredicate<T> {
    @Override
    public boolean test(T t) {
      return T.getInstance().getEnemies().isEmpty()
          && !T.getInstance().getMap().getEnemies().isEmpty()
          && T.getInstance().getMapInd() != T.getInstance().getMaps().size() - 1;
    }

  }
}
