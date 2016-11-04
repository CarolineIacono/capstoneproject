package com.caroline.android.udacitycapstoneproject.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 11/4/16.
 */
public class FavoriteMovieLoader extends AsyncTaskLoader<List<FavoriteMovies>> {
    public FavoriteMovieLoader(Context context) {
        super(context);
    }
    @Override
    public List<FavoriteMovies> loadInBackground() {
        List<FavoriteMovies> list = new ArrayList<>();


        list.add(new FavoriteMovies("Twilight", "2011"));
        list.add(new FavoriteMovies("Bambi", "1997"));
        list.add(new FavoriteMovies("Only Lovers Left Alive", "2014"));
        return list;
    }
}
