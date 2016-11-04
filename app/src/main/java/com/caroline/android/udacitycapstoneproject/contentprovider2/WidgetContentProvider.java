package com.caroline.android.udacitycapstoneproject.contentprovider2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.caroline.android.udacitycapstoneproject.DataUtil;
import com.caroline.android.udacitycapstoneproject.MovieItem;

import java.util.List;

/**
 * Created by carolinestewart on 11/3/16.
 */
public class WidgetContentProvider extends ContentProvider {
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    public static final String AUTHORITY = "com.caroline.android.udacitycapstoneproject.contentprovider2.WidgetContentProvider";
    public static final String TABLE_TOP100 = "top100";

    static {
        sURIMatcher.addURI(AUTHORITY, TABLE_TOP100, 0);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // go fetch top 100
        final List<MovieItem> movies = DataUtil.fetchMovieItems();

        Cursor cursor = new MovieItemCursor(movies);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/com.caroline.android.udacitycapstoneproject.MediaItem";

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
