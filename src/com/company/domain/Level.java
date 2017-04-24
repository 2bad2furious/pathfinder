package com.company.domain;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 23.04.2017.
 */
public class Level {
    public final String file = "level.json";

    public static List<Board> loadBoards(String filepath) throws Exception {
        File f = new File(filepath);
        if (!f.exists() || f.isDirectory()) throw new Exception("File Does not exist");

        List<Board> levels = new ArrayList<>();

        FileReader reader = new FileReader(f);

        BufferedReader br = new BufferedReader(reader);

        Gson gson = new Gson();

        try {
            levels.add(gson.fromJson(br, Board.class));
        } finally {
            br.close();
            reader.close();
        }

        return levels;
    }
}
