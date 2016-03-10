package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Braeden on 3/8/2016.
 */
public class KeyListener implements KeyboardView.OnKeyboardActionListener {
    int shift = 0;
    HashSet<Integer> toggledKeys = new HashSet<Integer>();
    HashSet<Integer> toggleableKeys = new HashSet<>();
    Keyboard myKeyboard;
    KeyboardViewFix myKeyboardView;
    Context myContext;

    public KeyListener(KeyboardViewFix myKeyboardView, Context context) {
        this.myKeyboardView = myKeyboardView;
        this.myContext = context;
        myKeyboard = new Keyboard(myContext, R.xml.kbd1);
        myKeyboardView.setKeyboard( myKeyboard );
        toggleableKeys.add(R.integer.key_shift);
        toggleableKeys.add(R.integer.key_ctrl);
        toggleableKeys.add(R.integer.key_alt);
        toggleableKeys.add(R.integer.key_win);
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
    }

    @Override
    public void onPress(int arg0) {
        if (arg0 == R.integer.key_shift) {
            shift = (shift + 1) % 3;
            myKeyboard.setShifted(!(shift == 0));
            if (shift != 2)
                toggleKey(arg0);
        }
        else if (toggleableKeys.contains(arg0)) {
            toggleKey(arg0);
        }
        //Send key down here
    }

    @Override public void onRelease(int primaryCode) {

    }

    @Override public void onText(CharSequence text) {
    }

    @Override public void swipeDown() {
    }

    @Override public void swipeLeft() {
    }

    @Override public void swipeRight() {
    }

    @Override public void swipeUp() {
    }

    private void toggleKey(int keyCode)
    {
        if(toggledKeys.contains(keyCode)){
            toggledKeys.remove(keyCode);
        }
        else {
            toggledKeys.add(keyCode);
        }
    }

}
