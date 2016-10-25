package com.caroline.android.udacitycapstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by carolinestewart on 9/15/16.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private String key;
    private static final String DETAIL_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/by_id/";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);


//
//        MovieItem movieItem = (MovieItem) getIntent().getSerializableExtra(EXTRA_MOVIE);
//        DetailFragment detailFragment = new DetailFragment(movieItem);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, detailFragment);
//        fragmentTransaction.commit();


        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        MovieItem movieItem = (MovieItem) args.getSerializable("EXTRA_MOVIE");
        String key = intent.getExtras().getString("key");

//        new DataUtil.MovieFetchTask(DataUtil.GetMovieCallback).execute(DETAIL_URL + key);





//        TextView writer = (TextView) findViewById(R.id.writer);
//        writer.setText(movieItem.getWriter());
//
//        TextView director = (TextView) findViewById(R.id.director);
//        director.setText(movieItem.getDirector());
//
//        TextView actors = (TextView) findViewById(R.id.actors);
//        actors.setText(movieItem.getActors());
//
//        TextView plot = (TextView) findViewById(R.id.plot);
//        plot.setText(movieItem.getPlot());
//
//        TextView language = (TextView) findViewById(R.id.language);
//        language.setText(movieItem.getLanguage());
//
//        TextView country = (TextView) findViewById(R.id.country);
//        country.setText(movieItem.getCountry());
//
//        TextView imbdRating = (TextView) findViewById(R.id.imdbRating);
//        imbdRating.setText(movieItem.getImdbRating());
//
//        TextView awards = (TextView) findViewById(R.id.awards);
//        awards.setText(movieItem.getAwards());
//
//        TextView metascore = (TextView) findViewById(R.id.metascore);
//        metascore.setText(movieItem.getMetascore());
//
//        TextView title = (TextView) findViewById(R.id.title);
//        title.setText(movieItem.getTitle());
//
//        TextView year = (TextView) findViewById(R.id.year);
//        year.setText(movieItem.getYear());










        //grab the views from the detail screen layout (find ViewbyId)
        //view.setMovie title etc.


        }

    }





