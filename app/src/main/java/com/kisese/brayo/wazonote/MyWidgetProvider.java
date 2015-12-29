package com.kisese.brayo.wazonote;

/**
 * Created by Brayo on 7/23/2014.
 */
import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {


    String message;
    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {


        SharedPreferences sharedPref = context.getSharedPreferences("sticky", context.MODE_PRIVATE);
        message = sharedPref.getString("note", "");

        for(int i=0; i<appWidgetIds.length; i++){
            int currentWidgetId = appWidgetIds[i];
            String url = "http://www.tutorialspoint.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
            PendingIntent pending = PendingIntent.getActivity(context, 0,
                    intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            views.setTextViewText(R.id.update,message);


            appWidgetManager.updateAppWidget(currentWidgetId,views);
          //  Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }


    }
}