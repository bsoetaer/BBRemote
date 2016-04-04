package com.example.bbschool.bbremotemobile;

import android.app.Activity;
import android.app.Instrumentation;
import android.location.Location;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;
import android.view.MotionEvent;
import android.view.View;
import android.test.ViewAsserts;
import 	android.test.ActivityInstrumentationTestCase2.*;
import android.widget.RelativeLayout;

import static android.support.test.espresso.action.ViewActions.swipeRight;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;

/**
 * Created by Braeden on 4/3/2016.
 */
public class CustomizeGamepadTest {

    @Rule
    public ActivityTestRule<CustomizeGamepadActivity> mActivityRule = new ActivityTestRule<>(
            CustomizeGamepadActivity.class);

    @Before
    public void clearFiles() {
        for( File f : mActivityRule.getActivity().getFilesDir().listFiles()) {
            mActivityRule.getActivity().deleteFile(f.getName());
        }
    }

    @Test
    public void addInput() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Add Input")).perform(click());
        onView(withId(R.id.gamepadInputList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.gamepadInputList)).atPosition(0).perform(click());
        Utils.matchToolbarTitle("Customize Gamepad");
        onView(withClassName(endsWith("GamepadInputView"))).check(matches(isDisplayed()));
        //with
        //withRe
        //onView(withClassName(any("GamepadInputView"))).check(matches(isDisplayed()));
        //onData(is(instanceOf(GamepadInputView.class))).check(matches(isDisplayed()));
        //onView(withId(R.id.pairedDeviceList)).check(matches(isDisplayed()));
    }

    @Test
    public void moveInput() {
        addInput();
        GamepadLayout gamepadLayout = getGamepadLayout();
        GamepadInputView testInput = gamepadLayout.getGamepadInputs().get(0);
        int[] coords = new int[2];
        testInput.getLocationOnScreen(coords);
        //drag(InstrumentationRegistry.getInstrumentation(), coords[0] + 5, coords[1] + 5, coords[0] + 105, coords[1] + 105, 10);
        onView(withClassName(endsWith("GamepadInputView"))).perform(swipeCenter());
        testInput.getLocationOnScreen(coords);
        assertTrue(testInput.getX() == coords[0] + 100);
        assertTrue(testInput.getY() == coords[1] + 100);

        //onInput.perform(swipeCenter());
        //((CustomizeGamepadActivity) mActivityRule.getActivity()).get

    }

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

    public GamepadLayout getGamepadLayout() {
        RelativeLayout fragmentLayout =  (RelativeLayout) ((RelativeLayout) mActivityRule.getActivity().findViewById(R.id.customize_gamepad_fragment)).getChildAt(0);
        GamepadLayout gamepadLayout = (GamepadLayout) fragmentLayout.getChildAt(0);
        return gamepadLayout;
    }

    public static ViewAction swipeCenter() {
        return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.TOP_LEFT,
                GeneralLocation.BOTTOM_RIGHT, Press.PINPOINT);
    }

    public static void drag(Instrumentation inst, float fromX, float toX, float fromY,
                            float toY, int stepCount) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        float y = fromY;
        float x = fromX;

        float yStep = (toY - fromY) / stepCount;
        float xStep = (toX - fromX) / stepCount;

        MotionEvent event = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        inst.sendPointerSync(event);
        for (int i = 0; i < stepCount; ++i) {
            y += yStep;
            x += xStep;
            eventTime = SystemClock.uptimeMillis();
            event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, x, y, 0);
            inst.sendPointerSync(event);
        }

        eventTime = SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
        inst.sendPointerSync(event);
        inst.waitForIdleSync();
    }
}
