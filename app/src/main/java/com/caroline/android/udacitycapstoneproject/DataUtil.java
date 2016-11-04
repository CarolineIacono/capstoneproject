package com.caroline.android.udacitycapstoneproject;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 6/8/16.
 */
public class DataUtil {
    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String TOP_MOVIE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/top_movies.json";

    public static List<MovieItem> fetchMovieItems() {
        String movieJsonStr = fetchJson(TOP_MOVIE_URL);
        return parseMovieItems(movieJsonStr);
    }

    public static MovieSummary fetchMovieSummary(String urlString) {
        return parseMovieSummary(fetchJson(urlString));
    }


    @Nullable
    private static String fetchJson(String urlString) {
        Integer result = 0;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonStr = null;

        try {

            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                jsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                jsonStr = null;
            }
            jsonStr = buffer.toString();


        } catch (IOException e) {
            Log.e("DataUtil", "Error ", e);
            jsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("DataUtil", "Error closing stream", e);
                }
            }
        }
        return jsonStr;
    }

    private static List<MovieItem> parseMovieItems(String result) {


        List<MovieItem> items = null;


        try {
            JSONArray response = new JSONArray(result);

            {
                items = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject post = response.getJSONObject(i);
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


                    items.add(item);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    private static MovieSummary parseMovieSummary(String result) {

        try {
            JSONObject response = new JSONObject(result);

            MovieSummary item = new MovieSummary();


            String title = response.optString("title");
            item.setTitle(title);

            StringBuilder sb = new StringBuilder();
            JSONArray genre = response.optJSONArray("genre");
            for(int i = 0; i < genre.length(); i++) {
                String genres = (String) genre.get(i);
                sb.append(genres + " ");
            }
            item.setGenre(sb);


            StringBuilder sbActor = new StringBuilder();
            JSONArray actor = response.optJSONArray("actors");
            for(int i = 0; i < actor.length(); i++) {
                String actors = (String) actor.get(i);
                sbActor.append(actors + " ");
            }
            item.setActors(sbActor);

            StringBuilder sbLanguage = new StringBuilder();
            JSONArray language = response.optJSONArray("language");
            for(int i = 0; i < language.length(); i++) {
                String languages = (String) language.get(i);
                sbLanguage.append(languages + " ");
            }


            item.setLanguage(sbLanguage);


            String year = response.optString("year");
            item.setYear(year);

            String poster = response.optString("poster");
            item.setPoster(poster);

            String imdbLink = response.optString("imdbLink");
            item.setImdbLink(imdbLink);

            String imdbRating = response.optString("imdbRating");
            item.setImdbRating(imdbRating);

            String imdbVotes = response.optString("imdbVotes");
            item.setImdbVotes(imdbVotes);

            String imdbId = response.optString("imdbId");
            item.setImdbId(imdbId);

            String rated = response.optString("rated");
            item.setRated(rated);

            String release = response.optString("released");
            item.setReleased(release);

            String director = response.optString("director");
            item.setDirector(director);


            String writer = response.optString("writer");
            item.setWriter(writer);

            String country = response.optString("country");
            item.setCountry(country);

            String runtime = response.optString("runtime");
            item.setRuntime(runtime);

            String metascore = response.optString("metascore");
            item.setMetascore(metascore);

            String awards = response.optString("awards");
            item.setAwards(awards);

            String plot = response.optString("plot");
            item.setPlot(plot);

            return item;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}
