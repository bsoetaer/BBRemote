package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.Log;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Braeden on 3/13/2016.
 */
public class TouchpadTouchListener implements View.OnTouchListener {
    Context myContext;
    private boolean mouseDown = false;
    private float lastX = 0;
    private float lastY = 0;
    private boolean isScroll = false;
    public static final float maxScroll = 1500;

    private static final String TAG = "TouchpadTouchListener";
    private static final float MAX_VELOCITY = 150;

    public TouchpadTouchListener(Context myContext) {
        this.myContext =  myContext;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        int action = event.getActionMasked();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                mouseDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                scrollDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mouseMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mouseUp(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                scrollUp(event);
                break;
        }
        return true;
    }

    private void mouseDown(MotionEvent event) {
        mouseDown = true;
        updateLastPoint(event);
    }

    private void scrollDown(MotionEvent event) {
        isScroll = true;
        updateLastPoint(event);
    }

    private void mouseUp(MotionEvent event) {
        mouseDown = false;
        lastX = 0;
        lastY = 0;
        sendMovementData(lastX, lastY);
    }

    private void scrollUp(MotionEvent event) {
        sendMovementData(0,0);
        isScroll = false;
        updateLastPoint(event);
    }

    private void mouseMove(MotionEvent event) {
        float deltaX = lastX - event.getX();
        float deltaY = lastY - event.getY();
        sendMovementData(deltaX, deltaY);
        updateLastPoint(event);
    }

    private void sendMovementData(float deltaX, float deltaY) {
        if( isScroll )
            sendScrollData(deltaX, deltaY);
        else
            sendCursorMovementData(deltaX, deltaY);
    }

    private void sendScrollData(float deltaX, float deltaY) {
        try {
            byte normalizedData = normalizeScroll(deltaY);
            BluetoothAxisTransmitter.sendSingleMovement(MouseAxis.SCROLL.getVal(), normalizedData);
            Log.w(TAG, "scroll: " + normalizedData);
        } catch (IOException e) {
            Log.w(TAG, "Failed touchpad scroll movement (value: " + deltaY + "). " + e.getMessage());
        }
    }

    private byte normalizeScroll(float val) {
        return (byte)((int)Math.min(val, maxScroll) / maxScroll * 127);
    }

    private void sendCursorMovementData(float deltaX, float deltaY) {
        Map<Integer,Byte> normalizedMovement = new HashMap<>();
        byte normalizedX = normalize(deltaX);
        byte normalizedY = normalize(deltaY);
        normalizedMovement.put(MouseAxis.X.getVal(), normalizedX);
        normalizedMovement.put(MouseAxis.Y.getVal(), normalizedY);
        try {
            BluetoothAxisTransmitter.sendMovement(normalizedMovement);
        } catch (IOException e) {
            Log.w(TAG, "Failed touchpad mouse movement (X: " + deltaX + ", Y: " + deltaY + "). " + e.getMessage());
            ModeChanger.disconnected(myContext);
        }
    }

    private byte normalize(float val) {
        int sensitivity = PreferenceManager.getDefaultSharedPreferences(myContext).getInt("TOUCHPAD_SENSITIVITY", 50);
        float v = Math.min(val, MAX_VELOCITY);
        v = Math.max(v, -MAX_VELOCITY);
        float movementWithoutSensitivity = (v / MAX_VELOCITY) * -127;
        int movementWithSensitivity = (int) (movementWithoutSensitivity * (float)sensitivity/(float)100);
        //float movementWithoutSensitivity = Math.min(val, MAX_VELOCITY) / MAX_VELOCITY * -127;
        return (byte) movementWithSensitivity;//movementWithoutSensitivity * (float)sensitivity/(float)100);
    }

    private void updateLastPoint(MotionEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }
}
