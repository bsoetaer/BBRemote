package com.example.bbschool.bbremotemobile;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class InputActivity extends AppCompatActivity {

    private MainMenu mainMenu;
    private static final HashMap<Mode, String> fragmentTags;
    private static final String modeTag = "Mode";
    private Mode mode;

    static {
        fragmentTags = new HashMap<Mode, String>();
        fragmentTags.put(Mode.Keyboard, "Keyboard");
        fragmentTags.put(Mode.Touchpad, "Touchpad Mouse");
        fragmentTags.put(Mode.Optical, "Optical Mouse");
        fragmentTags.put(Mode.Camera, "Camera");
        fragmentTags.put(Mode.Mic, "Microphone");
        fragmentTags.put(Mode.Gamepad, "Game Controller");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainMenu = new MainMenu(this);
        ConnectionReceiver.register(this);
        if (savedInstanceState != null ) {
            mode = (Mode) savedInstanceState.getSerializable(modeTag);
            swapFragments(mode);
        }
        else {
            loadStartFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu.hideModes(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( mainMenu.menuItemSelected(item) )
            return true;
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // XMLParser our own state now
        outState.putSerializable(modeTag, mode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ConnectionReceiver.unregister(this);
    }

    public void swapFragments(Mode mode) {
        Fragment newFragment = getFragment(mode);
        fragmentLoad(newFragment, mode);
    }

    private void loadStartFragment() {
        mode = Mode.Keyboard;
        Intent intent = getIntent();
        if ( intent.hasExtra("Mode") ) {
            mode = (Mode) intent.getSerializableExtra("Mode");
        }
        swapFragments(mode);
    }

    private Fragment getFragment(Mode mode) {
        Fragment newFragment = getSupportFragmentManager().findFragmentByTag(fragmentTags.get(mode));
        if (newFragment == null) {
            switch (mode) {
                case Touchpad:
                    newFragment = new TouchpadFragment();
                    break;
                case Optical:
                    newFragment = new OpticalFragment();
                    break;
                case Camera:
                    newFragment = new CameraFragment();
                    break;
                case Mic:
                    newFragment = new MicrophoneFragment();
                    break;
                case Gamepad:
                    newFragment = new GamepadFragment();
                    break;
                default:
                    newFragment = new KeyboardFragment();
                    break;
            }
        }
        return newFragment;
    }

    private void fragmentLoad(Fragment newFragment, Mode mode) {
        if (newFragment == null)
            return;
        this.mode = mode;
        getSupportActionBar().setTitle(fragmentTags.get(mode));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.input_mode_fragment, newFragment, fragmentTags.get(mode));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
