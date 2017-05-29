package com.company.Loader;

import com.company.domain.GameLevel;
import com.company.domain.LevelLoaderI;
import com.google.gson.Gson;

import java.io.*;

/**
 * Created by user on 27.05.2017.
 */
public class LevelLoader implements LevelLoaderI {
    private static final String PATH_TO_FILE = "C:\\Users\\user\\Documents\\pathfinder\\levels.json";
    private Gson gson;

    public LevelLoader(Gson gson) throws Exception {
        if(gson == null) throw new NullPointerException("gson cannot be null");
        this.gson = gson;

        File file = new File(PATH_TO_FILE);
        if(!file.exists() && file.isDirectory()) throw new Exception("filepath is folder or doesnt exist");
    }

    //stackoverflow xd https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java#4716623
    @Override
    public GameLevel[] getAllLevels() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(PATH_TO_FILE))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return gson.fromJson(everything,GameLevel[].class);
        }
    }

}
