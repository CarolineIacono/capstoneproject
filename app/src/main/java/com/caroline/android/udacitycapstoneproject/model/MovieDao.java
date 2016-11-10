package com.caroline.android.udacitycapstoneproject.model;

import java.util.List;

/**
 * Created by carolinestewart on 11/10/16.
 */
public interface MovieDao {
    List<MovieItem> fetchMovieItems();

    MovieSummary fetchMovieSummary(String urlString);
}
