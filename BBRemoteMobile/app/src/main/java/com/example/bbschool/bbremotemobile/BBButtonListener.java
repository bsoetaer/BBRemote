package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Listener for all input mode buttons that have a simple down and up functionality.
 * Required by all requirements that send these buttons.
 * 3.2.4.2.3. Left Click
 * 3.2.4.2.4. Right Click
 * 3.2.5.2.4. Left Click
 * 3.2.5.2.5. Right Click
 * 3.2.5.2.8. Middle Mouse Button
 * 3.2.7.2.1. Press Gamepad Button
 * 3.2.7.2.7. Multiple Input Press
 * Created by Braeden on 3/13/2016.
 */
public class BBButtonListener implements Button.OnTouchListener {

    private int keyCode;
    private Context myContext;
    private static final String TAG = "BBButtonListener";

    public BBButtonListener(int keyCode, Context myContext) {
        this.keyCode = keyCode;
        this.myContext = myContext;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                sendButton(true);
                return true;
            case MotionEvent.ACTION_UP:
                sendButton(false);
                return true;
        }
        return false;
    }

    private void sendButton(Boolean pressed) {
        Map<Integer, Boolean> buttonPresses = new HashMap<>();
        buttonPresses.put(keyCode, pressed);
        try {
            ButtonBluetoothTransmitter.sendKeys(buttonPresses);
        }
        catch (IOException e) {
            Log.w(TAG, "Failed button press of button " + keyCode + ": " + e.getMessage());
            ModeChanger.disconnected(myContext);
        }
    }
}
