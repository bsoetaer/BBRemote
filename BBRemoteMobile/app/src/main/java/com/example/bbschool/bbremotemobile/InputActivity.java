package com.example.bbschool.bbremotemobile;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bbschool.bbremotemobile.R;

public class InputActivity extends AppCompatActivity {

    private MainMenu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainMenu = new MainMenu(this);
        //loadStartFragment();
        initialFragmentLoad(new myFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( mainMenu.menuItemSelected(item) )
            return true;
        else
            return super.onOptionsItemSelected(item);
    }

    public void swapFragments(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadStartFragment() {
        Intent intent = getIntent();
        Mode mode = Mode.Keyboard;
        if ( intent.hasExtra("Mode") ) {
            mode = (Mode) intent.getSerializableExtra("Mode");
        }
        switch( mode ) {
            case Touchpad:
                //TODO
                break;
            case Optical:
                //TODO
                break;
            case Camera:
                //TODO
                break;
            case Mic:
                //TODO
                break;
            case Gamepad:
                //TODO
                break;
            default:
                initialFragmentLoad( new KeyboardFragment() );
                break;
        }
    }

    private void initialFragmentLoad(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,  newFragment);
        fragmentTransaction.commit();
    }

}
