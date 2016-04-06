package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Braeden on 3/13/2016.
 */
public class OpticalMotionListener implements SensorEventListener {

    //TODO evaluate validity of threshold with other devices
    private static final float[] noInput = {0,0};
    private static final float[] velocityThreshold = {0.001f, 0.001f, 0.001f}; // 2 mm/s
    private static final float[] accelerationThreshold = {0.1f, 0.1f, 0.1f};; // 5 cm/s^2
    private static final float maxMovementVelocity = 1f; // 3 cm/s
    private static final String TAG = "OpticalMotionListener";
    private int[] noChangeCounter = {0,0,0};
    float[] oldA = {0,0,0};
    float[] oldV = {0,0,0};
    long oldTime = 0;
    private boolean currentlyMoving = false;
    Context myContext;

    public OpticalMotionListener(Context myContext) {
        this.myContext =  myContext;
    }

    //TODO use right coords based on orientation, maybe this is okay already due to fixed orientation?
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] newV = new float[3];

        if (oldTime == 0) {
            oldTime = event.timestamp;
            return;
        }
        float interval = (event.timestamp - oldTime) / 1000000000f;
        // Calculate current velocity and distance based off acceleration. Use of thresholds and
        // no change counters are for dealing with the inaccuracies of the accelerometer and the fact
        // that it reports motion even when it is stationary
        for (int i = 0; i < newV.length; i++) {
            newV[i] = oldV[i] + (((event.values[i] + oldA[i])/2) * interval);
            if((Math.abs(newV[i]) < velocityThreshold[i]) || (oldV[i] * newV[i] < 0) )
                newV[i] = 0;
            if ( Math.abs(event.values[i]) > accelerationThreshold[i]) { //phone is accelerating
                noChangeCounter[i] = 0;
            } else {
                noChangeCounter[i]++;
                if (noChangeCounter[i] > 5) { //no change for 1 s, assume we are stationary
                    noChangeCounter[i] = 0;
                    newV[i] = 0;
                }
            }
        }
        sendMovement(event, newV);
        updateOldValues(event, newV);
    }

    private void sendMovement(SensorEvent event, float[] newV) {
        if( Math.abs(newV[2]) < velocityThreshold[2]) { // Phone not moving in z plane so it is on surface (or held very steadily)
            if (Math.abs(newV[0]) > velocityThreshold[0] || Math.abs(newV[1]) > velocityThreshold[1]) {
                Log.d(TAG, "Acceleration: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
                Log.d(TAG, "Velocity: " + newV[0] + ", " + newV[1] + ", " + newV[2]);
                currentlyMoving = true;
                sendCursorMovementData(newV);
            } else if (currentlyMoving) {
                Log.d(TAG, "A2: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);
                Log.d(TAG, "V2: " + newV[0] + ", " + newV[1] + ", " + newV[2]);
                currentlyMoving = false;
                sendCursorMovementData(noInput);
            }
        }
    }

    private void sendCursorMovementData(float[] velocity) {
        Map<Integer,Byte> normalizedMovement = new HashMap<>();
        byte normalizedX = normalize(velocity[0]);
        byte normalizedY = normalize(-velocity[1]);
        normalizedMovement.put(MouseAxis.X.getVal(), normalizedX);
        normalizedMovement.put(MouseAxis.Y.getVal(), normalizedY);
            Log.w(TAG, "movement: x: " + velocity[0] + ", y: " + velocity[1]);
            Log.w(TAG, "NormalizedMovement: x: " + normalizedX + ", y: " + normalizedY);
        try {
            BluetoothAxisTransmitter.sendMovement(normalizedMovement);
        } catch (IOException e) {
            Log.w(TAG, "Failed to send movement: x: " + normalizedX + ", y: " + normalizedY + ": " + e.getMessage());
        }
    }

    private byte normalize(float val) {
        int sensitivity = PreferenceManager.getDefaultSharedPreferences(myContext).getInt("OPTICAL_SENSITIVITY", 50);
        float v = Math.min(val, maxMovementVelocity);
        v = Math.max(v, -maxMovementVelocity);
        float movementWithoutSensitivity = (v / maxMovementVelocity) * 127;
        int movementWithSensitivity = (int) (movementWithoutSensitivity * (float)sensitivity/(float)100);
        if (movementWithoutSensitivity > 0 && movementWithSensitivity == 0)
            movementWithSensitivity = 1;
        if (movementWithoutSensitivity < 0 && movementWithSensitivity == 0)
            movementWithSensitivity = -1;
        return (byte)(movementWithSensitivity);
    }

    private void updateOldValues(SensorEvent event, float[] newV) {
        oldA = event.values.clone();
        oldV = newV;
        oldTime = event.timestamp;
    }
}
