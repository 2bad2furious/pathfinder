package com.company.domain;

/**
 * Created by user on 27.05.2017.
 */
public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof Coordinates)) return false;
        Coordinates nobj = (Coordinates) obj;
        return nobj.getX() == getX() && nobj.getY() == getY();
    }
}
