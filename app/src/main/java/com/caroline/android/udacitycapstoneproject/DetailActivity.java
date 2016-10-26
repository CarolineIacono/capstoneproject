package com.caroline.android.udacitycapstoneproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by carolinestewart on 9/15/16.
 */
public class DetailActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_screen);
//
//        Intent intent = getIntent();
//        String key = intent.getExtras().getString("key");

        MovieSummaryFragment fragment = new MovieSummaryFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();


    }

}













