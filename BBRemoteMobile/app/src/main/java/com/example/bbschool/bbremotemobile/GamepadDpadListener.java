package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Braeden-MSI on 3/31/2016.
 */
public class GamepadDpadListener implements View.OnTouchListener{

    private static final String TAG = "GamepadDpadListener";
    Context myContext;
    float centerX, centerY;
    GamepadInput lastInput;

    public GamepadDpadListener(Context myContext) {
        this.myContext =  myContext;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        centerX = v.getWidth() / 2;
        centerY = v.getHeight() / 2;

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                lastInput = null;
                buttonPressed(event);
                break;
            case MotionEvent.ACTION_MOVE:
                buttonPressed(event);
                break;
            case MotionEvent.ACTION_UP:
                onUp(event);
                break;
        }
        return true;
    }

    private GamepadInput findInput(MotionEvent event) {
        float X = event.getX();
        float Y = event.getY();
        float xDiff = (float) Math.abs((Math.tan(Math.PI / 4) * Math.abs(Y - centerY)));

        if( Math.abs(X - centerX) < xDiff) {
            if( Y <= centerY )
                return GamepadInput.UP_DPAD;
            else
                return GamepadInput.DOWN_DPAD;
        } else {
            if( X <= centerX )
                return GamepadInput.LEFT_DPAD;
            else
                return GamepadInput.RIGHT_DPAD;
        }
    }

    private void onUp(MotionEvent event) {
        sendButtonPressed(lastInput, false);
    }

    private void buttonPressed(MotionEvent event) {
        GamepadInput input = findInput(event);
        if(lastInput == null )
            sendButtonPressed(input, true);
        else if( lastInput.getVal() != input.getVal()) {
            sendButtonPressed(lastInput, false);
            sendButtonPressed(input, true);
        }
        lastInput = input;
    }

    private void sendButtonPressed(GamepadInput input, Boolean pressed) {
        Map<Integer, Boolean> buttonPresses = new HashMap<>();
        buttonPresses.put(input.getVal(), pressed);
        try {
            ButtonBluetoothTransmitter.sendKeys(buttonPresses);
        }
        catch (IOException e) {
            Log.w(TAG, "Failed button press of button " + input.getVal() + ": " + e.getMessage());
            ModeChanger.disconnected(myContext);
        }
    }
}
