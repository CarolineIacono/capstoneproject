//package com.caroline.android.udacitycapstoneproject.MovieWidget;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.Context;
//import android.content.Intent;
//import android.widget.RemoteViews;
//
//import com.caroline.android.udacitycapstoneproject.MovieItem;
//import com.caroline.android.udacitycapstoneproject.R;
//
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by carolinestewart on 10/31/16.
// */
//public class MovieWidget extends AppWidgetProvider  {
//    private List<MovieItem> movieViewData;
//    private Context context;
//
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//
//        for (int appWidgetId : appWidgetIds) {
//
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                    R.layout.movie_widget);
//            remoteViews.setTextViewText(R.id.textView, number);
//
//            Intent intent = new Intent(context, MovieWidget.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//        }
//    }
//
//    @Override
//    public void onComplete(List<MovieItem> list) {
//
//    }
//}
//
