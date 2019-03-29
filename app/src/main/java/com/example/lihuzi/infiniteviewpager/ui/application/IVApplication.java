package com.example.lihuzi.infiniteviewpager.ui.application;

import android.app.Application;
import android.support.multidex.MultiDex;

public class IVApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
