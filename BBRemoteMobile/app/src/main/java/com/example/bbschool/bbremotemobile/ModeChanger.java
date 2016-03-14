package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.reflect.Constructor;

import javax.net.ssl.KeyStoreBuilderParameters;

/**
 * Created by Braeden on 3/10/2016.
 */
public class ModeChanger {

    Activity currentActivity;

    public ModeChanger(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    // Change input mode to selected mode
    public void changeInputMode(Mode mode) {
        //TODO Notify windows of mode change
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
        //TODO Notify windows if we are no longer on an input mode
        if (currentActivity.getClass() != newActivityClass) {
            Intent intent = new Intent(currentActivity, newActivityClass);
            currentActivity.startActivity(intent);
        }
    }
}
