package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Braeden on 3/13/2016.
 */
public class OpticalScrollListener implements Button.OnTouchListener {
    Context myContext;
    private boolean mouseDown = false;
    private float lastX = 0;
    private float lastY = 0;
    private boolean isScroll = false;

    public OpticalScrollListener(Context myContext) {
        this.myContext =  myContext;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

        switch(action) {
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
        isScroll = true;
        updateLastPoint(event);
    }

    private void scrollUp(MotionEvent event) {
        isScroll = false;
        updateLastPoint(event);
    }

    private void mouseMove(MotionEvent event) {
        float deltaX = lastX - event.getX();
        float deltaY = lastY - event.getY();
        // TODO Call MotionBluetoothTransmitter with deltaX and deltaY for scroll
        updateLastPoint(event);
    }

    private void updateLastPoint(MotionEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }
}
