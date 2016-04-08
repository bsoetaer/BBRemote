package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Layout containing gamepad inputs
 * All requirements under
 * 3.2.7. Touchscreen Gamepad Input
 * 3.2.8. Customize Gamepad Layout
 * Created by Braeden on 3/29/2016.
 */
public class GamepadLayout extends RelativeLayout {

    private ArrayList<GamepadInputView> gamepadInputs;
    private boolean rotateAsInput = false;
    private boolean portrait = true; // false means landscape
    private String name = null;
    public static final Integer ID = 15; // Arbitrary number

    public GamepadLayout(Context context) {
        super(context);
        init();
    }

    public GamepadLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public GamepadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        gamepadInputs = new ArrayList<>();
    }

    public void addGamepadInput(GamepadInputView input) {
        gamepadInputs.add(input);
        addView(input);
    }

    public void removeGamepadInput(GamepadInputView input) {
        gamepadInputs.remove(input);
        removeView(input);
    }

    public ArrayList<GamepadInputView> getGamepadInputs() {
        return gamepadInputs;
    }

    public void setRotateAsInput(boolean setting) {
        rotateAsInput = setting;
    }

    public boolean getRotateAsInput() {
        return rotateAsInput;
    }

    public void setPortrait(boolean setting) {
        portrait = setting;
    }

    public boolean getPortrait() {
        return portrait;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
