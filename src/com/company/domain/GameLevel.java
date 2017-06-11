package com.company.domain;

/**
 * Created by user on 27.05.2017.
 */
public class GameLevel {
    private BoardField[][] field;
    private Coordinates player;
    private Coordinates enemy;
    private Coordinates target;

    public GameLevel(BoardField[][] field, Coordinates player, Coordinates enemy, Coordinates target) {
        this.field = field;
        this.player = player;
        this.enemy = enemy;
        this.target = target;
    }

    public Coordinates getPlayer() {
        return player;
    }

    public Coordinates getEnemy() {
        return enemy;
    }

    public Coordinates getTarget() {
        return target;
    }

    public BoardField[][] getField() {
        return field;
    }
}
