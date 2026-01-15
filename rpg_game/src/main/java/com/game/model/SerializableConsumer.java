package com.game.model;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.game.controller.ViewManager;
import com.game.model.battle.Battle;
import com.game.model.character.Enemy;
import com.game.model.character.Job;
import com.game.model.story.Flag;

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

    public class ThinkInMap1<T extends GameState> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            T.getInstance().setFlag(Flag.THINKED_IN_MAP1);
        }
    }

    public class StudyEnemyBehavior<T extends GameState> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            T.getInstance().setFlag(Flag.STUDY_ENEMY_BEHAVIOR);
        }
    }

    public class DeepThinking<T extends GameState> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            T.getInstance().setFlag(Flag.DEEP_THINKING);
        }
    }

    public class BossFight<T> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            Battle battle= new Battle(new Enemy(List.of(Job.BOSS, Job.BOSS2, Job.BOSS3).get(new Random().nextInt(3)), new Position(22, 27), 1));
            ViewManager.getInstance().showBattleView(battle);
        }
    }

    public class EndOfStory<T extends GameState> implements SerializableConsumer<T> {
        @Override
        public void accept(T t) {
            ViewManager.getInstance().exit();
        }
    }
}