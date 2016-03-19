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
