package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Main menu for the app for general navigation and hiding menu items.
 * 3.2.2. Select Input Mode
 * and all sub requirements
 * Created by Braeden on 3/10/2016.
 */
public class MainMenu {
    Activity currentActivity;
    ModeChanger modeChanger;

    public MainMenu(Activity currentActivity) {
        this.currentActivity = currentActivity;
        modeChanger = new ModeChanger(currentActivity);
    }

    public void hideModes(Menu menu) {
        if ( !ConnectionState.isConnected()) {
            menu.findItem(R.id.input_modes).setVisible(false);
            menu.findItem(R.id.dual_modes).setVisible(false);
        }
        SensorManager mSensorManager = (SensorManager) currentActivity.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null){
            menu.findItem(R.id.action_optical).setVisible(false);
        }
    }

    public boolean menuItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_connection:
                modeChanger.changeActivity(ConnectionActivity.class);
                return true;
            case R.id.action_keyboard:
                modeChanger.changeInputMode(Mode.Keyboard);
                return true;
            case R.id.action_touchpad:
                modeChanger.changeInputMode(Mode.Touchpad);
                return true;
            case R.id.action_optical:
                modeChanger.changeInputMode(Mode.Optical);
                return true;
            case R.id.action_camera:
                modeChanger.changeInputMode(Mode.Camera);
                return true;
            case R.id.action_mic:
                modeChanger.changeInputMode(Mode.Mic);
                return true;
            case R.id.action_gamepad:
                modeChanger.changeInputMode(Mode.Gamepad);
                return true;
            case R.id.action_dual_camera:
                dualCamera(item);
                return true;
            case R.id.action_dual_mic:
                dualMic(item);
                return true;
            case R.id.action_dual_none:
                dualNone(item);
                return true;
            case R.id.action_customize:
                modeChanger.changeActivity(CustomizeGamepadActivity.class);
                return true;
            case R.id.action_settings:
                modeChanger.changeActivity(SettingsActivity.class);
                return true;
        }
        return false;
    }

    private void dualCamera(MenuItem item) {
        if (!item.isChecked()) {
            item.setChecked(true);
            modeChanger.disableDualMic();
            modeChanger.enableDualCamera();
        }
    }

    private void dualMic(MenuItem item) {
        if (!item.isChecked()) {
            item.setChecked(true);
            modeChanger.disableDualCamera();
            modeChanger.enableDualMic();
        }
    }

    private void dualNone(MenuItem item) {
        if (!item.isChecked()) {
            item.setChecked(true);
            modeChanger.disableDualCamera();
            modeChanger.disableDualMic();
        }
    }
}
