package com.caroline.android.udacitycapstoneproject.view.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.caroline.android.udacitycapstoneproject.R;
import com.caroline.android.udacitycapstoneproject.model.FavoriteMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 11/4/16.
 */
public class FavoriteMovieLoader extends AsyncTaskLoader<List<FavoriteMovie>> {


    public FavoriteMovieLoader(Context context) {
        super(context);
    }
    @Override
    public List<FavoriteMovie> loadInBackground() {
        List<FavoriteMovie> list = new ArrayList<>();


        list.add(new FavoriteMovie(R.string.twilight, R.string.romance));
        list.add(new FavoriteMovie(R.string.bambi, R.string.childrens));
        list.add(new FavoriteMovie(R.string.only_lovers, R.string.romance));
        return list;
    }
}
