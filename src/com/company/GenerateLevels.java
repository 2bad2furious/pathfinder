package com.company;

import com.company.domain.BoardField;
import com.company.domain.Coordinates;
import com.company.domain.GameLevel;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 28.05.2017.
 */
public class GenerateLevels {
    private final String path = "C:\\Users\\user\\Documents\\pathfinder\\levels.json";

    public GenerateLevels(int numberOfLevels, Gson gson) {
        GameLevel[] arr = new GameLevel[numberOfLevels];
        Random rn = new Random();

        for (int i = 0; i < numberOfLevels; i++) {
            int size1 = getRandomSize(rn, 10, 30);
            int size2 = getRandomSize(rn, 10, 30);

            BoardField[][] field = generateRandomField(rn, size1, size2, 6);
            arr[i] = new GameLevel(field, getTopLeft(field), getBottomRight(field));
            int a = 1+1;
        }
        List<String> lines = Arrays.asList(gson.toJson(arr));
        Path file = Paths.get("levels.json");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Coordinates getTopLeft(BoardField[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == BoardField.EMPTY) return new Coordinates(i, j);
            }
        }
        return new Coordinates(-1, -1);
    }

    private Coordinates getBottomRight(BoardField[][] field) {
        for (int i = field.length - 1; i >= 0; i--) {
            for (int j = field[i].length - 1; j >= 0; j--) {
                if (field[i][j] == BoardField.EMPTY) return new Coordinates(i, j);
            }
        }
        return new Coordinates(-1, -1);
    }

    private BoardField[][] generateRandomField(Random rn, int size1, int size2, int mod) {
        BoardField[][] field = new BoardField[size1][size2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int random = rn.nextInt(100);
                field[i][j] = (random % mod == 0) ? BoardField.BLOCKED : BoardField.EMPTY;
            }
        }
        return field;
    }

    private int getRandomSize(Random random, int min, int max) {
        int size = random.nextInt(max);
        if (size < min) size += min;
        return size;
    }
}
