package com.company.UI;

import com.company.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by user on 27.05.2017.
 */
public class UI {
    private static Scanner scanner = new Scanner(System.in);

    public static void printLevel(GameLevel level) {
        BoardField[][] field = level.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (level.getPlayer().isSame(i, j)) System.out.print("U");
                else if (level.getEnemy().isSame(i, j)) System.out.print("E");
                else if (level.getEnemy().isSame(i, j)) System.out.print("T");
                else System.out.print(field[i][j] == BoardField.EMPTY ? "_" : "x");
            }
            System.out.println();
        }
    }

    public static GameLevel listAndChooseGameLevel(GameLevel[] levels) throws Exception {
        if (levels == null || levels.length == 0) throw new NullPointerException();

        List<Option<GameLevel>> options = new ArrayList<>();

        for (GameLevel level : levels) {
            options.add(new Option<>(level, "Level " + options.size()));
        }

        return optionSelection("Please, choose a level", options).getAnswer();
    }

    private static <E> Option<E> optionSelection(String intro, List<Option<E>> arr) {
        if (arr == null || arr.size() == 0) throw new IllegalArgumentException("invalid options array");

        StringBuilder options = new StringBuilder();

        for (int i = 0; i < arr.size(); i++) {
            options.append(i).append(". ").append(arr.get(i).getTitle()).append("\n");
        }

        System.out.println(intro);
        System.out.println(options.toString());

        int index = scanner.nextInt();

        if (index >= 0 && index < arr.size()) return arr.get(index);

        System.out.println("Invalid option. Please choose again");
        return optionSelection(intro, arr);
    }

    public static Coordinates getNextMove(GameLevel level) {
        int x = getCoordinate(0, level.getField().length, "Choose an x coordinate.", "x coordinate is");
        int y = getCoordinate(0, level.getField()[x].length, "Choose an y coordinate.", "y coordinate is");
        return new Coordinates(x, y);
    }

    private static int getCoordinate(int min, int max, String init, String afterChoice) {
        System.out.println(init);
        int coordinate = -1;
        boolean error = false;
        while (coordinate < min || coordinate >= max) {
            if (error) System.out.println("Number must be between " + min + " and " + max + ".");
            coordinate = scanner.nextInt();
            error = true;
        }
        System.out.println(afterChoice + " " + coordinate);
        return coordinate;
    }

    public static void wrongMove() {
        System.out.println("Wrong move, please make a move again");
    }

    public static void printWinOrLose(Winner winner) throws Exception {
        if (winner == Winner.NONE) throw new Exception("there is no winner in living");
        if (winner == Winner.AI) System.out.println("You lost");
        else if (winner == Winner.PLAYER) System.out.println("You won");
    }
}
