package com.nalulabs.nalulib.injection;


import android.view.LayoutInflater;

import com.nalulabs.nalulib.example.ExampleActivity;

import dagger.Component;


/**
 * Created by Jack on 16/02/2017.
 */

@ExampleApplicationScope
@Component(modules = ExampleApplicationModule.class)
public interface ExampleApplicationComponent {

    LayoutInflater layoutInflater();

    ExampleApplication application();

    void inject(ExampleActivity exampleActivity);
}
