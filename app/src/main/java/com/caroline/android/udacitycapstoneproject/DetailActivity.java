package com.caroline.android.udacitycapstoneproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

/**
 * Created by carolinestewart on 9/15/16.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String DETAIL_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/by_id/";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);

//        MovieSummaryFragment movieSummaryFragment = new MovieSummaryFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, movieSummaryFragment);
//        fragmentTransaction.commit();


        Intent intent = getIntent();

        String key = intent.getExtras().getString("key");



        new MovieSummaryFetchTask().execute(DETAIL_URL + key + ".json");



    }


    public class MovieSummaryFetchTask extends AsyncTask<String, Void, MovieSummary> {
        @Override
        protected void onPostExecute(MovieSummary movieSummary) {
            super.onPostExecute(movieSummary);

            TextView title = (TextView) findViewById(R.id.title);
            title.setText(movieSummary.getTitle());
        }

        @Override
        protected MovieSummary doInBackground(String... params) {
            String urlString = params[0];
            return parseMovieItem(DataUtil.fetch(urlString));



        }


    }

    private MovieSummary parseMovieItem(String result) {

        try {
            JSONObject response = new JSONObject(result);

            if(response == null){
                return null;
            }else{
                return parseSingleObject(response);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt("stateOptions", state);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onPause() {


        super.onPause();
    }

    private MovieSummary parseSingleObject(JSONObject post) {
        MovieSummary item = new MovieSummary();


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

        String genre = post.optString("genre");
        item.setGenre(genre);

        String imdbId = post.optString("imdbId");
        item.setImdbId(imdbId);

        String rated = post.optString("rated");
        item.setRated(rated);

        String release = post.optString("released");
        item.setReleased(release);

        String director = post.optString("director");
        item.setDirector(director);

        String language = post.optString("language");
        item.setLanguage(language);

        String writer = post.optString("writer");
        item.setWriter(writer);

        String actor = post.optString("actors");
        item.setActors(actor);

        String plot = post.optString("plot");
        item.setPlot(plot);

        String country = post.optString("country");
        item.setCountry(country);

        String runtime = post.optString("runtime");
        item.setRuntime(runtime);

        String metascore = post.optString("metascore");
        item.setMetascore(metascore);

        String awards = post.optString("awards");
        item.setAwards(awards);

        return item;
    }





}













