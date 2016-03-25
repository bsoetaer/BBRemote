package com.example.bbschool.bbremotemobile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class KeyboardFragment extends Fragment {

    KeyboardViewFix myKeyboardView = null;
    KeyListener myKeyListener = null;

    public KeyboardFragment() {
        KeyboardViewFix.inEditMode = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        KeyboardViewFix myKeyboardView = (KeyboardViewFix) getView().findViewById(R.id.keyboard_view);
        if (myKeyListener == null) {
            myKeyListener = new KeyListener(myKeyboardView, getContext());
        }
        else {
            myKeyListener.setKeyboardView(myKeyboardView, getContext());
            myKeyListener.loadKeyboard();
        }
        myKeyboardView.setOnKeyboardActionListener(myKeyListener);
    }
}
