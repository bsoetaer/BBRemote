package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Class to workaround bug in Android for the custom keyboard as the getSystemService call breaks
 * when requesting audio service.
 * 3.2.3. Touchscreen Keyboard Input
 * Created by Braeden on 3/8/2016.
 */
public class ContextWrapperFix extends ContextWrapper {
    private boolean editMode;

    public ContextWrapperFix(Context context, boolean editMode) {
        super(context);
        this.editMode = editMode;
    }

    public Object getSystemService(String name) {
        if (editMode && Context.AUDIO_SERVICE.equals(name)) {
            return null;
        }
        return super.getSystemService(name);
    }
}
