package com.caroline.android.udacitycapstoneproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by carolinestewart on 9/15/16.
 */
public class DetailActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);



            MovieSummaryFragment fragment = new MovieSummaryFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();




    }

}













