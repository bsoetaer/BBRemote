package com.example.bbschool.bbremotemobile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class KeyboardFragment extends Fragment {

    KeyListener myKeyListener = null;

    public KeyboardFragment() {
        KeyboardViewFix.inEditMode = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_keyboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //TODO only show default mouse
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_touchpad_quick:
                new ModeChanger(getActivity()).changeInputMode(Mode.Touchpad);
                return true;
            case R.id.action_optical_quick:
                new ModeChanger(getActivity()).changeInputMode(Mode.Optical);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
