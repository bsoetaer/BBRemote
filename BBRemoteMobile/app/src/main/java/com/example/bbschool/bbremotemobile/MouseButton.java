package com.example.bbschool.bbremotemobile;

/**
 * Created by Brendan on 3/24/2016.
 */
public enum MouseButton {
    LEFT(0),
    MIDDLE(1),
    RIGHT(2);

    private int val;

    MouseButton(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}