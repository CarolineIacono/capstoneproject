package com.caroline.android.udacitycapstoneproject.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carolinestewart on 11/3/16.
 */
public class WidgetDatabaseHelper extends SQLiteOpenHelper {


    public WidgetDatabaseHelper(Context context) {
        super(context,StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s primary key, %s text, %s text, %text)",
                StatusContract.TABLE,
                StatusContract.Column.ID,
                    StatusContract.Column.DIRECTOR,
                StatusContract.Column.TITLE,
                StatusContract.Column.YEAR);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + StatusContract.TABLE);
        onCreate(db);
    }
}
