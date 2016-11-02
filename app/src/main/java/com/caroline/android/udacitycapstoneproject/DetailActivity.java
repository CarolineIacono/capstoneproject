package com.caroline.android.udacitycapstoneproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by carolinestewart on 9/15/16.
 */
public class DetailActivity extends AppCompatActivity {

    private boolean twoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //check to see if you already have one

        if (getSupportFragmentManager().findFragmentById(R.id.details_container) == null) {


            MovieSummaryFragment fragment = new MovieSummaryFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.details_container, fragment).commit();
        }
    }

    }


















