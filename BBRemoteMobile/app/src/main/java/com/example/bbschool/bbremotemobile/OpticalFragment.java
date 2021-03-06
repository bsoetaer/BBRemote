package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragment for containing the optical mouse input mode.
 * 3.2.5. Optical Mouse Input
 * and all sub requirements
 * Created by Braeden on 3/13/2016.
 */
public class OpticalFragment extends Fragment {

    private SensorManager sensorManager;
    private OpticalMotionListener motionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_optical, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mouse, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //TODO only show default mouse
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_keyboard_quick:
                new ModeChanger(getActivity()).changeInputMode(Mode.Keyboard);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if( getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupListeners();
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(motionListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    // Setup listeners for 3 mouse buttons, scroll and motion sensors
    private void setupListeners() {
        Button leftClick = (Button) getView().findViewById(R.id.optical_left_click);
        leftClick.setOnTouchListener(new BBButtonListener(MouseButton.LEFT.getVal(), getContext()));
        Button rightClick = (Button) getView().findViewById(R.id.optical_right_click);
        rightClick.setOnTouchListener(new BBButtonListener(MouseButton.RIGHT.getVal(), getContext()));
        Button middleClick = (Button) getView().findViewById(R.id.optical_middle_click);
        //TODO verfiy this works when doing functionality (2 ontouch listeners on the same button)
        middleClick.setOnTouchListener(new BBButtonListener(MouseButton.MIDDLE.getVal(), getContext()));
        middleClick.setOnTouchListener(new OpticalScrollListener(getContext()));
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        motionListener = new OpticalMotionListener(getContext());
        sensorManager.registerListener(motionListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_GAME);
    }
}
