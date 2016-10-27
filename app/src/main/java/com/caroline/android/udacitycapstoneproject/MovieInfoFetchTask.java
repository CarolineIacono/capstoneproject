package com.caroline.android.udacitycapstoneproject;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

            return parseMovieItems(DataUtil.fetch(urlString));

        }

        @Override
        protected void onPostExecute(List<MovieItem> result) {
            movieCallback.onComplete(result);


        }

        private List<MovieItem> parseMovieItems(String result) {


            List<MovieItem> items = null;


            try {
                JSONArray response = new JSONArray(result);

                {
                    items = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject post = response.getJSONObject(i);
                        items.add(parseSingleObject(post));

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return items;
        }

        private MovieItem parseSingleObject(JSONObject post) {
            MovieItem item = new MovieItem();


            String title = post.optString("title");
            item.setTitle(title);

            String rank = post.optString("rank");
            item.setRank(rank);

            String year = post.optString("year");
            item.setYear(year);

            String poster = post.optString("poster");
            item.setPoster(poster);

            String imdbLink = post.optString("imdbLink");
            item.setImdbLink(imdbLink);

            String imdbRating = post.optString("imdbRating");
            item.setImdbRating(imdbRating);

            String imdbVotes = post.optString("imdbVotes");
            item.setImdbVotes(imdbVotes);

            String imdbId = post.optString("imdbId");
            item.setImdbId(imdbId);


            return item;
        }



    }

    public interface GetMovieCallback {
        void onComplete(List<MovieItem> list);

    }
}