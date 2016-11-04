package com.caroline.android.udacitycapstoneproject.loaders;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.caroline.android.udacitycapstoneproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 11/4/16.
 */
public class LoaderActivity  extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<FavoriteMovies>> {
    FavoriteMoviesAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        adapter = new FavoriteMoviesAdapter(this, new ArrayList<FavoriteMovies>());
        ListView movieListView = (ListView) findViewById(R.id.favorite_movies);
        movieListView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }
    @Override
    public Loader<List<FavoriteMovies>> onCreateLoader(int id, Bundle args) {
        return new FavoriteMovieLoader(LoaderActivity.this);
    }
    @Override
    public void onLoadFinished(Loader<List<FavoriteMovies>> loader, List<FavoriteMovies> data) {
        adapter.setFavoriteMovies(data);
    }
    @Override
    public void onLoaderReset(Loader<List<FavoriteMovies>> loader) {
        adapter.setFavoriteMovies(new ArrayList<FavoriteMovies>());
    }
}
