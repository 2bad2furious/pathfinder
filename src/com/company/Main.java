package com.company;

import com.company.Loader.LevelLoader;
import com.company.domain.Game;
import com.company.domain.GameLevel;
import com.google.gson.Gson;


public class Main {

    public static void main(String[] args) throws Exception {

        Game game = new Game(new LevelLoader(new Gson()));

        int levelId = UI.listAndChooseGameLevel(game.getLevels());

        GameLevel level = game.selectLevel(levelId);

        while(!game.isFinished()){
            UI.printLevel(level);
            level = game.moveTo(UI.getNextMove(level));
        }
    }
}
