package com.example.bbschool.bbremotemobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 3.2.7.2.5. Change Gamepad Layout
 * 3.2.7.2.6. Default Gamepad Layouts
 * Created by Braeden on 4/3/2016.
 */
public class GamepadTest {

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
            intent.putExtra("Mode", Mode.Gamepad);
            return intent;
        }
    };


    @Before
    public void clearPrefs() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        preferences.edit().remove("DEFAULT_GAMEPAD_LAYOUT").commit();
        relaunchActivity();
    }

    @Test
    public void defaultLoad() {
        // Verify default layout was loaded
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        preferences.edit().putString("DEFAULT_GAMEPAD_LAYOUT", "DefaultComplex").commit();
        relaunchActivity();
        verifyComplex();
    }

    @Test
    public void loadLayout() throws InterruptedException {
        onView(withId(R.id.action_load_layout)).perform(click());
        onView(withText("DefaultComplex")).perform(click());
        verifyComplex();
    }

    @Test
    public void moveStick() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void pressDpad() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void pressInput() {
        //TODO Restructure code for Dagger injection
    }

    @Test
    public void sendDataDisconnected() {
        //TODO Restructure code for Dagger injection
    }

    private void relaunchActivity() {
        Intent intent = new Intent();
        intent.putExtra("Mode", Mode.Gamepad);
        mActivityRule.launchActivity(intent);
    }

    public GamepadLayout getGamepadLayout() {
        RelativeLayout fragmentLayout =  ((RelativeLayout) mActivityRule.getActivity().findViewById(R.id.input_mode_fragment));
        fragmentLayout = (RelativeLayout) fragmentLayout.getChildAt(0);
        GamepadLayout gamepadLayout = (GamepadLayout) fragmentLayout.getChildAt(0);
        return gamepadLayout;
    }

    private void verifyComplex() {
        GamepadLayout layout = getGamepadLayout();
        assertTrue(layout.getGamepadInputs().size() == 8);
        assertTrue(layout.getName().equals("DefaultComplex"));
        assertTrue(mActivityRule.getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertFalse(layout.getPortrait());
    }
}
