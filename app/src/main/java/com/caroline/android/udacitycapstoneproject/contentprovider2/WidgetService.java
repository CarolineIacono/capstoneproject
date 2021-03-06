package com.caroline.android.udacitycapstoneproject.contentprovider2;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.caroline.android.udacitycapstoneproject.MovieItem;
import com.caroline.android.udacitycapstoneproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WidgetService extends IntentService {
    public final static String TAG = WidgetService.class.getSimpleName();
    private static final String EXTRA_WIDGET_IDS = "extra_widgetIds";

    public WidgetService() {
        super(TAG);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(WidgetContentProvider.AUTHORITY);
        builder.appendPath(WidgetContentProvider.TABLE_TOP100);

        Uri uri = builder.build();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        List<MovieItem> movies = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            MovieItem item = new MovieItem();
            item.setTitle(cursor.getString(cursor.getColumnIndex(MovieItemCursor.TITLE)));
            item.setImdbRating(cursor.getString(cursor.getColumnIndex(MovieItemCursor.DIRECTOR)));
            item.setYear(cursor.getString(cursor.getColumnIndex(MovieItemCursor.YEAR)));
            movies.add(item);
        }

        // pick a random movie
        Random random = new Random();
        MovieItem movieItem = movies.get(random.nextInt(movies.size()));

        int[] widgetIds = intent.getIntArrayExtra(EXTRA_WIDGET_IDS);

        for (int widgetId : widgetIds) {

            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                    R.layout.movie_widget);

//
//
            remoteViews.setTextViewText(R.id.widget_title, movieItem.getTitle());
            remoteViews.setTextViewText(R.id.widget_year, movieItem.getYear());
            remoteViews.setTextViewText(R.id.widget_director, movieItem.getImdbRating());



            // Register an onClickListener
//            Intent intent = new Intent(context, MovieWidgetProvider.class);
//
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.widget_title, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    public static Intent createIntent(Context context, int[] allWidgetIds) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(EXTRA_WIDGET_IDS, allWidgetIds);
        return intent;
    }
}
