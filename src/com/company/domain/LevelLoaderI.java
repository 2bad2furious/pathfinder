package com.company.domain;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by user on 27.05.2017.
 */
public interface LevelLoaderI {

    public GameLevel[] getAllLevels() throws IOException;
}
