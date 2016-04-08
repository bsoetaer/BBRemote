package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Fragment to select a new gamepad input to add to a gamepad layout.
 * 3.2.8.2.3. Add Gamepad Input
 * Created by Braeden on 3/30/2016.
 */
public class SelectInputFragment extends Fragment implements ListView.OnItemClickListener{

    OnInputSelectedListener mListener;

    public interface OnInputSelectedListener {
        public void onInputSelected(GamepadInputView input);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_input, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnInputSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnInputSelectedListener.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<GamepadInput> inputs = new ArrayList<GamepadInput>(Arrays.asList(GamepadInput.values()));
        inputs.remove(GamepadInput.DOWN_DPAD);
        inputs.remove(GamepadInput.LEFT_DPAD);
        inputs.remove(GamepadInput.RIGHT_DPAD);
        GamepadInputAdapter adapter = new GamepadInputAdapter(getContext(), R.layout.input_item, inputs);
        ListView v = (ListView) getView().findViewById(R.id.gamepadInputList);
        v.setAdapter(adapter);
        v.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView adapter, View v, int position, long id) {
        // Do something when a list item is clicked
        GamepadInput input = (GamepadInput) adapter.getItemAtPosition(position);
        mListener.onInputSelected(new GamepadInputView(getContext(), input));
    }


}
