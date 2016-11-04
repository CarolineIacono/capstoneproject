package com.caroline.android.udacitycapstoneproject.contentprovider2;

import android.database.AbstractCursor;
import android.provider.BaseColumns;

import com.caroline.android.udacitycapstoneproject.MovieItem;

import java.util.List;

public class MovieItemCursor extends AbstractCursor {
    public static final String ID = BaseColumns._ID;
    public static final String TITLE = "title";
    public static final String DIRECTOR = "director";
    public static final String YEAR = "year";

    private static String[] COLUMNS = new String[] {
            ID,
            TITLE,
            DIRECTOR,
            YEAR
    };

    private final List<MovieItem> movies;

    public MovieItemCursor(List<MovieItem> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public String[] getColumnNames() {
        return COLUMNS;
    }

    @Override
    public String getString(int column) {

        MovieItem currentItem = movies.get(getPosition());
        switch(column) {
            case 0:
                return currentItem.getImdbId();
            case 1:
                return currentItem.getTitle();
            case 2:
                return currentItem.getDirector();
            case 3:
                return currentItem.getYear();
        }
        return null;
    }

    @Override
    public short getShort(int column) {
        return 0;
    }

    @Override
    public int getInt(int column) {
        return 0;
    }

    @Override
    public long getLong(int column) {
        return 0;
    }

    @Override
    public float getFloat(int column) {
        return 0;
    }

    @Override
    public double getDouble(int column) {
        return 0;
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }
}
