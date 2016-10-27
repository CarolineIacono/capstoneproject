package com.caroline.android.udacitycapstoneproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by carolinestewart on 6/7/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean twoPane;
    private android.support.v7.widget.RecyclerView recyclerView;
    private TextView emptyStateTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

//        LinearLayout mainView = (LinearLayout) findViewById(R.id.main);
//
//        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
//        mainView.setEmptyView(emptyStateTextView);


        if (findViewById(R.id.detail_container) != null) {
            twoPane = true;
        }

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {


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
                                .replace(R.id.detail_container, fragment)
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

        }
        else {
//            emptyStateTextView.setText("No internet connection");

            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }


}


