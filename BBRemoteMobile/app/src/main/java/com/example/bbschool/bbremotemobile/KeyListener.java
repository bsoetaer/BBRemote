package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Listener for the custom keyboard. Needed for:
 * 3.2.3.2.1. Send Key Input
 * 3.2.3.2.3. Key Size
 * 3.2.3.2.4. Multiple Key Press
 * 3.2.3.2.5. Essential Extra Keys Display
 * 3.2.3.2.6. Non-essential Extra Keys Display
 * Created by Braeden on 3/8/2016.
 */
public class KeyListener implements KeyboardView.OnKeyboardActionListener {
    private static final String TAG = "KeyListener";
    HashSet<Integer> toggledKeys = new HashSet<Integer>();
    HashSet<Integer> toggleableKeys = new HashSet<>();
    Keyboard myKeyboard;
    KeyboardViewFix myKeyboardView;
    Context myContext;
    int[] KeyboardModes = {R.xml.kbd_qwerty, R.xml.kbd_sym, R.xml.kbd_extra};
    int currentMode = 0;

    public KeyListener(KeyboardViewFix myKeyboardView, Context context) {
        setKeyboardView(myKeyboardView, context);
        toggleableKeys.add(AndroidKeyCodes.lookupCode("SHIFT"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("CTRL"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("ALT"));
        toggleableKeys.add(AndroidKeyCodes.lookupCode("WIN"));
        //toggleableKeys.add(AndroidKeyCodes.lookupCode("CAPS"));
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
                sendToggledKeys(true);
            } else {
                // Send toggle key up here for new key and down for all other toggled keys
                sendOneKey(arg0, false);
                sendToggledKeys(true);
            }
            updateShiftState();
        }
        else if (arg0 != AndroidKeyCodes.lookupCode("CHANGE MODE")) {
             //Send key down here for new key and all toggled keys
             removeShiftForSpecialShiftKeys(arg0);
             sendToggledKeys(true);
             sendOneKey(arg0, true);
        }
    }

    private void removeShiftForSpecialShiftKeys(int keyCode) {
        if (AndroidKeyCodes.isSpecialNoShiftKey(keyCode)) {
            toggledKeys.remove(AndroidKeyCodes.lookupCode("SHIFT"));
            sendOneKey(AndroidKeyCodes.lookupCode("SHIFT"), false);
        }
        else if (AndroidKeyCodes.isSpecialShiftKey(keyCode)) {
            toggledKeys.add(AndroidKeyCodes.lookupCode("SHIFT"));
            sendOneKey(AndroidKeyCodes.lookupCode("SHIFT"), true);
        }
    }

    @Override public void onRelease(int primaryCode) {
        if (primaryCode == AndroidKeyCodes.lookupCode("CHANGE MODE")) {
            currentMode = ((currentMode + 1) % 3);
            loadKeyboard();
        }
        else if (!toggleableKeys.contains(primaryCode)) {
            // Send key up here for key and all toggled keys
            sendOneKey(primaryCode, false);
            sendToggledKeys(false);
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

    public void setKeyboardView(KeyboardViewFix myKeyboardView, Context context) {
        this.myKeyboardView = myKeyboardView;
        this.myContext = context;
    }

    public void loadKeyboard() {
        int newMode = KeyboardModes[currentMode];
        myKeyboard = new Keyboard(myContext, newMode);
        myKeyboardView.setKeyboard(myKeyboard);
        myKeyboardView.setPreviewEnabled(false);
        setToggledKeys();
        updateShiftState();
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
        boolean capsOn = toggledKeys.contains(AndroidKeyCodes.lookupCode("CAPS"));
        for (Keyboard.Key key : myKeyboard.getKeys()) {
            if (key.codes[0] != AndroidKeyCodes.lookupCode("CAPS")) {
                key.on = false;
            }
        }
        toggledKeys.clear();
        if (capsOn)
            toggledKeys.add(AndroidKeyCodes.lookupCode("CAPS"));
    }

    private void updateShiftState() {
        // Shift mode depends on both shift and caps
        myKeyboard.setShifted(toggledKeys.contains(AndroidKeyCodes.lookupCode("SHIFT")) ^ toggledKeys.contains(AndroidKeyCodes.lookupCode("CAPS")));
        myKeyboardView.invalidateAllKeys();
    }

    private void setToggledKeys() {
        for (Keyboard.Key key : myKeyboard.getKeys()) {
            if (toggledKeys.contains(key.codes[0])) {
                key.on = true;
            }
        }
    }

    private void sendToggledKeys(boolean pressed) {
        Map<Integer, Boolean> keyPresses = new HashMap<>();
        for(Integer code : toggledKeys) {
            keyPresses.put(code,pressed);
        }
        sendKeys(keyPresses);
    }

    private void sendOneKey(int keyCode, boolean pressed) {
        Map<Integer, Boolean> keyPresses = new HashMap<>();
        keyPresses.put(keyCode,pressed);
        sendKeys(keyPresses);
    }

    private void sendKeys(Map<Integer, Boolean> keyPresses) {
        try {
            ButtonBluetoothTransmitter.sendKeys(remapToHID(keyPresses));
        }
        catch (IOException e) {
            Log.w(TAG, "Failed key press send: " + e.getMessage());
            ModeChanger.disconnected(myContext);
        }
    }

    private Map<Integer, Boolean> remapToHID(Map<Integer, Boolean> keyPresses) {
        Map<Integer, Boolean> remappedKeyPresses = new HashMap<>();
        for (Integer key : keyPresses.keySet())
            remappedKeyPresses.put(AndroidToWindowsKeyCodes.lookupCode(key), keyPresses.get(key));
        return remappedKeyPresses;
    }

}
