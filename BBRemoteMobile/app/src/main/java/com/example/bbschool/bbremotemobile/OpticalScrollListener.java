package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Listener to listen to scroll events for the optical mouse on a button.
 * 3.2.5.2.6. Scrolling
 * Created by Braeden on 3/13/2016.
 */
public class OpticalScrollListener implements Button.OnTouchListener {
    Context myContext;
    private boolean mouseDown = false;
    private float lastX = 0;
    private float lastY = 0;
    private boolean isScroll = false;
    private static final String TAG = "OpticalScrollListener";
    public static final float maxScroll = 100;

    public OpticalScrollListener(Context myContext) {
        this.myContext =  myContext;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    scrollDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mouseMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                    scrollUp(event);
                    break;
            }
        return true;
    }

    private void scrollDown(MotionEvent event) {
        try {
            isScroll = true;
            ButtonBluetoothTransmitter.sendSingleKeyDown(MouseButton.MIDDLE.getVal());
            updateLastPoint(event);
        } catch (IOException e) {
            Log.w(TAG, "Failed to send middle mouse click: " + e.getMessage());
        }
    }

    private void scrollUp(MotionEvent event) {
        try {
            isScroll = false;
            ButtonBluetoothTransmitter.sendSingleKeyUp(MouseButton.MIDDLE.getVal());
            BluetoothAxisTransmitter.sendSingleMovement(MouseAxis.SCROLL.getVal(), (byte)0);
            updateLastPoint(event);
        } catch (IOException e) {
            Log.w(TAG, "Failed to send middle mouse unclick: " + e.getMessage());
        }
    }

    private void mouseMove(MotionEvent event) {
        float deltaX = lastX - event.getX();
        float deltaY = lastY - event.getY();
        updateLastPoint(event);
        byte scrollVal = normalize(deltaY);
        try {
            BluetoothAxisTransmitter.sendSingleMovement(MouseAxis.SCROLL.getVal(), scrollVal);
        } catch (IOException e) {
            Log.w(TAG, "Failed to send scroll: " + scrollVal + ": " + e.getMessage());
        }
    }

    private byte normalize(float val) {
        return (byte)((int)Math.min(val, maxScroll) / maxScroll * 127);
    }

    private void updateLastPoint(MotionEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }
}
