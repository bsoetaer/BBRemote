package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

import javax.net.ssl.KeyStoreBuilderParameters;

/**
 * Created by Braeden on 3/10/2016.
 */
public class ModeChanger {
    private static final String TAG = "ModeChanger";
    Activity currentActivity;

    public ModeChanger(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    // Change input mode to selected mode
    public void changeInputMode(Mode mode) {
        changeWindowsMode(mode);
        if (currentActivity.getClass() == InputActivity.class) {
            ((InputActivity) currentActivity).swapFragments(mode);
        } else {
            Intent intent = new Intent(currentActivity, InputActivity.class);
            intent.putExtra("Mode", mode);
            currentActivity.startActivity(intent);
        }
    }

    public void enableDualMic() {

    }

    public void disableDualMic() {

    }

    public void enableDualCamera() {

    }

    public void disableDualCamera() {

    }

    public void changeActivity(Class newActivityClass) {
        if (currentActivity.getClass() != newActivityClass) {
            if (newActivityClass == InputActivity.class) {
                changeWindowsMode(Mode.Keyboard);
            } else {
                changeWindowsMode(Mode.None);
            }
            Intent intent = new Intent(currentActivity, newActivityClass);
            currentActivity.startActivity(intent);
        }
    }

    private void changeWindowsMode(Mode mode) {
        try {
            BluetoothModeChangeTransmitter.changeMode(mode);
        }
        catch (IOException e) {
            Log.w(TAG, "Failed mode change: " + e.getMessage());
            ModeChanger.disconnected(currentActivity);
        }
    }

    public static void disconnected(Context myContext) {
        // TODO ensure on reconnect that old mode registrations are wiped
        Toast toast = Toast.makeText(myContext, "Disconnected from desktop client.", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(myContext, ConnectionActivity.class);
        myContext.startActivity(intent);
    }
}
