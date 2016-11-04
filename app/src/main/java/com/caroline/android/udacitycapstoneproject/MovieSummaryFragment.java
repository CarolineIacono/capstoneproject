package com.caroline.android.udacitycapstoneproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carolinestewart on 10/26/16.
 */
public class MovieSummaryFragment extends Fragment {


    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String DETAIL_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/by_id/";
    private MovieSummary movieSummaryObject;

    public MovieSummaryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

//check if I already have a MovieSummary object
        String key = getArguments().getString("key");
        new MovieSummaryFetchTask().execute(DETAIL_URL + key + ".json");




        return inflater.inflate(R.layout.details_screen, container, false);


    }





    public class MovieSummaryFetchTask extends AsyncTask<String, Void, MovieSummary> {
        @Override
        protected void onPostExecute(MovieSummary movieSummary) {
            super.onPostExecute(movieSummary);



            TextView title = (TextView) getView().findViewById(R.id.title);
            title.setText(movieSummary.getTitle());

            TextView imdbRating = (TextView) getView().findViewById(R.id.imdbRating);
            imdbRating.setText(movieSummary.getImdbRating());

            TextView imdbVotes = (TextView) getView().findViewById(R.id.imdbVotes);
            imdbVotes.setText(movieSummary.getImdbVotes());

            TextView year = (TextView) getView().findViewById(R.id.year);
            year.setText(movieSummary.getYear());

            TextView genre = (TextView) getView().findViewById(R.id.genre);
            genre.setText(movieSummary.getGenre());

            TextView rated = (TextView) getView().findViewById(R.id.rated);
            rated.setText(movieSummary.getRated());

            TextView runtime = (TextView) getView().findViewById(R.id.runtime);
            runtime.setText(movieSummary.getRuntime());

            TextView released = (TextView) getView().findViewById(R.id.released);
            released.setText(movieSummary.getReleased());

            TextView director = (TextView) getView().findViewById(R.id.director);
            director.setText(movieSummary.getDirector());

            TextView writer = (TextView) getView().findViewById(R.id.writer);
            writer.setText(movieSummary.getWriter());

            TextView actors = (TextView) getView().findViewById(R.id.actors);
            actors.setText(movieSummary.getActors());

            TextView plot = (TextView) getView().findViewById(R.id.plot);
            plot.setText(movieSummary.getPlot());

            TextView country = (TextView) getView().findViewById(R.id.country);
            country.setText(movieSummary.getCountry());

            TextView awards = (TextView) getView().findViewById(R.id.awards);
            awards.setText(movieSummary.getAwards());

            TextView metascore = (TextView) getView().findViewById(R.id.metascore);
            metascore.setText(movieSummary.getMetascore());

            TextView language = (TextView) getView().findViewById(R.id.language);
            language.setText(movieSummary.getLanguage());

            Picasso.with(getContext())
                    .load(movieSummary.getPoster())
                    .into((ImageView) getView().findViewById(R.id.poster));



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


            if (response == null) {
                return null;
            } else {
                return parseSingleObject(response);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {


        super.onPause();
    }

    private MovieSummary parseSingleObject(JSONObject post) throws JSONException {

        MovieSummary item = new MovieSummary();



        String title = post.optString("title");
        item.setTitle(title);

        StringBuilder sb = new StringBuilder();
        JSONArray genre = post.optJSONArray("genre");
        for(int i = 0; i < genre.length(); i++) {
            String genres = (String) genre.get(i);
            sb.append(genres + " ");
        }
        item.setGenre(sb);



        StringBuilder sbActor = new StringBuilder();
        JSONArray actor = post.optJSONArray("actors");
        for(int i = 0; i < actor.length(); i++) {
            String actors = (String) actor.get(i);
            sbActor.append(actors + " ");
        }
        item.setActors(sbActor);

        StringBuilder sbLanguage = new StringBuilder();
        JSONArray language = post.optJSONArray("language");
        for(int i = 0; i < language.length(); i++) {
            String languages = (String) language.get(i);
            sbLanguage.append(languages + " ");
        }


        item.setLanguage(sbLanguage);


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

        String rated = post.optString("rated");
        item.setRated(rated);

        String release = post.optString("released");
        item.setReleased(release);

        String director = post.optString("director");
        item.setDirector(director);


        String writer = post.optString("writer");
        item.setWriter(writer);

        String country = post.optString("country");
        item.setCountry(country);

        String runtime = post.optString("runtime");
        item.setRuntime(runtime);

        String metascore = post.optString("metascore");
        item.setMetascore(metascore);

        String awards = post.optString("awards");
        item.setAwards(awards);

        String plot = post.optString("plot");
        item.setPlot(plot);

        return item;
    }
}




