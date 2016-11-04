package com.caroline.android.udacitycapstoneproject.ContentProvider;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.caroline.android.udacitycapstoneproject.DataUtil;
import com.caroline.android.udacitycapstoneproject.MovieItem;
import com.caroline.android.udacitycapstoneproject.R;

import java.util.List;
import java.util.Random;

/**
 * Created by carolinestewart on 11/3/16.
 */
public class WidgetService extends IntentService {
    public final static String TAG = WidgetService.class.getSimpleName();
    private static final String EXTRA_WIDGET_IDS = "extra_widgetIds";

    public WidgetService() {
        super(TAG);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        // go fetch top 100
        List<MovieItem> movies = DataUtil.fetchMovieItems();

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
            remoteViews.setTextViewText(R.id.widget_year, movieItem.getDirector());
            remoteViews.setTextViewText(R.id.widget_director, movieItem.getYear());



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
