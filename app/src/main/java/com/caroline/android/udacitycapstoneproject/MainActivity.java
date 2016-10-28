package com.caroline.android.udacitycapstoneproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by carolinestewart on 6/7/16.
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private boolean twoPane;
    private android.support.v7.widget.RecyclerView recyclerView;
    private TextView emptyStateTextView;
    protected GoogleApiClient googleApiClient;
    protected static final String TAG = "MainActivity";

    protected Location lastLocation;
    protected String latitudeLabel;
    protected String longitudeLabel;
    protected TextView latitudeText;
    protected TextView longitudeText;
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

        //start the shared tracker and obtain the instance
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        tracker = application.getDefaultTracker();

        //set lat and long for the Location service, call the API client
        latitudeLabel = getResources().getString(R.string.latitude_label);
        longitudeLabel = getResources().getString(R.string.longitude_label);
        latitudeText = (TextView) findViewById((R.id.latitude_text));
        longitudeText = (TextView) findViewById((R.id.longitude_text));

        buildGoogleApiClient();


        //check if the screen is large or small
        if (findViewById(R.id.detail_container) != null) {
            twoPane = true;
        }


        //check if the device is connected to the internet
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //if it's connected, set the adapter and load depending on whether it's in single or dual pane mode

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

            //if it's not connected to the internet, let the user know

        } else {

            emptyStateTextView = (TextView) findViewById(R.id.empty_view);

            emptyStateTextView.setText(R.string.emptytext);

        }

    }

    //create the GoogleAPIClient for the Location service
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + name);
        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            latitudeText.setText(String.format("%s: %f", latitudeLabel,
                    lastLocation.getLatitude()));
            longitudeText.setText(String.format("%s: %f", longitudeLabel,
                    lastLocation.getLongitude()));
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


