package com.caroline.android.udacitycapstoneproject.view;

import android.os.AsyncTask;

import com.caroline.android.udacitycapstoneproject.model.DataUtil;
import com.caroline.android.udacitycapstoneproject.model.MovieItem;

import java.util.List;

/**
 * Created by carolinestewart on 11/9/16.
 */
public class MovieFetchTask extends AsyncTask<String, Void, List<MovieItem>> {
    private GetMovieCallback movieCallback;

    public MovieFetchTask(GetMovieCallback movieCallback) {


        this.movieCallback = movieCallback;
    }


    @Override
    protected List<MovieItem> doInBackground(String... params) {
        return DataUtil.fetchMovieItems();
    }

    @Override
    protected void onPostExecute(List<MovieItem> result) {
        movieCallback.onComplete(result);
    }

    public interface GetMovieCallback {
        void onComplete(List<MovieItem> list);

    }
}
