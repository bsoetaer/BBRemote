package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
        isScroll = false;
        updateLastPoint(event);
    }

    private void mouseMove(MotionEvent event) {
        float deltaX = lastX - event.getX();
        float deltaY = lastY - event.getY();
        // TODO Call MotionBluetoothTransmitter with deltaX and deltaY
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

    }

    private void sendCursorMovementData(float deltaX, float deltaY) {
        Map<Integer,Byte> normalizedMovement = new HashMap<>();
        byte normalizedX = normalize(deltaX);
        byte normalizedY = normalize(deltaY);
        normalizedMovement.put(MouseAxis.X.getVal(), normalizedX);
        normalizedMovement.put(MouseAxis.Y.getVal(), normalizedY);
        Log.w(TAG, "movement: x: " + normalizedX + ", y: " + normalizedY);
        try {
            BluetoothAxisTransmitter.sendMovement(normalizedMovement);
        } catch (IOException e) {
            Log.w(TAG, "Failed touchpad mouse movement (X: " + deltaX + ", Y: " + deltaY + "). " + e.getMessage());
            ModeChanger.disconnected(myContext);
        }
    }

    private byte normalize(float val) {
        return (byte)((int)Math.min(val, MAX_VELOCITY) / MAX_VELOCITY * -127);
    }

    private void updateLastPoint(MotionEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }
}
