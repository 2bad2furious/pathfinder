package com.company.domain;

import com.company.AI.BadAI;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by user on 27.05.2017.
 */
public class Game {
    private Winner winner = null;
    private GameLevel[] levels = new GameLevel[0];
    private GameLevel currentLevel = null;

    private int[][] visited;

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
        boolean didPlayerWin = currentLevel.getPlayer().equals(currentLevel.getTarget());
        boolean didAIWin = currentLevel.getPlayer().equals(currentLevel.getEnemy());

        if (didPlayerWin) winner = Winner.PLAYER;
        else if (didAIWin) winner = Winner.AI;

        return didPlayerWin || didAIWin;
    }

    public GameLevel moveTo(Coordinates nextMove) throws Exception {
        if (!moveIsPossible(currentLevel, nextMove)) throw new Exception("Move not possible");
        currentLevel = new GameLevel(currentLevel.getField(), nextMove, makeNextAIMove(), currentLevel.getTarget());
        return currentLevel;
    }

    private boolean moveIsPossible(GameLevel currentLevel, Coordinates nextMove) {
        boolean isEmpty = currentLevel.getField()[nextMove.getX()][nextMove.getY()] == BoardField.EMPTY;
        boolean isInside = isInsideOfField(nextMove, currentLevel.getField());
        boolean isNext = isNextToPreviousMove(nextMove, currentLevel.getPlayer());
        return isEmpty &&
                isInside &&
                isNext;
    }

    private boolean isNextToPreviousMove(Coordinates nextMove, Coordinates position) {
        int diffx = Math.abs(nextMove.getX() - position.getX());
        int diffy = Math.abs(nextMove.getY() - position.getY());

        if (diffx == 1)
            return diffy == 0;
        else
            return diffx == 0 && diffy == 1;
    }

    private boolean isInsideOfField(Coordinates nextMove, BoardField[][] field) {
        return nextMove.getX() >= 0 && nextMove.getX() < field.length &&
                nextMove.getY() >= 0 && nextMove.getY() < field[nextMove.getX()].length;
    }

    private Coordinates makeNextAIMove() throws Exception {
        AI ai = new BadAI(currentLevel.getEnemy(), currentLevel.getPlayer(), currentLevel.getField());
        return ai.getMove();
    }

    public Winner getWinner() {
        return winner;
    }
}
