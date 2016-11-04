package com.caroline.android.udacitycapstoneproject;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by carolinestewart on 10/25/16.
 */
public class MovieInfoFetchTask {

    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String BASE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/top_movies.json";


    public static void getMovieData(GetMovieCallback movieCallback) {


        new MovieFetchTask(movieCallback).execute(BASE_URL);

    }

    public static class MovieFetchTask extends AsyncTask<String, Void, List<MovieItem>> {
        private GetMovieCallback movieCallback;

        public MovieFetchTask(GetMovieCallback movieCallback) {


            MovieFetchTask.this.movieCallback = movieCallback;
        }


        @Override
        protected List<MovieItem> doInBackground(String... params) {
            String urlString = params[0];
            return DataUtil.fetchMovieItems(urlString);
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