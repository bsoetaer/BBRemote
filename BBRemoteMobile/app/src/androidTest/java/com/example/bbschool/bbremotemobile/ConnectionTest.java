package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothAdapter;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Braeden on 4/3/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ConnectionTest {
    BluetoothAdapter bluetoothAdapter;

    @Rule
    public ActivityTestRule<ConnectionActivity> mActivityRule = new ActivityTestRule<>(
            ConnectionActivity.class);

    @Before
    public void setConnected() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Test
    public void initialScan() {
        assertTrue(bluetoothAdapter.isDiscovering());
    }

    @Test
    public void testScan() throws InterruptedException{
        // Wait for initial scanning to be done
        while(bluetoothAdapter.isDiscovering()) {
            Thread.sleep(1000);
        }
        onView(withId(R.id.action_scan)).perform(click());
        assertTrue(bluetoothAdapter.isDiscovering());
    }


    public void testConnect() {
        //TODO test that we actually connect, maybe with dagger injection
    }
}
