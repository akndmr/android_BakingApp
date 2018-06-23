package com.example.android.android_bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String widgetRecipeLabel, String widgetRecipeIngredients, int[] appWidgetIds) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        views.setTextViewText(R.id.widget_ingredients_label, widgetRecipeLabel);
        views.setTextViewText(R.id.widget_ingredients_tv, widgetRecipeIngredients);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("name", widgetRecipeLabel);
        intent.putExtra("ingredients", widgetRecipeIngredients);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }
}

