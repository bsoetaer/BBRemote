package com.example.bbschool.bbremotemobile;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Braeden on 3/13/2016.
 */
public class BBButtonListener implements Button.OnTouchListener {

    private int keyCode;

    public BBButtonListener(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
