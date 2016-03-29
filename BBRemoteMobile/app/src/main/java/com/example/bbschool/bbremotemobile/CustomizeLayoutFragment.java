package com.example.bbschool.bbremotemobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Braeden on 3/27/2016.
 */
public class CustomizeLayoutFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.fragment_customize_layout, container, false);
        v.addView(new GamepadInputView(getContext(), GamepadInput.A_BUTTON));
        return v;
    }

}
