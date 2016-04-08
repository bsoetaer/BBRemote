package com.example.bbschool.bbremotemobile;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests of the optical mouse UI
 * 3.2.6.2.4. Swap to Keyboard from Optical Mouse
 * Created by Braeden on 4/3/2016.
 */
public class OpticalTest {

    @Rule
    public ActivityTestRule<InputActivity> mActivityRule = new ActivityTestRule<InputActivity>(
            InputActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            ConnectionState.setConnected(true);
        }

        @Override
        protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();
            intent.putExtra("Mode", Mode.Optical);
            return intent;
        }
    };

    @Test
    public void motionTest() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void clickTest() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void scrollTest() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void quickSwitchKeyboard() {
        onView(withId(R.id.action_keyboard_quick)).perform(click());
        onView(withId(R.id.keyboard_view)).check(matches(isDisplayed()));
    }

    @Test
    public void sendDataDisconnected() {
        //TODO Restructure code for Dagger injection
    }
}
