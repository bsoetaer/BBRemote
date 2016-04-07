package com.example.bbschool.bbremotemobile;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Camera input mode for sending video/audio device as a webcam.
 * This class is not fully implemented and is only a button currently. TODO update this line when functional
 * 3.2.10. Camera Input
 * 3.2.10.2.1. Send Video
 * 3.2.10.2.2. Send Audio
 * Created by Braeden on 3/13/2016.
 */
public class CameraFragment extends Fragment implements View.OnClickListener {

    boolean cameraOn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupListeners();
    }

    private void setupListeners() {
        ImageButton btn = (ImageButton) getView().findViewById(R.id.camera_toggle);
        btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        ImageButton btn = (ImageButton) view;
        if (cameraOn) {
            cameraOn = false;
            btn.setImageResource(R.drawable.ic_videocam_off_black_48dp);
            btn.setBackgroundResource(R.drawable.media_button_unpressed);
        } else {
            cameraOn = true;
            btn.setImageResource(R.drawable.ic_videocam_black_48dp);
            btn.setBackgroundResource(R.drawable.media_button_pressed);
        }
        //Turn camera on/off here
    }
}
