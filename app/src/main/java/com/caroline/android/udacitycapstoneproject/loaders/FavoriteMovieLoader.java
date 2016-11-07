package com.caroline.android.udacitycapstoneproject.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.caroline.android.udacitycapstoneproject.R;

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


        list.add(new FavoriteMovies(R.string.twilight, R.string.romance));
        list.add(new FavoriteMovies(R.string.bambi, R.string.childrens));
        list.add(new FavoriteMovies(R.string.only_lovers, R.string.romance));
        return list;
    }
}
