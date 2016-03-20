package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Braeden on 3/13/2016.
 */
public class OpticalFragment extends Fragment {

    private SensorManager sensorManager;
    private OpticalMotionListener motionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_optical, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(motionListener);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    // Setup listeners for 3 mouse buttons, scroll and motion sensors
    private void setupListeners() {
        Button leftClick = (Button) getView().findViewById(R.id.optical_left_click);
        leftClick.setOnTouchListener(new BBButtonListener(AndroidKeyCodes.lookupCode("LEFT CLICK"), getContext()));
        Button rightClick = (Button) getView().findViewById(R.id.optical_right_click);
        rightClick.setOnTouchListener(new BBButtonListener(AndroidKeyCodes.lookupCode("RIGHT CLICK"), getContext()));
        Button middleClick = (Button) getView().findViewById(R.id.optical_middle_click);
        //TODO verfiy this works when doing functionality (2 ontouch listeners on the same button)
        middleClick.setOnTouchListener(new BBButtonListener(AndroidKeyCodes.lookupCode("MIDDLE CLICK"), getContext()));
        middleClick.setOnTouchListener(new OpticalScrollListener(getContext()));
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        motionListener = new OpticalMotionListener();
        sensorManager.registerListener(motionListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }
}
