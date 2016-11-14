package com.caroline.android.udacitycapstoneproject.view.loaders;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.caroline.android.udacitycapstoneproject.R;
import com.caroline.android.udacitycapstoneproject.model.FavoriteMovie;

import java.util.ArrayList;
import java.util.List;

public class LoaderActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<FavoriteMovie>> {
    FavoriteMoviesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        adapter = new FavoriteMoviesAdapter(this, new ArrayList<FavoriteMovie>());
        ListView movieListView = (ListView) findViewById(R.id.favorite_movies);
        movieListView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<List<FavoriteMovie>> onCreateLoader(int id, Bundle args) {
        return new FavoriteMovieLoader(LoaderActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<FavoriteMovie>> loader, List<FavoriteMovie> data) {
        adapter.setFavoriteMovies(data);
    }

    @Override
    public void onLoaderReset(Loader<List<FavoriteMovie>> loader) {
        adapter.setFavoriteMovies(new ArrayList<FavoriteMovie>());
    }
}
