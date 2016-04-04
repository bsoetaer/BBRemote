package com.example.bbschool.bbremotemobile;

import java.util.HashMap;

/**
 * Created by Braeden on 3/28/2016.
 */
public class GamepadInputImage {
    private static HashMap<GamepadInput, Integer> imageMap = new HashMap<>();

    static {
        imageMap.put(GamepadInput.A_BUTTON, R.drawable.gamepad_a);
        imageMap.put(GamepadInput.B_BUTTON, R.drawable.gamepad_b);
        imageMap.put(GamepadInput.X_BUTTON, R.drawable.gamepad_x);
        imageMap.put(GamepadInput.Y_BUTTON, R.drawable.gamepad_y);
        imageMap.put(GamepadInput.LEFT_BUMPER, R.drawable.gamepad_lb);
        imageMap.put(GamepadInput.RIGHT_BUMPER, R.drawable.gamepad_rb);
        imageMap.put(GamepadInput.LEFT_TRIGGER, R.drawable.gamepad_lt);
        imageMap.put(GamepadInput.RIGHT_TRIGGER, R.drawable.gamepad_rt);
        imageMap.put(GamepadInput.BACK, R.drawable.gamepad_back_alt);
        imageMap.put(GamepadInput.START, R.drawable.gamepad_start_alt);
        imageMap.put(GamepadInput.LEFT_STICK, R.drawable.gamepad_left_stick);
        imageMap.put(GamepadInput.RIGHT_STICK, R.drawable.gamepad_right_stick);
        imageMap.put(GamepadInput.UP_DPAD, R.drawable.gamepad_dpad);
        imageMap.put(GamepadInput.DOWN_DPAD, R.drawable.gamepad_dpad);
        imageMap.put(GamepadInput.LEFT_DPAD, R.drawable.gamepad_dpad);
        imageMap.put(GamepadInput.RIGHT_DPAD, R.drawable.gamepad_dpad);
    }

    public static Integer getImageId(GamepadInput gamepadInput) {
        return imageMap.get(gamepadInput);
    }
}
