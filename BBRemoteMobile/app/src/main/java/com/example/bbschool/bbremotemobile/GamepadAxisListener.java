package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Listener for detecting movements on a gamepad axis and sending them to Windows. Handles both
 * axis movements and axis toggle. Toggle performed by double tap on the axis and will remain until
 * released.
 * 3.2.7.2.3. Press Toggle Button
 * 3.2.7.2.7. Multiple Input Press
 * Created by Braeden-MSI on 3/31/2016.
 */
public class GamepadAxisListener implements View.OnTouchListener {

    private static final String TAG = "GamepadAxisListener";
    private static final int pixelError = 25;
    private static final int maxStickMovement = 100;
    Context myContext;
    long startTime = 0;
    boolean stickIsPressed = false;
    float centerX, centerY;
    GamepadInput stick;

    public GamepadAxisListener(Context myContext, GamepadInput stick) {
        this.myContext =  myContext;
        this.stick = stick;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        centerX = v.getWidth() / 2;
        centerY = v.getHeight() / 2;

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                onDown(event);
                stickMove(event);
                break;
            case MotionEvent.ACTION_MOVE:
                stickMove(event);
                break;
            case MotionEvent.ACTION_UP:
                onUp(event);
                break;
        }
        return true;
    }

    private void onDown(MotionEvent event) {
        if(event.getDownTime() - startTime < 1000) { //Double tap within 1 second
            stickIsPressed = true;
            sendStickPressed(true);
        }
        startTime = event.getDownTime();
    }

    private void onUp(MotionEvent event) {
        if(stickIsPressed) {
            stickIsPressed = false;
            sendStickPressed(false);
        }
        sendZeroMovement();
    }

    private void sendZeroMovement() {
        try {
            BluetoothAxisTransmitter.sendMovement(axisMovementMap(0f, 0f));
        }
        catch (IOException e) {
            Log.w(TAG, "Failed axis movement of zero");
        }
    }

    private void stickMove(MotionEvent event) {
        float deltaX = event.getX() - centerX;
        float deltaY = event.getY() - centerY;
        if(Math.abs(deltaX) < pixelError)
            deltaX = 0;
        if(Math.abs(deltaY) < pixelError)
            deltaY = 0;
        try {
            BluetoothAxisTransmitter.sendMovement(axisMovementMap(deltaX, deltaY));
        } catch (IOException e) {
            Log.w(TAG, "Failed axis movement: x: " + deltaX + ", y: " + deltaY);
        }
        // TODO Call MotionBluetoothTransmitter with deltaX and deltaY and sensitivity for stick move
    }

    private Map<Integer, Byte> axisMovementMap(Float x, Float y) {
        x = -x; // the x is backwards
        if(stick == GamepadInput.LEFT_STICK)
            return axisMovementMap(GamepadAxis.LEFT_X, x, GamepadAxis.LEFT_Y, y);
        else if (stick == GamepadInput.RIGHT_STICK)
            return axisMovementMap(GamepadAxis.RIGHT_X, x, GamepadAxis.RIGHT_Y, y);
        Log.w(TAG, "Failed axis movement: x: " + x + ", y: " + y);
        return new HashMap<>();
    }

    private Map<Integer, Byte> axisMovementMap(GamepadAxis xAxis, Float x, GamepadAxis yAxis, Float y) {
        Map<Integer, Byte> retMap = new HashMap<>();
        byte normalizedX = normalize(x);
        byte normalizedY = normalize(y);
        retMap.put(xAxis.getVal(), normalizedX);
        retMap.put(yAxis.getVal(), normalizedY);
        Log.w(TAG, "Axis movement: x: " + x + ", y: " + y);
        Log.w(TAG, "normalizedX: " + normalizedX + ", normalizedY: " + normalizedY);
        return retMap;
    }

    private Byte normalize(Float val) {
        float maxSensitivityStickMovment = maxStickMovement * (1 - (float)PreferenceManager.getDefaultSharedPreferences(myContext).getInt("GAMEPAD_SENSITIVITY", 50) / 100f );
        float clippedMovement = Math.max(Math.min(val, maxSensitivityStickMovment), -maxSensitivityStickMovment);
        float normalizedMovement = clippedMovement / maxSensitivityStickMovment * 127;
        return (byte) normalizedMovement;

        /*int sensitivity = PreferenceManager.getDefaultSharedPreferences(myContext).getInt("GAMEPAD_SENSITIVITY", 50);
        float movementWithoutSensitivity = Math.min(val, maxStickMovement)/maxStickMovement * 127;
        return (byte)(movementWithoutSensitivity * (float)sensitivity/(float)100);*/
    }

    private void sendStickPressed(Boolean pressed) {
        //TODO is this the right way to send stick pressed down?
        Map<Integer, Boolean> buttonPresses = new HashMap<>();
        buttonPresses.put(stick.getVal(), pressed);
        try {
            ButtonBluetoothTransmitter.sendKeys(buttonPresses);
        }
        catch (IOException e) {
            Log.w(TAG, "Failed button press of button " + stick.getVal() + ": " + e.getMessage());
            ModeChanger.disconnected(myContext);
        }
    }

}