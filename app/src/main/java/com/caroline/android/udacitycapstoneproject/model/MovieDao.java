package com.caroline.android.udacitycapstoneproject.model;

import java.util.List;

public interface MovieDao {

    List<MovieItem> fetchMovieItems();

    MovieSummary fetchMovieSummary(String urlString);
}
