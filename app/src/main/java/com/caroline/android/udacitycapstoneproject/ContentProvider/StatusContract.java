package com.caroline.android.udacitycapstoneproject.ContentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by carolinestewart on 11/3/16.
 */
public class StatusContract {
    public static final String DB_NAME = "moviewidget.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "status";


    public static final String DEFAULT_SORT = Column.TITLE + " DESC";

    //notsure about this
    public static final String AUTHORITY = "com.caroline.android.udacitycapstoneproject.StatusProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                        + "/" + TABLE);
    public static final int STATUS_ITEM = 1;
    public static final int STATUS_DIR = 2;
    public static final String STATUS_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.caroline.android.udacitycapstoneproject.status";
    public static final String STATUS_TYPE_DIR = "vnd.android.cursor.item/vnd.com.caroline.android.udacitycapstoneproject.status";




    public class Column {

    public static final String ID = BaseColumns._ID;
    public static final String TITLE = "title";
    public static final String DIRECTOR = "director";
    public static final String YEAR = "year";
}
}
