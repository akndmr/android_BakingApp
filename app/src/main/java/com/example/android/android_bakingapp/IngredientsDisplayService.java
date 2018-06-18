package com.example.android.android_bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IngredientsDisplayService extends IntentService {
    private static final String ACTION_DISPLAY_RECIPE = "com.example.android.android_bakingapp.action.display_recipe";
    private static final String ACTION_UPDATE_RECIPE = "com.example.android.android_bakingapp.action.update_recipe";
    private static final String EXTRA_RECIPE_NAME = "com.example.android.android_bakingapp.extra.recipe_name";
    private static final String EXTRA_INGREDIENTS = "com.example.android.android_bakingapp.extra.ingredients";


    public IngredientsDisplayService() {
        super("IngredientsDisplayService");
    }

    /**
     * Starts this service to perform DisplayRecipe action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDisplayRecipe(Context context, String recipeName, String ingredients) {

        Intent intent = new Intent(context, IngredientsDisplayService.class);
        intent.setAction(ACTION_DISPLAY_RECIPE);
        intent.putExtra(EXTRA_RECIPE_NAME, recipeName);
        intent.putExtra(EXTRA_INGREDIENTS, ingredients);
        context.startService(intent);
    }


    public static void startActionUpdateRecipeWidget(Context context) {

        Intent intent = new Intent(context, IngredientsDisplayService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DISPLAY_RECIPE.equals(action)) {
                final String recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
                final String ingredients = intent.getStringExtra(EXTRA_INGREDIENTS);
                handleActionDisplayRecipe(recipeName, ingredients);
            }

        }
    }


    /**
     * Handle action DisplayRecipe in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDisplayRecipe(String recipeName, String ingredients) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        //Now update all widgets
        IngredientsWidgetProvider.updateAppWidget(this, appWidgetManager, recipeName, ingredients, appWidgetIds);
    }


}


