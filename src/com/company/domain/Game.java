package com.company.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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

    private Coordinates makeNextAIMove(GameLevel currentLevel, Coordinates nextMove) throws Exception {
        Optional<Coordinates> next = getNextMove(currentLevel.getField(), currentLevel.getEnemy());
        if (next.isPresent()) return next.get();
        else throw new Exception("No path found");
    }

    //This was a bad idea, i know
    private Optional<Coordinates> getNextMove(BoardField[][] field, Coordinates position) {
        int[][] visitedCounts = new int[field.length][field[0].length];

        Coordinates[] available = getAvailableMoves(field, position);

        int best = field.length * field[0].length;
        Coordinates bestC = null;

        for (Coordinates anAvailable : available) {
            int a = getCount(field, anAvailable, position, currentLevel.getTarget(), visitedCounts);
            if (a < best) {
                bestC = anAvailable;
                best = a;
            }
        }

        return Optional.of(bestC);
    }

    //stejnej kod jako nahoře :(
    private int getCount(BoardField[][] fields, Coordinates position, Coordinates previous, Coordinates target, int[][] visitedCounts) {
        int visited = visitedCounts[position.getX()][position.getY()];
        if (visited != 0) return visited;

        int best = Integer.MAX_VALUE;

        if (position.equals(target)) best = 0;

        if (best != 0) {
            Coordinates[] available = getAvailableMoves(fields, position);

            for (int i = 0; i < available.length; i++) {
                if (available[i].equals(previous)) continue;

                int count = getCount(fields, available[i], position, target, visitedCounts);
                if (count < best) best = count;
            }
        }
        visitedCounts[position.getX()][position.getY()] = best;

        return 1 + best;
    }


    private Coordinates[] getAvailableMoves(BoardField[][] field, Coordinates position) {
        ArrayList<Coordinates> available = new ArrayList<>();

        addIfItIsOK(field, new Coordinates(position.getX() + 1, position.getY()), available);
        addIfItIsOK(field, new Coordinates(position.getX() - 1, position.getY()), available);
        addIfItIsOK(field, new Coordinates(position.getX(), position.getY() + 1), available);
        addIfItIsOK(field, new Coordinates(position.getX(), position.getY() - 1), available);

        return (Coordinates[]) available.toArray();
    }

    private void addIfItIsOK(BoardField[][] field, Coordinates position, ArrayList<Coordinates> list) {
        if (isInsideOfField(position, field) && field[position.getX()][position.getY()] != BoardField.BLOCKED)
            list.add(position);
    }
}
