package com.example.bbschool.bbremotemobile;

/**
 * Enum for mouse button ids.
 * 3.2.4.2.3. Left Click
 * 3.2.4.2.4. Right Click
 * 3.2.5.2.4. Left Click
 * 3.2.5.2.5. Right Click
 * 3.2.5.2.8. Middle Mouse Button
 * Created by Brendan on 3/24/2016.
 */
public enum MouseButton {
    LEFT(0),
    RIGHT(1),
    MIDDLE(2);


    private int val;

    MouseButton(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }
}
