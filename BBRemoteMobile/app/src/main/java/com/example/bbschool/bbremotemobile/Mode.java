package com.example.bbschool.bbremotemobile;

/**
 * Enum for different possible input modes.
 * 3.2.2. Select Input Mode
 * Created by Braeden on 3/10/2016.
 */
public enum Mode {
    Keyboard(1),
    Touchpad(2),
    Optical(3),
    Camera(4),
    Mic(5),
    Gamepad(6),
    None(7);

    private int val;

    Mode(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
