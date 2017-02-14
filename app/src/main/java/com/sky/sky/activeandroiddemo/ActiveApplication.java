package com.sky.sky.activeandroiddemo;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by BlueSky on 17/2/14.
 */

public class ActiveApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
