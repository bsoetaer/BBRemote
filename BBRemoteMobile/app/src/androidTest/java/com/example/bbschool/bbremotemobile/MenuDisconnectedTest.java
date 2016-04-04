package com.example.bbschool.bbremotemobile;

/**
 * Created by Braeden on 4/3/2016.
 */
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.EventLogTags;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.intent.Intents.*;
import static android.support.test.espresso.intent.matcher.IntentMatchers.*;

public class MenuDisconnectedTest {

    @Rule
    public ActivityTestRule<ConnectionActivity> mActivityRule = new ActivityTestRule<>(
            ConnectionActivity.class);

    @Before
    public void setConnected() {
        ConnectionState.setConnected(false);
    }

    @Test
    public void failChangeInputMode() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).check(doesNotExist());
        onView(withText("Secondary Input Modes")).check(doesNotExist());
    }

    @Test
    public void changeToConnection() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Connection")).perform(click());
        onView(withId(R.id.pairedDeviceList)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToCustomize() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize Controller")).perform(click());
        Utils.matchToolbarTitle("Customize Gamepad");
    }

    @Test
    public void changeToSettings() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Settings")).perform(click());
        Utils.matchToolbarTitle("Settings");
    }
}
