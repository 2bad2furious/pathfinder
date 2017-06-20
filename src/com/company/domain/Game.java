package com.company.domain;

import com.company.AI.BadAI;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by user on 27.05.2017.
 */
public class Game {
    private Winner winner = null;
    private GameLevel[] levels = new GameLevel[0];
    private Optional<GameLevel> currentlevel = Optional.empty();

    public Game(LevelLoaderI loader) throws IOException {
        this.levels = loader.getAllLevels();
    }

    public GameLevel[] getLevels() {
        return levels;
    }

    public void selectLevel(GameLevel level) throws Exception {
        winner = Winner.NONE;
        if (levels == null) throw new Exception("Levels are null");
        for (GameLevel i : levels) {
            if (i == level) {
                currentlevel = Optional.of(i);
                return;
            }
        }
        throw new Exception("GameLevel not found");
    }

    public boolean isFinished() throws Exception {
        GameLevel level = this.getCurrentLevel();

        boolean didPlayerWin = level.getPlayer().equals(level.getTarget());
        boolean didAIWin = level.getPlayer().equals(level.getEnemy());

        if (didPlayerWin) winner = Winner.PLAYER;
        else if (didAIWin) winner = Winner.AI;

        return didPlayerWin || didAIWin;
    }

    public GameLevel moveTo(Coordinates nextMove) throws Exception {
        GameLevel level = getCurrentLevel();

        if (!moveIsPossible(level, nextMove)) throw new Exception("Move not possible");
        level = new GameLevel(level.getField(), nextMove, makeNextAIMove(), level.getTarget());
        return level;
    }

    private boolean moveIsPossible(GameLevel currentLevel, Coordinates nextMove) {
        boolean isEmpty = currentLevel.getField()[nextMove.getX()][nextMove.getY()] == BoardField.EMPTY;
        boolean isInside = isInsideOfField(nextMove, currentLevel.getField());
        boolean isNext = isNextToPreviousMove(nextMove, currentLevel.getPlayer());
        return isEmpty &&
                isInside &&
                isNext;
    }

    private GameLevel getCurrentLevel() throws Exception {
        return currentlevel.orElseThrow(() -> new Exception("No currentlevel set"));
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
        GameLevel level = getCurrentLevel();
        AI ai = new BadAI(level.getEnemy(), level.getPlayer(), level.getField());
        return ai.getMove();
    }

    public Winner getWinner() {
        return winner;
    }
}
