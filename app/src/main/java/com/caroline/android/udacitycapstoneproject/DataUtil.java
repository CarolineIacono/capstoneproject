package com.caroline.android.udacitycapstoneproject;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carolinestewart on 6/8/16.
 */
public class DataUtil {


    public static String fetch(String urlString) {

        Integer result = 0;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {

            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                movieJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                movieJsonStr = null;
            }
            movieJsonStr = buffer.toString();


        } catch (IOException e) {
            Log.e("DataUtil", "Error ", e);
            movieJsonStr = null;
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
        return movieJsonStr;
    }

    public static MovieItem parseSingleObject(JSONObject post) {
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
