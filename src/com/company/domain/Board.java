package com.company.domain;

import java.util.Random;

/**
 * Created by user on 10.04.2017.
 */
public final class Board {
    private Obstacle[][] field;
    private final double OBSTACLE_RATIO = (double) 3 / 10;
    private Coordinates player;
    private Coordinates target;

    public static final Random rn = new Random();

    public static Board newInstance(int size) {
        return new Board(size);
    }

    public Board(int size) {
        this.field = generate(size);
        this.player = generatePlayer();
        this.target = generateTarget();
    }

    private Obstacle[][] generate(int size) {
        Obstacle[][] arr = new Obstacle[size][size];

        int obstacleToSet = (int) OBSTACLE_RATIO * size * size;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (obstacleToSet > 0) {
                    int rand = Board.rn.nextInt();
                    if (rand % 2 == 0) {
                        arr[i][j] = Obstacle.YES;
                        obstacleToSet--;
                    } else arr[i][j] = Obstacle.NO;
                }
                arr[i][j] = Obstacle.NO;
            }
        }

        return arr;
    }

    private Coordinates generatePlayer() {
        return new Coordinates(1, 1);
    }

    private Coordinates generateTarget() {
        return new Coordinates(0, 0);
    }

}
