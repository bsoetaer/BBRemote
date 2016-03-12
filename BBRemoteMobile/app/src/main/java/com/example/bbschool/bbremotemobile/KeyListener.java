package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Braeden on 3/8/2016.
 */
public class KeyListener implements KeyboardView.OnKeyboardActionListener {
    HashSet<Integer> toggledKeys = new HashSet<Integer>();
    HashSet<Integer> toggleableKeys = new HashSet<>();
    Keyboard myKeyboard;
    KeyboardViewFix myKeyboardView;
    Context myContext;
    int[] KeyboardModes = {R.xml.kbd_qwerty, R.xml.kbd_sym, R.xml.kbd_extra};
    int currentMode = 0;

    public KeyListener(KeyboardViewFix myKeyboardView, Context context) {
        this.myKeyboardView = myKeyboardView;
        this.myContext = context;
        toggleableKeys.add(AndroidKeyCodes.lookupCode("SHIFT"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("CTRL"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("ALT"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("WIN"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("CAPS"));
        loadKeyboard();
    }
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
    }

    @Override
    public void onPress(int arg0) {
         if (toggleableKeys.contains(arg0)) {
            if (toggleKey(arg0)) {
                // Send key down here for new key and all other toggled keys
            } else {
                // Send toggle key up here for new key and down for all other toggled keys
            }
            updateShiftState();
        }
        else if (arg0 != AndroidKeyCodes.lookupCode("CHANGE MODE")) {
            //Send key down here for new key and all toggled keys
        }

    }

    @Override public void onRelease(int primaryCode) {
        if (primaryCode == AndroidKeyCodes.lookupCode("CHANGE MODE")) {
            currentMode = ((currentMode + 1) % 3);
            loadKeyboard();
        }
        else if (!toggleableKeys.contains(primaryCode)) {
            // Send key up here for key and all toggled keys
            clearToggledKeys();
            updateShiftState();
        }
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

    private boolean toggleKey(int keyCode)
    {
        if(toggledKeys.contains(keyCode)){
            toggledKeys.remove(keyCode);
            return false;
        }
        else {
            toggledKeys.add(keyCode);
            return true;
        }
    }

    // Clear all toggled keys except caps lock
    private void clearToggledKeys(){
        for (Keyboard.Key key : myKeyboard.getKeys()) {
            if (key.codes[0] != AndroidKeyCodes.lookupCode("CAPS")) {
                key.on = false;
            }
        }
    }

    private void updateShiftState() {
        // Shift mode depends on both shift and caps
        myKeyboard.setShifted(toggledKeys.contains(AndroidKeyCodes.lookupCode("SHIFT")) ^ toggledKeys.contains(AndroidKeyCodes.lookupCode("CAPS")));
        myKeyboardView.invalidateAllKeys();
    }

    private void loadKeyboard() {
        int newMode = KeyboardModes[currentMode];
        myKeyboard = new Keyboard(myContext, newMode);
        myKeyboardView.setKeyboard(myKeyboard);
        myKeyboardView.setPreviewEnabled(false);
        setToggledKeys();
        updateShiftState();
    }

    private void setToggledKeys() {
        for (Keyboard.Key key : myKeyboard.getKeys()) {
            if (toggledKeys.contains(key.codes[0])) {
                key.on = true;
            }
        }
    }

}
