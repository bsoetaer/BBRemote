package com.example.bbschool.bbremotemobile;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Braeden on 4/3/2016.
 */
public class CustomizeGamepadTest {

    @Test
    public void addInput() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Connection")).perform(click());
        onView(withId(R.id.pairedDeviceList)).check(matches(isDisplayed()));
    }

    @Test
    public void moveInput() {}

    @Test
    public void deleteInput() {}

    @Test
    public void saveLayout() {}

    @Test
    public void loadLayout() {}

    @Test
    public void deleteLayout() {}

    @Test
    public void changeOrientation() {}

    @Test
    public void setRotateAsInput() {}
}
