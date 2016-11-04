package com.caroline.android.udacitycapstoneproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.caroline.android.udacitycapstoneproject.loaders.LoaderActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

/**
 * Created by carolinestewart on 6/7/16.
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private boolean twoPane;
    private android.support.v7.widget.RecyclerView recyclerView;
    private TextView emptyStateTextView;
    protected static final String TAG = "MainActivity";

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    protected GoogleApiClient googleApiClient;
    private String uri;

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);


        //start the shared tracker and obtain the instance
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        tracker = application.getDefaultTracker();


        buildGoogleApiClient();


        //check if the screen is large or small
        if (findViewById(R.id.detail_container_large) != null) {
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
                                .replace(R.id.detail_container_large, fragment)
                                .commit();

                    } else {
                        String key = movieItem.getImdbId();
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("key", key);
                        startActivity(intent);
                    }
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Load Detail Screen")
                            .build());
                }
            });
            recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.movieList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

            //if it's not connected to the internet, let the user know

        } else {

            emptyStateTextView = (TextView) findViewById(R.id.empty_view);

            emptyStateTextView.setText(R.string.emptytext);
            Toast.makeText(this, "No internetconnection", Toast.LENGTH_LONG).show();



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


    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    protected void onStop() {

        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.fetch_button:
                Uri location = Uri.parse(uri + "?q=theater");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                String title = getString(R.string.select_map);
                Intent chooser = Intent.createChooser(mapIntent, title);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, LoaderActivity.class);
                startActivity(intent);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String name = getLocalClassName();
        Log.i(TAG, "Setting screen name: " + name);
        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }


        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                String longitude = String.valueOf(lastLocation.getLongitude());
                String latitude = String.valueOf(lastLocation.getLatitude());
                uri = String.format(Locale.ENGLISH, "geo:" + latitude + ", " + longitude);
            }
//
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");

        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());


    }


}



