package com.company.domain;

/**
 * Created by user on 10.04.2017.
 */
public class Game {
    private Board board;

    public static Game newFromLevel(Board board) {
        return new Game(board);
    }

    public static Game newInstance(int size) {
        return new Game(size);
    }

    public Board play() {
        return board;
    }

    private Game(int size) {
        board = Board.newInstance(size);
    }

    private Game(Board board) {
        this.board = board;
    }
}
