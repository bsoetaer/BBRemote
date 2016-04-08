package com.example.bbschool.bbremotemobile;

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
//import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;

/**
 * Tests for navigating menus
 * 3.2.2.2.1. Mode Select with Valid Connection
 * Created by Braeden on 4/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MenuConnectedTest {

    @Rule
    public ActivityTestRule<ConnectionActivity> mActivityRule = new ActivityTestRule<>(
            ConnectionActivity.class);

    @Before
    public void setConnected() {
        ConnectionState.setConnected(true);
    }

    @Test
    public void changeToConnection() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Connection")).perform(click());
        onView(withId(R.id.pairedDeviceList)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToKeyboard() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).perform(click());
        onView(withText("Keyboard")).perform(click());
        onView(withId(R.id.keyboard_view)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToTouchpad() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).perform(click());
        onView(withText("Touchpad Mouse")).perform(click());
        onView(withId(R.id.touchpad_left_click)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToOptical() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).perform(click());
        onView(withText("Optical Mouse")).perform(click());
        //matchToolbarTitle("Optical Mouse");
        onView(withId(R.id.optical_left_click)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToMic() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).perform(click());
        onView(withText("Microphone")).perform(click());
        onView(withId(R.id.mic_toggle)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToCamera() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).perform(click());
        onView(withText("Camera")).perform(click());
        onView(withId(R.id.camera_toggle)).check(matches(isDisplayed()));
    }

    @Test
    public void changeToGamepad() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Input Modes")).perform(click());
        onView(withText("Game Controller")).perform(click());
        Utils.matchToolbarTitle("Game Controller");
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
