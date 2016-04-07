package com.example.bbschool.bbremotemobile;

import org.junit.Test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.PreferenceMatchers.*;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import static 	android.support.test.espresso.intent.Checks.*;

/**
 * Created by Braeden on 4/3/2016.
 */
public class SettingsTest {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityRule = new ActivityTestRule<SettingsActivity>(
            SettingsActivity.class);

    @Before
    public void clearPrefs() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        preferences.edit().clear().commit();
    }

    @Test
    public void setDefaultMouse() {
        onView(withText("Default Mouse Mode")).perform(click());
        onView(withText("Touchpad")).perform(click());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        assertTrue(preferences.getString("DEFAULT_MOUSE_MODE", "").equals("1"));
    }

    @Test
    public void setDefaultLayout() {
        onView(withText("Default Gamepad Layout")).perform(click());
        onView(withText("DefaultComplex")).perform(click());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        assertTrue(preferences.getString("DEFAULT_GAMEPAD_LAYOUT", "").equals("DefaultComplex"));
    }

    @Test
    public void setTouchSensitivity() {
        onView(allOf(withParent(hasSibling(withText("Touchpad Sensitivity"))), withClassName(endsWith("AppCompatSeekBar")))).perform(swipeRight());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        assertFalse(preferences.getInt("TOUCHPAD_SENSITIVITY", 50) == 50);
}

    @Test
    public void setOpticalSensitivity() {
        onView(allOf(withParent(hasSibling(withText("Optical Sensitivity"))), withClassName(endsWith("AppCompatSeekBar")))).perform(swipeRight());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        assertFalse(preferences.getInt("OPTICAL_SENSITIVITY", 50) == 50);
    }

    @Test
    public void setGamepadSensitivity() {
        onView(allOf(withParent(hasSibling(withText("Gamepad Axis Sensitivity"))), withClassName(endsWith("AppCompatSeekBar")))).perform(swipeRight());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity());
        assertFalse(preferences.getInt("GAMEPAD_SENSITIVITY", 50) == 50);
    }

    public static Matcher<Object> withDialogListContent(String expectedText) {
        checkNotNull(expectedText);

        return withDialogListContent(equalTo(expectedText));
    }

    @SuppressWarnings("rawtypes")
    public static Matcher<Object> withDialogListContent(final Matcher<String> itemTextMatcher) {
        checkNotNull(itemTextMatcher);
        return new BoundedMatcher<Object, String>(String.class) {
            @Override    public boolean matchesSafely(String value){
                return itemTextMatcher.matches(value);
            }

            @Override    public void describeTo(org.hamcrest.Description description) {
                description.appendText("with Dialog List Content: ");
                itemTextMatcher.describeTo(description);
            }
        };
    }
}
