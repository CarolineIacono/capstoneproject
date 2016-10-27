//package com.caroline.android.udacitycapstoneproject;
//
//import android.content.Context;
//import android.support.v4.content.AsyncTaskLoader;
//
//import java.util.List;
//
///**
// * Created by carolinestewart on 10/27/16.
// */
//public class MovieSummaryLoader extends AsyncTaskLoader<List<MovieSummary>> {
//
//    private static final String LOG_TAG = MovieSummaryLoader.class.getName();
//
//    private String mUrl;
//
//
//    public MovieSummaryLoader(Context context, String url) {
//        super(context);
//        mUrl = url;
//    }
//
//    @Override
//    protected void onStartLoading() {
//        forceLoad();
//    }
//
//    /**
//     * This is on a background thread.
//     */
//    @Override
//    public List<MovieSummary> loadInBackground() {
//        if (mUrl == null) {
//            return null;
//        }
//
//        // Perform the network request, parse the response, and extract a list of earthquakes.
//        List<MovieSummary> movieSummaries = DataUtil.fetchEarthquakeData(mUrl);
//        return movieSummaries;
//    }
//}
//
