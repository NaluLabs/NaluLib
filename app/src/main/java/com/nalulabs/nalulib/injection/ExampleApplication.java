package com.nalulabs.nalulib.injection;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nalulabs.lib.logs.LogUtils;
import com.nalulabs.nalulib.BuildConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Jack on 16/02/2017.
 */

public class ExampleApplication extends Application {
    private ExampleApplicationComponent component;

    public static ExampleApplication get(Activity activity) {
        return (ExampleApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.initLoggingAndCrashReporting(BuildConfig.DEBUG);

        /** INIT DAGGER COMPONENT*/
        component = DaggerExampleApplicationComponent.builder()
                .exampleApplicationModule(new ExampleApplicationModule(this))
                .build();


    }

    public ExampleApplicationComponent getComponent() {
        return component;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
