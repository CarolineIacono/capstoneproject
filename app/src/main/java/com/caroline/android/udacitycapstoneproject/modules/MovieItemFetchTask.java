package com.caroline.android.udacitycapstoneproject.modules;

import android.os.AsyncTask;

import com.caroline.android.udacitycapstoneproject.MovieItem;

import java.util.List;

/**
 * Created by carolinestewart on 10/25/16.
 */
public class MovieItemFetchTask {




    public static void getMovieData(GetMovieCallback movieCallback) {


        new MovieFetchTask(movieCallback).execute();

    }

    public static class MovieFetchTask extends AsyncTask<String, Void, List<MovieItem>> {
        private GetMovieCallback movieCallback;

        public MovieFetchTask(GetMovieCallback movieCallback) {


            MovieFetchTask.this.movieCallback = movieCallback;
        }


        @Override
        protected List<MovieItem> doInBackground(String... params) {
            return MovieSummaryFetchTask.fetchMovieItems();
        }

        @Override
        protected void onPostExecute(List<MovieItem> result) {
            movieCallback.onComplete(result);
        }
    }

    public interface GetMovieCallback {
        void onComplete(List<MovieItem> list);

    }
}