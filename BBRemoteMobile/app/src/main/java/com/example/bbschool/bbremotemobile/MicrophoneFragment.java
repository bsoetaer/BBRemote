package com.example.bbschool.bbremotemobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Fragment for the microphone input mode.
 * Currently only a button.
 * 3.2.9.2.1. Send Sound
 * Created by Braeden on 3/13/2016.
 */
public class MicrophoneFragment extends Fragment implements View.OnClickListener {

    boolean cameraOn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_microphone, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupListeners();
    }

    private void setupListeners() {
        ImageButton btn = (ImageButton) getView().findViewById(R.id.mic_toggle);
        btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        ImageButton btn = (ImageButton) view;
        if (cameraOn) {
            cameraOn = false;
            btn.setImageResource(R.drawable.ic_mic_off_black_48dp);
            btn.setBackgroundResource(R.drawable.media_button_unpressed);
        } else {
            cameraOn = true;
            btn.setImageResource(R.drawable.ic_mic_black_48dp);
            btn.setBackgroundResource(R.drawable.media_button_pressed);
        }
        //Turn mic on/off here
    }
}
