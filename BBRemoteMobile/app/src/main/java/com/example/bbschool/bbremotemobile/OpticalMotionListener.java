package com.example.bbschool.bbremotemobile;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Braeden on 3/13/2016.
 */
public class OpticalMotionListener implements SensorEventListener {

    //TODO use right coords based on orientation
    //TODO Ensure device has accelerometer or else disable optical mouse mode
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //TODO Need to track last position, then double integrate to get distance moved since last position
    }
}
