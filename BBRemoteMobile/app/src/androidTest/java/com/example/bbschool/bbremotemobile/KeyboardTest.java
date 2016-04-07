package com.example.bbschool.bbremotemobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static org.hamcrest.Matchers.*;

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

/**
 * Created by Braeden on 4/3/2016.
 */
public class KeyboardTest {

    @Rule
    public ActivityTestRule<InputActivity> mActivityRule = new ActivityTestRule<InputActivity>(
            InputActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            ConnectionState.setConnected(true);
        }
    };

    @Before
    public void changeToKeyboard() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        preferences.edit().remove("DEFAULT_MOUSE_MODE").commit();
        relaunchActivity();
    }

    @Test
    public void pressKey() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void pressMultiKey() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void switchKeyboardMode() {
        // Verify on first page of keyboard
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_a)));
        // Change keyboard page
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_mode_change)));
        // Verify on second page of keyboard
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_equal)));
        // Change keyboard page
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_mode_change)));
        // Verify on third page of keyboard
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_f1)));
        // Change keyboard page
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_mode_change)));
        // Verify on first page of keyboard
        onView(withId(R.id.keyboard_view)).perform(ViewActions.pressKey(mActivityRule.getActivity().getResources().getInteger(R.integer.key_a)));
    }

    @Test
    public void quickSwitchTouch() {
        onView(withId(R.id.action_touchpad_quick)).perform(click());
        onView(withId(R.id.touchpad_left_click)).check(matches(isDisplayed()));
    }

    @Test
    public void quickSwitchOptical() {
        onView(withId(R.id.action_optical_quick)).perform(click());
        onView(withId(R.id.optical_left_click)).check(matches(isDisplayed()));
    }

    //Need this?
    @Test
    public void hideOptical() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        preferences.edit().putString("DEFAULT_MOUSE_MODE", "1").commit();
        relaunchActivity();
        onView(withId(R.id.action_touchpad_quick)).check(matches(isDisplayed()));
        onView(withId(R.id.action_optical_quick)).check(doesNotExist());
    }

    @Test
    public void hideTouchpad() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        preferences.edit().putString("DEFAULT_MOUSE_MODE", "0").commit();
        relaunchActivity();
        onView(withId(R.id.action_optical_quick)).check(matches(isDisplayed()));
        onView(withId(R.id.action_touchpad_quick)).check(doesNotExist());

    }

    @Test
    public void sendDataDisconnected() {
        //TODO Restructure code for Dagger injection
    }

    private void relaunchActivity() {
        Intent intent = new Intent();
        intent.putExtra("Mode", Mode.Keyboard);
        mActivityRule.launchActivity(intent);
    }
}
