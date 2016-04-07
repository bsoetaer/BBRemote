package com.example.bbschool.bbremotemobile;

import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

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
        assertTrue(testInput.getLeft() != 0);
        assertTrue(testInput.getRight() != 0);

        //onInput.perform(swipeCenter());
        //((CustomizeGamepadActivity) mActivityRule.getActivity()).get

    }

    @Test
    public void deleteInput() {
        addInput();
        onView(withClassName(endsWith("GamepadInputView"))).perform(longClick());
        onView(withText("OK")).perform(click());
        GamepadLayout gamepadLayout = getGamepadLayout();
        assertTrue(gamepadLayout.getGamepadInputs().size() == 0);
    }

    @Test
    public void saveLayout() {
        addInput();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Save Layout")).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(typeText("testLayout"));
        onView(withText("OK")).perform(click());
        ArrayList<String> layouts = XMLParser.getSavedLayouts(mActivityRule.getActivity());
        assertTrue(layouts.contains("testLayout"));
    }

    @Test
    public void loadLayout() {
        saveLayout();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Load Layout")).perform(click());
        onView(withText("DefaultComplex")).perform(click());
        verifyComplex();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Load Layout")).perform(click());
        onView(withText("testLayout")).perform(click());
        assertTrue(getGamepadLayout().getGamepadInputs().size() == 1);
        assertTrue(getGamepadLayout().getName().equals("testLayout"));
        assertTrue(mActivityRule.getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertFalse(getGamepadLayout().getPortrait());

    }

    @Test
    public void deleteLayout() {
        saveLayout();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Delete Layout")).perform(click());
        onView(withText("Yes")).perform(click());
        ArrayList<String> layouts = XMLParser.getSavedLayouts(mActivityRule.getActivity());
        assertFalse(layouts.contains("testLayout"));
    }

    @Test
    public void changeOrientation() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Orientation")).perform(click());
        onView(withText("Landscape")).perform(click());
        assertFalse(getGamepadLayout().getPortrait());
        assertTrue(mActivityRule.getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Orientation")).perform(click());
        onView(withText("Portrait")).perform(click());
        assertTrue(getGamepadLayout().getPortrait());
        assertTrue(mActivityRule.getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void setRotateAsInput() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Rotate as Input")).perform(click());
        onView(withText("Enabled")).perform(click());
        assertTrue(getGamepadLayout().getRotateAsInput());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Customize")).perform(click());
        onView(withText("Rotate as Input")).perform(click());
        onView(withText("Disabled")).perform(click());
        assertFalse(getGamepadLayout().getRotateAsInput());
    }

    public GamepadLayout getGamepadLayout() {
        RelativeLayout fragmentLayout =  (RelativeLayout) ((RelativeLayout) mActivityRule.getActivity().findViewById(R.id.customize_gamepad_fragment)).getChildAt(0);
        GamepadLayout gamepadLayout = (GamepadLayout) fragmentLayout.getChildAt(0);
        return gamepadLayout;
    }

    public static ViewAction swipeCenter() {
        return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.TOP_LEFT,
                GeneralLocation.BOTTOM_RIGHT, Press.PINPOINT);
    }

    private void verifyComplex() {
        GamepadLayout layout = getGamepadLayout();
        assertTrue(layout.getGamepadInputs().size() == 8);
        assertTrue(layout.getName().equals("DefaultComplex"));
        assertTrue(mActivityRule.getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertFalse(layout.getPortrait());
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
