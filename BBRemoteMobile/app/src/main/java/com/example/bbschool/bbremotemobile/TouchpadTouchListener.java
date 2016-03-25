package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Braeden on 3/13/2016.
 */
public class TouchpadTouchListener implements View.OnTouchListener {
    Context myContext;
    private boolean mouseDown = false;
    private float lastX = 0;
    private float lastY = 0;
    private boolean isScroll = false;

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
    }

    private void scrollUp(MotionEvent event) {
        isScroll = false;
        updateLastPoint(event);
    }

    private void mouseMove(MotionEvent event) {
        float deltaX = lastX - event.getX();
        float deltaY = lastY - event.getY();
        // TODO Call MotionBluetoothTransmitter with deltaX and deltaY
        if( isScroll ) {
        } else {
        }
        updateLastPoint(event);
    }

    private void updateLastPoint(MotionEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }
}
