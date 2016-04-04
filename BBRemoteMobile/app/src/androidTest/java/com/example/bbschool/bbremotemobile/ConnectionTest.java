package com.example.bbschool.bbremotemobile;

import android.bluetooth.BluetoothAdapter;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.regex.Pattern;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;

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
        //ConnectionState.setConnected(true);
    }

    @Test
    public void initialScan() throws Exception {
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

    //TODO test that we actually connect
    /*public void testConnect() {

    }*/
}
