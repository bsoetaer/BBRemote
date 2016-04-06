package com.example.bbschool.bbremotemobile;

/**
 * Created by Brendan on 3/24/2016.
 */
public enum MouseAxis {
    X(0),
    Y(1),
    SCROLL(2);

    private int val;

    MouseAxis(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
