package com.company;

import com.company.domain.Game;
import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {


        Game g = Game.newInstance(5);
        Gson gson = new Gson();
        System.out.println(gson.toJson(g));
    }
}
