package com.example.android.android_bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.android.android_bakingapp.utils.Utils;


/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String widgetRecipeLabel, String widgetRecipeIngredients, int[] appWidgetIds) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        Utils.getRecipeIngredients(context, widgetRecipeLabel, widgetRecipeIngredients);
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

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        IngredientsDisplayService.startActionUpdateRecipeWidget(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        IngredientsDisplayService.startActionUpdateRecipeWidget(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

