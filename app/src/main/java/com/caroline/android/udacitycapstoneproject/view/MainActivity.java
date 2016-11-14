package com.caroline.android.udacitycapstoneproject.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.caroline.android.udacitycapstoneproject.MovieApplication;
import com.caroline.android.udacitycapstoneproject.R;
import com.caroline.android.udacitycapstoneproject.concurrency.UiExecutor;
import com.caroline.android.udacitycapstoneproject.model.ConnectivityProvider;
import com.caroline.android.udacitycapstoneproject.model.MovieDao;
import com.caroline.android.udacitycapstoneproject.model.MovieItem;
import com.caroline.android.udacitycapstoneproject.presenter.MainPresenter;
import com.caroline.android.udacitycapstoneproject.view.loaders.LoaderActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executors;

;

/**
 * Created by carolinestewart on 6/7/16.
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MainPresenter.View {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    protected GoogleApiClient googleApiClient;
    protected static final String TAG = "MainActivity";
    private boolean twoPane;
    private android.support.v7.widget.RecyclerView recyclerView;
    private TextView emptyStateTextView;
    private Tracker tracker;
    private MovieListAdapter adapter;
    private MainPresenter presenter;
    @Inject MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_list);

        MovieApplication application = (MovieApplication) getApplication();
        application.getComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tracker = application.getDefaultTracker();

        buildGoogleApiClient();

        if (findViewById(R.id.detail_container_large) != null) {
            twoPane = true;
        }

        adapter = new MovieListAdapter(this, new MovieListAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClicked(MovieItem movieItem) {
                presenter.showMovie(movieItem);

            }
        });
        recyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        ConnectivityProvider connectivityProvider = new ConnectivityProvider() {
            @Override
            public boolean isConnected() {
                ConnectivityManager cm =
                        (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null &&
                       activeNetwork.isConnectedOrConnecting();
            }
        };

        presenter = new MainPresenter(Executors.newSingleThreadExecutor(), new UiExecutor(new Handler()), connectivityProvider, movieDao);
        presenter.attach(this);
        presenter.present();
    }

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
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fetch_button:
                presenter.handleShowTheatersRequest();
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
                presenter.updateLocation(String.valueOf(lastLocation.getLatitude()), String.valueOf(lastLocation.getLongitude()));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
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

    @Override
    public void showMovieItems(List<MovieItem> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void showDisconnectedWhenDisplayList() {
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        emptyStateTextView.setText(R.string.emptytext);
    }

    @Override
    public void showMovie(MovieItem movieItem) {
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

    @Override
    public void showDisconnectWhenSelected() {
        Toast.makeText(this, "We are disconnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTheatersNearMe(String url) {
        Uri location = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        String title = getString(R.string.select_map);
        Intent chooser = Intent.createChooser(mapIntent, title);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}



