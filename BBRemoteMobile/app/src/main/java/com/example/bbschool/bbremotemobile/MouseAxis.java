package com.example.bbschool.bbremotemobile;

/**
 * Enum for mouse axis.
 * 3.2.4.2.1. Send Mouse Movement
 * 3.2.5.2.1. Send Mouse Movement
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
