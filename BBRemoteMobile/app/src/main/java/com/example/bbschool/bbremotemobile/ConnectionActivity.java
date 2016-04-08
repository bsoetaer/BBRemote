package com.example.bbschool.bbremotemobile;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Activity to allow users to connect to windows devices via bluetooth.
 * 3.2.1. Connect to Computer Via Bluetooth
 * and sub requirements
 * 3.2.1.2.1. Connect Device Successfully
 * 3.2.1.2.2. Only Allow Connection Between Paired Devices
 * 3.2.1.2.3. Handle Failed Attempt to Connect to Computer Via Bluetooth
 * 3.2.1.2.4. Scan for Devices
 * 3.2.1.2.8. Auto-connect to Last Device on Startup
 */
public class ConnectionActivity extends AppCompatActivity  {

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

}
