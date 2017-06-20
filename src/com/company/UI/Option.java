package com.company.UI;

import com.company.domain.GameLevel;

/**
 * Created by user on 12.06.2017.
 */
public final class Option<T> {
    private T o;
    private String title;

    public Option(T o, String title) {
        this.o = o;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public T getAnswer() {
        return o;
    }
}
