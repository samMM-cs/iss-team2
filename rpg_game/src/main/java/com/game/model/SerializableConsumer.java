package com.game.model;

import java.io.Serializable;
import java.util.function.Consumer;

public interface SerializableConsumer<T> extends Consumer<T>, Serializable {

    public class DoNothing<T> implements SerializableConsumer<T> {
        @Override
        public void accept(T value) {
            return;
        }
    }
}