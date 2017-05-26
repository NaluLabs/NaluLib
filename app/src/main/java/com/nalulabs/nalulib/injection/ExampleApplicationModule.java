package com.nalulabs.nalulib.injection;

import android.content.Context;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Jack on 16/02/2017.
 */

@Module
public class ExampleApplicationModule {
    private ExampleApplication application;

    public ExampleApplicationModule(ExampleApplication application) {
        this.application = application;
    }

    @Provides
    @ExampleApplicationScope
    public ExampleApplication application() {
        return application;
    }



    @Provides
    @ExampleApplicationScope
    public LayoutInflater layoutInflater() {
        return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
