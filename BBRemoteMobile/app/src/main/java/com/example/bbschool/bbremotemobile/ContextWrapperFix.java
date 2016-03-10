package com.example.bbschool.bbremotemobile;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Braeden on 3/8/2016.
 * TODO reference properly
 * http://stackoverflow.com/questions/11090629/android-unsupported-service-audio/34809518#34809518
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
