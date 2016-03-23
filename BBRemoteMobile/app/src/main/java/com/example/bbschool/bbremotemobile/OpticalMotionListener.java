package com.example.bbschool.bbremotemobile;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Created by Braeden on 3/13/2016.
 */
public class OpticalMotionListener implements SensorEventListener {

    //TODO evaluate validity of threshold with other devices
    private static final float velocityThreshold = 0.002f; // 2 mm/s
    private static final float accelerationThreshold = 0.1f; // 5 cm/s^2
    private static final float distanceThreshold = 0.0001f; // 1/10 mm
    private int[] noChangeCounter = {0,0,0};
    float[] oldA = {0,0,0};
    float[] oldV = {0,0,0};
    long oldTime = 0;

    //TODO use right coords based on orientation, maybe this is okay already due to fixed orientation?
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] newV = new float[3];
        float[] newD = new float[3];

        if (oldTime == 0)
            //Start with base delay of 20 ms. Best guess based off SENSOR_DELAY_GAME
            oldTime = event.timestamp - (20 * 1000000);
        float interval = (event.timestamp - oldTime) / 1000000000f;
        // Calculate current velocity and distance based off acceleration. Use of thresholds and
        // no change counters are for dealing with the inaccuracies of the accelerometer and the fact
        // that it reports motion even when it is stationary
        for (int i = 0; i < newV.length; i++) {
            if ( event.values[i] > accelerationThreshold) { //phone is accelerating
                noChangeCounter[i] = 0;
                newV[i] = oldV[i] + ((event.values[i] - oldA[i]) * interval);
                newD[i] = oldV[i] * interval + 0.5f * (event.values[i] - oldA[i]) * interval * interval;
            } else {
                noChangeCounter[i]++;
                if (noChangeCounter[i] > 10) { //no change for 200 ms, assume we are stationary
                    noChangeCounter[i] = 0;
                    newV[i] = 0;
                    newD[i] = 0;
                } else { // continue moving at same velocity as before
                    newV[i] = oldV[i];
                    newD[i] = oldV[i] * interval;
                }
            }
        }
        sendMovement(event, newV, newD);
        updateOldValues(event, newV);
    }

    private void sendMovement(SensorEvent event, float[] newV, float[] newD) {
        if( Math.abs(newV[2]) < velocityThreshold) { // Phone not moving in z plane so it is on surface (or held very steadily)
            for( int i = 0; i < newD.length; i++) {
                if (Math.abs(newD[i]) < distanceThreshold )
                    newD[i] = 0;
            }
            //TODO Call MotionBluetoothTransmitter here with distances moved
            //TODO Remove below code that is for testing only
            if (Math.abs(newD[0]) > distanceThreshold) {
                Log.d("Optical Move", "Acceleration: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
                Log.d("Optical Move", "Velocity: " + newV[0] + ", " + newV[1] + ", " + newV[2]);
                Log.d("Optical Move", "Distance: " + newD[0] + ", " + newD[1] + ", " + newD[2]);
            }
        }
    }

    private void updateOldValues(SensorEvent event, float[] newV) {
        oldA = event.values.clone();
        oldV = newV;
        reset();
        oldTime = event.timestamp;
    }

    private void reset() {
        for( int i = 0; i < oldV.length; i++) {
            if (Math.abs(oldV[i]) < velocityThreshold )
                oldV[i] = 0;
        }
        //for( int i = 0; i < oldA.length; i++) {
        //    if (Math.abs(oldA[i]) < accelerationThreshold )
        //        oldA[i] = 0;
        //}
    }
}
