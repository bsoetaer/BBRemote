package com.example.bbschool.bbremotemobile;

/**
 * Created by Brendan on 4/3/2016.
 */
public enum GamepadAxis {
    LEFT_X(0),
    LEFT_Y(1),
    RIGHT_X(2),
    RIGHT_Y(3);

    private int val;

    GamepadAxis(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
