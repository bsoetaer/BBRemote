package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Braeden on 3/27/2016.
 */
public class CustomizeLayoutFragment extends Fragment {

    private OnSelectInputListener mListener;

    public interface OnSelectInputListener {
        public void onSelectInput();
    }

    private GamepadLayout gamepadLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamepadLayout = new GamepadLayout(getContext());
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.fragment_customize_layout, container, false);
        clearLayoutParent();
        v.addView(gamepadLayout);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.customize_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( item.getItemId() == R.id.action_add_input ){
            mListener.onSelectInput();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSelectInputListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnInputSelectedListener.");
        }
    }

    public void addInput(GamepadInputView input) {
        gamepadLayout.addGamepadInput(input);
    }

    public void removeInput(GamepadInputView input) {
        gamepadLayout.removeGamepadInput(input);
    }

    public void changeGamepadLayout(GamepadLayout layout) {
        RelativeLayout v = (RelativeLayout) getView();
        v.removeAllViews();
        this.gamepadLayout = layout;
        clearLayoutParent();
        v.addView(layout);
    }

    private void clearLayoutParent() {
        if (gamepadLayout.getParent() != null)
            ((RelativeLayout) gamepadLayout.getParent()).removeAllViews();
    }

}
