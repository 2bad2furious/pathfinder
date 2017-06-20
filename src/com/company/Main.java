package com.company;

import com.company.Loader.LevelLoader;
import com.company.UI.UI;
import com.company.domain.Game;
import com.company.domain.GameLevel;
import com.google.gson.Gson;


public class Main {

    public static void main(String[] args) throws Exception {

        Game game = new Game(new LevelLoader(new Gson()));

        GameLevel level = UI.listAndChooseGameLevel(game.getLevels());

        game.selectLevel(level);

        while (!game.isFinished()) {
            UI.printLevel(level);
            try {
                level = game.moveTo(UI.getNextMove(level));
            } catch (Throwable ex) {
                UI.wrongMove();
            }
        }
        UI.printLevel(level);
        UI.printWinOrLose(game.getWinner());
    }
}
