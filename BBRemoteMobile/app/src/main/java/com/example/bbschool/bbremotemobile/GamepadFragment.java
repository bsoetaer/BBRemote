package com.example.bbschool.bbremotemobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import java.io.File;

/**
 * Created by Braeden-MSI on 3/31/2016.
 */
public class GamepadFragment extends Fragment {

    private GamepadLayout gamepadLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String initialLayout = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("DEFAULT_GAMEPAD_LAYOUT", "DefaultSimple");
        gamepadLayout = XMLParser.load(getContext(), initialLayout);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.fragment_gamepad, container, false);
        changeGamepadLayout(gamepadLayout, v);
        setOrientation(gamepadLayout.getPortrait());
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gamepad_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_load_layout:
                loadDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    public void changeGamepadLayout(GamepadLayout layout) {
        RelativeLayout v = (RelativeLayout) getView();
        changeGamepadLayout(layout, v);
    }

    public void changeGamepadLayout(GamepadLayout layout, RelativeLayout v) {
        v.removeAllViews();
        this.gamepadLayout = layout;
        clearLayoutParent();
        setOrientation(gamepadLayout.getPortrait());
        v.addView(layout);
        for (GamepadInputView input : gamepadLayout.getGamepadInputs()) {
            input.setEditable(false);
            setupListener(input);
        }
    }

    private void setOrientation(boolean portrait) {
        gamepadLayout.setPortrait(portrait);
        if(portrait) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void setupListener(GamepadInputView input) {
        GamepadInput inputType = input.getGamepadInput();
        if (inputType == GamepadInput.LEFT_STICK || inputType == GamepadInput.RIGHT_STICK) {
            input.setOnTouchListener(new GamepadAxisListener(getContext(), inputType));
        } else if (inputType == GamepadInput.DOWN_DPAD || inputType == GamepadInput.UP_DPAD ||
                inputType == GamepadInput.LEFT_DPAD || inputType == GamepadInput.RIGHT_DPAD) {
            input.setOnTouchListener(new GamepadDpadListener(getContext()));
        } else {
            input.setOnTouchListener(new BBButtonListener(inputType.getVal(), getContext()));
        }

    }

    private void clearLayoutParent() {
        if (gamepadLayout.getParent() != null)
            ((RelativeLayout) gamepadLayout.getParent()).removeAllViews();
    }

    private void loadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Load Layout");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        for (String name : XMLParser.getSavedLayouts(getContext())) {
            arrayAdapter.add(name);
        }
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String layoutName = arrayAdapter.getItem(which);
                GamepadLayout newLayout = XMLParser.load(getContext(), layoutName);
                changeGamepadLayout(newLayout);
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
