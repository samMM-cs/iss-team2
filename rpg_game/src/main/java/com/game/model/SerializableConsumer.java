package com.game.model;

import java.io.Serializable;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.game.controller.ViewManager;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY)
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {

    public class DoNothing<T> implements SerializableConsumer<T> {
        @Override
        public void accept(T value) {
            return;
        }
    }

    public class ChangeMap<T extends GameState> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            T.getInstance().nextMap();
            ViewManager.getInstance().updateMaps();
        }
    }

    public class EndOfStory<T extends GameState> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            ViewManager.getInstance().exit();
        }
    }
}