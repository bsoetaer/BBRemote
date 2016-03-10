package com.example.bbschool.bbremotemobile;

import android.inputmethodservice.Keyboard;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class KeyboardFragment extends Fragment {

    private final ArrayList<Integer> keyboardXml = new ArrayList<Integer>();

    public KeyboardFragment() {
        KeyboardViewFix.inEditMode = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        KeyboardViewFix myKeyboardView= (KeyboardViewFix)getView().findViewById(R.id.keyboard_view);
        myKeyboardView.setOnKeyboardActionListener(new KeyListener(myKeyboardView, getContext()));
    }
}
