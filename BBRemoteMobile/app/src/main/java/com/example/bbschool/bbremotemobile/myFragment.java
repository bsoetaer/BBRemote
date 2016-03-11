package com.example.bbschool.bbremotemobile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Braeden on 3/11/2016.
 */
public class myFragment extends Fragment{
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        LinearLayout linLayout=
                new LinearLayout(getActivity());
        Button b = new Button(getActivity());
        b.setText("Hello Button");
        linLayout.addView(b);

        return linLayout;
    }
}
