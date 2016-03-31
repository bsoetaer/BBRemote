package com.example.bbschool.bbremotemobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.File;

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
        for (GamepadInputView input : gamepadLayout.getGamepadInputs()) {
            input.setOnTouchListener(new DeleteInputListener());
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.customize_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_input:
                mListener.onSelectInput();
                return true;
            case R.id.action_save_layout:
                saveDialog();
                return true;
            case R.id.action_load_layout:
                loadDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        for (GamepadInputView input : gamepadLayout.getGamepadInputs()) {
            input.setOnTouchListener(new DeleteInputListener());
            input.setEditable(true);
        }
    }

    private void clearLayoutParent() {
        if (gamepadLayout.getParent() != null)
            ((RelativeLayout) gamepadLayout.getParent()).removeAllViews();
    }

    private void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final EditText edittext = new EditText(getContext());
        edittext.setText(gamepadLayout.getName());
        builder.setMessage("Layout Name");
        builder.setTitle("Save Gamepad Layout");

        builder.setView(edittext);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String layoutName = edittext.getText().toString();
                if (checkValidName(layoutName) && !checkNameAlreadyExists(layoutName)) {
                    gamepadLayout.setName(layoutName);
                    XMLParser.save(getContext(), gamepadLayout);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private boolean checkValidName(String layoutName) {
        if (layoutName.isEmpty() ||
                layoutName.contains(" ") ||
                layoutName.toLowerCase().contains("default")) {
            invalidNameDialog();
            return false;
        } else
            return true;
    }

    private void invalidNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Invalid Layout Name");
        builder.setMessage("Layout name must not contain spaces or the word 'default'.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private boolean checkNameAlreadyExists(String layoutName) {
        if (new File(getActivity().getFilesDir(), layoutName + ".xml").exists()) {
            layoutExistsDialog();
            return true;
        } else
            return false;
    }

    private void layoutExistsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(gamepadLayout.getName() + "already exists. Would you like to overwrite the existing layout?");
        builder.setTitle("Layout Already Exists");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                XMLParser.save(getContext(), gamepadLayout);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void loadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Load Layout");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        for (File f : getActivity().getFilesDir().listFiles()) {
            arrayAdapter.add(f.getName().substring(0, f.getName().length() - 4)); //Remove .xml from the end
        }
        builder.setNegativeButton("cancel",
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

    private class DeleteInputListener implements View.OnTouchListener {
        private GestureDetectorCompat mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());
        private GamepadInputView v;

        class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                deleteInputDialog();
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mDetector.onTouchEvent(event);
            this.v = (GamepadInputView) v;
            return false;
        }

        private void deleteInputDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Delete Selected Input?");
            builder.setTitle("Delete Input");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    gamepadLayout.removeGamepadInput(v);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
