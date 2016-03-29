package com.example.bbschool.bbremotemobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class CustomizeGamepadActivity extends AppCompatActivity {

    private enum GamepadMode {Customize, SelectInput};
    private MainMenu mainMenu;
    private static final HashMap<GamepadMode, String> fragmentTags;
    private static final String gamepadModeTag = "Gamepad Mode";
    private GamepadMode mode;

    static {
        fragmentTags = new HashMap<GamepadMode, String>();
        fragmentTags.put(GamepadMode.Customize, "Customize Gamepad");
        fragmentTags.put(GamepadMode.SelectInput, "Select Gamepad Input");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_gamepad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainMenu = new MainMenu(this);
        ConnectionReceiver.register(this);
        if (savedInstanceState != null ) {
            mode = (GamepadMode) savedInstanceState.getSerializable(gamepadModeTag);
        }
        mode = (mode == null ? GamepadMode.Customize : mode);
        loadFragment();
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
        // Save our own state now
        outState.putSerializable(gamepadModeTag, mode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ConnectionReceiver.unregister(this);
    }

    private void loadFragment() {
        Fragment newFragment = getFragment();
        if (newFragment == null)
            return;
        getSupportActionBar().setTitle(fragmentTags.get(mode));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customize_gamepad_fragment, newFragment, fragmentTags.get(mode));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private Fragment getFragment() {
        Fragment newFragment = getSupportFragmentManager().findFragmentByTag(fragmentTags.get(mode));
        if (newFragment == null) {
            switch (mode) {
                case SelectInput:
                    //newFragment = new SelectInputFragment();
                    break;
                default:
                    newFragment = new CustomizeLayoutFragment();
                    break;
            }
        }
        return newFragment;
    }

}
