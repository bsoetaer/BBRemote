package com.example.bbschool.bbremotemobile;

/**
 * Created by Braeden on 3/28/2016.
 */
public enum GamepadInput {
    A_BUTTON(0),
    B_BUTTON(1),
    X_BUTTON(2),
    Y_BUTTON(3),
    LEFT_BUMPER(4),
    RIGHT_BUMPER(5),
    LEFT_TRIGGER(6),
    RIGHT_TRIGGER(7),
    BACK(8),
    START(9),
    LEFT_STICK(10),
    RIGHT_STICK(11),
    UP_DPAD(12),
    DOWN_DPAD(13),
    LEFT_DPAD(14),
    RIGHT_DPAD(15);

    private int val;

    GamepadInput(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
