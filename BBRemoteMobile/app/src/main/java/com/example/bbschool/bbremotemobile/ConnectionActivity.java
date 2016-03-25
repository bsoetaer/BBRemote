package com.example.bbschool.bbremotemobile;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.Set;

public class ConnectionActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private MainMenu mainMenu;

    private static final String TAG_CONNECTION_FRAGMENT = "connection_fragment";
    private ConnectionFragment connectionFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainMenu = new MainMenu(this);

        FragmentManager fm = getSupportFragmentManager();
        connectionFragment = (ConnectionFragment) fm.findFragmentByTag(TAG_CONNECTION_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (connectionFragment == null) {
            connectionFragment = new ConnectionFragment();
            fm.beginTransaction().add(R.id.connection_fragment, connectionFragment, TAG_CONNECTION_FRAGMENT).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu.hideItems(menu);
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
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


}
