package com.company.AI;

import com.company.domain.AI;
import com.company.domain.BoardField;
import com.company.domain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by user on 11.06.2017.
 */
public class BadAI implements AI {
    private static final int MAX_VALUE = 1000;
    private final Coordinates enemy;
    private final Coordinates player;
    private final int[][] paths;
    private final BoardField[][] field;

    public BadAI(Coordinates enemy, Coordinates player, BoardField[][] field) {
        this.enemy = enemy;
        this.player = player;
        this.field = field;
        this.paths = initPaths(field, player);
    }

    private int[][] initPaths(BoardField[][] field, Coordinates player) {
        int[][] paths = new int[field.length][field[0].length];
        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths[i].length; j++) {
                paths[i][j] = (player.getX() == i && player.getY() == j) ? 0 : MAX_VALUE;
            }
        }
        return paths;
    }


    @Override
    public Coordinates getMove() {
        countPaths();
        return getBestMove().orElseThrow(IllegalStateException::new);
    }

    private Optional<Coordinates> getBestMove() {
        int best = Integer.MAX_VALUE;
        Coordinates bestC = null;

        Coordinates[] availables = getSurroundings(enemy);
        for (Coordinates available : availables) {
            int path = paths[available.getX()][available.getY()];
            if (path < best) {
                best = path;
                bestC = available;
            }
        }
        return Optional.ofNullable(bestC);
    }

    private void countPaths() {
        for (int x = 0; x < paths.length; x++) {
            for (int y = 0; y < paths[x].length; y++) {
                int best = MAX_VALUE;
                Coordinates[] surroundings = getSurroundings(new Coordinates(x, y));
                for (Coordinates c : surroundings) {
                    if (paths[c.getX()][c.getY()] < best)
                        best = paths[c.getX()][c.getY()];

                }
                if (paths[x][y] >= best && field[x][y] != BoardField.BLOCKED)
                    paths[x][y] = best + 1;
            }
        }
    }

    private Coordinates[] getSurroundings(Coordinates c) {
        List<Coordinates> surs = new ArrayList<>();
        surs = addIfItIsOkay(new Coordinates(c.getX() + 1, c.getY()), surs);
        surs = addIfItIsOkay(new Coordinates(c.getX() - 1, c.getY()), surs);
        surs = addIfItIsOkay(new Coordinates(c.getX(), c.getY() + 1), surs);
        surs = addIfItIsOkay(new Coordinates(c.getX(), c.getY() - 1), surs);
        return getArrayFromListLol(surs);
    }

    private Coordinates[] getArrayFromListLol(List<Coordinates> list) {
        Coordinates[] arr = new Coordinates[list.size()];
        for (int i = 0; (i < list.size() && i < arr.length); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    private List<Coordinates> addIfItIsOkay(Coordinates c, List<Coordinates> list) {
        if (isInsideOfPaths(c))
            list.add(c);
        return list;
    }

    private boolean isInsideOfPaths(Coordinates c) {
        return c.getX() >= 0 && c.getX() < paths.length && c.getY() >= 0 && c.getY() < paths[c.getX()].length;
    }
}
