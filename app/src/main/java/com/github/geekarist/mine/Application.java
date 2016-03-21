package com.github.geekarist.mine;

import android.os.StrictMode;
import android.util.Log;

import com.github.geekarist.mine.edit.EditStuffActivity;

import java.lang.reflect.Method;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
            // Fix false positive: see http://stackoverflow.com/a/24617415/1665730
            Method m;
            try {
                m = StrictMode.class.getMethod("incrementExpectedActivityCount", Class.class);
                m.invoke(null, EditStuffActivity.class);
            } catch (Exception e) {
                Log.v(getClass().getSimpleName(), "Exception setting up StrictMode", e);
            }
        }
        super.onCreate();
    }
}
