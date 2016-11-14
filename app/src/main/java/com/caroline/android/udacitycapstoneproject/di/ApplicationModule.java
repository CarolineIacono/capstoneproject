package com.caroline.android.udacitycapstoneproject.di;

import android.app.Application;
import com.caroline.android.udacitycapstoneproject.model.MovieDao;
import com.caroline.android.udacitycapstoneproject.model.MovieDaoActual;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {
    Application application;

    public ApplicationModule(Application thisApplication) {
        application = thisApplication;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    MovieDao provideMovieDao() {
        return new MovieDaoActual();
    }
}




