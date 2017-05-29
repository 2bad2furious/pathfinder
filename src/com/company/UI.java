package com.company;

import com.company.domain.BoardField;
import com.company.domain.Coordinates;
import com.company.domain.GameLevel;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Scanner;

/**
 * Created by user on 27.05.2017.
 */
class UI {
    private static Scanner scanner = new Scanner(System.in);

    static void printLevel(GameLevel level) {
        BoardField[][] field = level.getField();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (level.getPlayer().getX() == i && level.getPlayer().getY() == j) System.out.print("U");
                else if (level.getEnemy().getX() == i && level.getEnemy().getY() == j) System.out.print("E");
                else if (level.getTarget().getX() == i && level.getTarget().getY() == j) System.out.print("T");
                else System.out.print(field[i][j] == BoardField.EMPTY ? "_" : "x");
            }
            System.out.println();
        }
    }

    static int listAndChooseGameLevel(GameLevel[] levels) {
        if (levels == null || levels.length == 0) throw new NullPointerException();

        String[] options = new String[levels.length];

        for (int i = 0; i < options.length; i++) {
            options[i] = "Level " + i;
        }

        return optionSelection("Please select a level to play", options);
    }

    //would be a great object i guess
    private static int optionSelection(String intro, String[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("invalid options array");

        StringBuilder options = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            options.append(i).append(". ").append(arr[i]).append("\n");
        }

        System.out.println(intro);
        System.out.println(options.toString());

        int index = scanner.nextInt();

        if (index >= 0 && index < arr.length) return index;

        System.out.println("Invalid option. Please choose again");
        return optionSelection(intro, arr);
    }

    public static void errorStartingGame(Exception ex) {
        System.out.println("There was an error during the game" + ex.getLocalizedMessage());
    }


    public static Coordinates getNextMove(GameLevel level) {
        int x = 0;
        while(true){
            x = scanner.nextInt();
            if (x >= level.getField().length || x < 0) {
                System.out.println("Number must be between 0 and " + level.getField().length);
            }else{
                break;
            }
        }
        int y = 0;
        while (true){
            y = scanner.nextInt();
            if (y >= level.getField()[x].length || y < 0) {
                System.out.println("Number must be between 0 and " + level.getField()[x].length);
            }else{
                return new Coordinates(x, y);
            }
        }
    }
}
