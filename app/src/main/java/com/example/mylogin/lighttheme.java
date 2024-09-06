package com.example.mylogin;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class lighttheme extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Disable dark mode and force light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}