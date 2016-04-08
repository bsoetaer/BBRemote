package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bbschool.bbremotemobile.R;

/**
 * Fragment for the touchpad input mode.
 * 3.2.4. Touchscreen Mouse Input
 */
public class TouchpadFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_touchpad, container, false);
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
        setupListeners();
    }

    // Setup listeners for 2 mouse buttons and touch interactions
    private void setupListeners() {
        Button leftClick = (Button) getView().findViewById(R.id.touchpad_left_click);
        leftClick.setOnTouchListener(new BBButtonListener(MouseButton.LEFT.getVal(), getContext()));
        Button rightClick = (Button) getView().findViewById(R.id.touchpad_right_click);
        rightClick.setOnTouchListener(new BBButtonListener(MouseButton.RIGHT.getVal(), getContext()));
        getView().setOnTouchListener(new TouchpadTouchListener(getContext()));
    }
}
