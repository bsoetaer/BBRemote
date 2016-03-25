package com.example.bbschool.bbremotemobile;

/**
 * Created by Braeden on 3/10/2016.
 */
public enum Mode {
    Keyboard(0),
    Touchpad(1),
    Optical(2),
    Camera(3),
    Mic(4),
    Gamepad(5),
    None(6);

    private int val;

    Mode(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
