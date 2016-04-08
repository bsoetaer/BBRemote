package com.example.bbschool.bbremotemobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * Created by Braeden on 3/8/2016.
 * Workaround for known bug with request to get audio service from custom keyboard.
 * 3.2.3. Touchscreen Keyboard Input
 * and sub requirements
 */
public class KeyboardViewFix extends KeyboardView {
    public static boolean inEditMode = true;

    @TargetApi(21) // Build.VERSION_CODES.L
    public KeyboardViewFix(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(new ContextWrapperFix(context, inEditMode), attrs, defStyleAttr, defStyleRes);
    }

    public KeyboardViewFix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(new ContextWrapperFix(context, inEditMode), attrs, defStyleAttr);
    }

    public KeyboardViewFix(Context context, AttributeSet attrs) {
        super(new ContextWrapperFix(context, inEditMode), attrs);
    }
}
