package com.company.domain;

import java.io.IOException;

/**
 * Created by user on 27.05.2017.
 */
public class Game {
    private GameLevel[] levels = new GameLevel[0];
    private GameLevel currentLevel = null;

    public Game(LevelLoaderI loader) throws IOException {
        this.levels = loader.getAllLevels();
    }

    public GameLevel[] getLevels() {
        int a = 1 + 1;
        return levels;
    }

    public GameLevel selectLevel(int levelId) throws Exception {
        if (levels == null) throw new Exception("Levels are null");
        if (levelId < 0 || levelId >= levels.length)
            throw new IllegalArgumentException("levelid must be a valid number");

        return this.currentLevel = levels[levelId];
    }

    public boolean isFinished() {
        return currentLevel.getPlayer().equals(currentLevel.getTarget()) ||
                currentLevel.getPlayer().equals(currentLevel.getEnemy());
    }

    public GameLevel moveTo(Coordinates nextMove) throws Exception {
        if (!moveIsPossible(currentLevel, nextMove)) throw new Exception("Move not possible");
        return new GameLevel(currentLevel.getField(), nextMove, makeNextAIMove(currentLevel, nextMove));
    }

    private boolean moveIsPossible(GameLevel currentLevel, Coordinates nextMove) {
        return currentLevel.getField()[nextMove.getX()][nextMove.getY()] == BoardField.EMPTY &&
                isInsideOfField(nextMove, currentLevel.getField()) &&
                isNextToPreviousMove(nextMove, currentLevel.getPlayer());
    }

    private boolean isNextToPreviousMove(Coordinates nextMove, Coordinates position) {
        if (isIn1AwayFrom(nextMove.getX(), position.getX())) {
            return !isIn1AwayFrom(nextMove.getY(), position.getY());
        } else {
            return isIn1AwayFrom(nextMove.getY(), position.getY());
        }
    }

    private boolean isIn1AwayFrom(int i1, int i2) {
        return i1 - 1 == i2 || i2 - 1 == i1;
    }

    private boolean isInsideOfField(Coordinates nextMove, BoardField[][] field) {
        return nextMove.getX() >= 0 && nextMove.getX() < field.length &&
                nextMove.getY() >= 0 && nextMove.getY() < field[nextMove.getX()].length;
    }

    private Coordinates makeNextAIMove(GameLevel currentLevel, Coordinates nextMove) {
        
    }

}
