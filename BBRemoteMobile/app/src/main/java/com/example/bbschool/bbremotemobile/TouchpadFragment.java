package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bbschool.bbremotemobile.R;


public class TouchpadFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_touchpad, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupListeners();
    }

    // Setup listeners for 2 mouse buttons and touch interactions
    private void setupListeners() {
        Button leftClick = (Button) getView().findViewById(R.id.touchpad_left_click);
        leftClick.setOnTouchListener(new BBButtonListener(AndroidKeyCodes.lookupCode("LEFT CLICK"), getContext()));
        Button rightClick = (Button) getView().findViewById(R.id.touchpad_right_click);
        rightClick.setOnTouchListener(new BBButtonListener(AndroidKeyCodes.lookupCode("RIGHT CLICK"), getContext()));
        getView().setOnTouchListener(new TouchpadTouchListener(getContext()));
    }
}
