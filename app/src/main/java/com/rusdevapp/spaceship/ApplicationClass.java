package com.rusdevapp.spaceship;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application
{

    private static final String ONESIGNAL_APP_ID = "20844719-4a48-4ef8-9fe6-7ab1560833c2";

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
