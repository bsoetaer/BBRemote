package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.Serializable;

/**
 * Created by Braeden on 3/27/2016.
 */
public class GamepadInputView extends ImageView {

    private GamepadInput gamepadInput;
    private Context context;
    int deltaX, deltaY;
    private float scaleFactor = 1f;
    private ScaleGestureDetector scaleDetector;
    private boolean isEditable = true;

    public GamepadInputView (Context context) {
        super(context);
        setup(context);
    }

    public GamepadInputView (Context context, GamepadInput gamepadInput) {
        super(context);
        setup(context);
        setGamepadInput(gamepadInput);
    }

    private void setup(Context context) {
        this.context = context;
        setFocusable(true); // necessary for getting the touch events
        setScaleType(ScaleType.FIT_CENTER);
        setBackgroundColor(Color.TRANSPARENT);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public void setGamepadInput(GamepadInput input) {
        this.gamepadInput = input;
        setImageResource(GamepadInputImage.getImageId(gamepadInput));
    }

    public GamepadInput getGamepadInput() {
        return gamepadInput;
    }

    int pointerId;
    boolean invalidMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (!isEditable) {
            return false;
        }
        scaleDetector.onTouchEvent(event);
        handleMove(event);
        return true;
    }

    public void setEditable( boolean isEditable) {
        this.isEditable = isEditable;
    }

    private void handleMove(MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getActionMasked() ) {
            case MotionEvent.ACTION_DOWN:
                pointerId = event.getActionIndex();
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
                deltaX = X - lParams.leftMargin;
                deltaY = Y - lParams.topMargin;
                invalidMove = false;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                invalidMove = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if( event.getActionIndex() == pointerId)
                    invalidMove = true;
                else if (event.getPointerCount() == 1) {
                    invalidMove = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!invalidMove) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                    layoutParams.leftMargin = X - deltaX;
                    layoutParams.topMargin = Y - deltaY;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    setLayoutParams(layoutParams);
                }
                break;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            setScaleX(scaleFactor);
            setScaleY(scaleFactor);
            invalidate();
            return true;
        }
    }
}
