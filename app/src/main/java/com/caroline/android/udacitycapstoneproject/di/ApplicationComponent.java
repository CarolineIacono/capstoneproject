package com.caroline.android.udacitycapstoneproject.di;

import com.caroline.android.udacitycapstoneproject.view.MainActivity;
import com.caroline.android.udacitycapstoneproject.view.MovieSummaryFragment;
import com.caroline.android.udacitycapstoneproject.view.contentprovider2.WidgetContentProvider;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
    public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(MovieSummaryFragment movieSummaryFragment);
    void inject(WidgetContentProvider widgetContentProvider);
    }


