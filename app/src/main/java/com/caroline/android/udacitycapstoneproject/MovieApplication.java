package com.caroline.android.udacitycapstoneproject;

import android.app.Application;

import com.caroline.android.udacitycapstoneproject.di.ApplicationComponent;
import com.caroline.android.udacitycapstoneproject.di.ApplicationModule;
import com.caroline.android.udacitycapstoneproject.di.DaggerApplicationComponent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class MovieApplication extends Application {
    private ApplicationComponent component;
    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }
}
