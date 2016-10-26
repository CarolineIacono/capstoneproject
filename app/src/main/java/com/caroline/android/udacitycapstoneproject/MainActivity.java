package com.caroline.android.udacitycapstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by carolinestewart on 6/7/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean twoPane;
    private android.support.v7.widget.RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);



        RecyclerView.Adapter adapter = new MovieListAdapter(this, new MovieListAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClicked(MovieItem movieItem) {

                if (twoPane) {
                    String key = movieItem.getImdbId();
                    Bundle bundle = new Bundle();
                    bundle.putString("key", key);
                    MovieSummaryFragment fragment = new MovieSummaryFragment();
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();

                } else {
                    String key = movieItem.getImdbId();
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("key", key);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        if (findViewById(R.id.fragment_container) != null) {
            twoPane = true;
        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }


}


