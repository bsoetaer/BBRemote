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

    public void changeInputMode(Mode mode) {
        switch (mode) {
            case Keyboard:
                swapInputMode(KeyboardFragment.class, mode);
                break;
            case Touchpad:
                //TODO
                break;
            case Optical:
                //TODO
                break;
            case Camera:
                //TODO
                break;
            case Mic:
                //TODO
                break;
            case Gamepad:
                //TODO
                break;
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

    private void swapInputMode(Class newFragmentclass, Mode mode) {
        //TODO Notify windows of mode change
        if (currentActivity.getClass() == InputActivity.class) {
            try {
                Constructor<? extends Runnable> ctor = newFragmentclass.getConstructor();
                Fragment newFragment = (Fragment) ctor.newInstance();
                ((InputActivity) currentActivity).swapFragments(newFragment);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            Intent intent = new Intent(currentActivity, InputActivity.class);
            intent.putExtra("Mode", mode);
            currentActivity.startActivity(intent);
        }
    }

    public void changeActivity(Class newActivityClass) {
        //TODO Notify windows if we are no longer on an input mode
        if (currentActivity.getClass() != newActivityClass) {
            Intent intent = new Intent(currentActivity, newActivityClass);
            currentActivity.startActivity(intent);
        }
    }
}
