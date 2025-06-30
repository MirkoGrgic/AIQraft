package com.example.aiqraft10;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.qualifiers.ApplicationContext;
import jakarta.inject.Inject;

@HiltAndroidApp
public class MyApplication extends Application {

    @Inject
    @ApplicationContext
    Context context;
}
