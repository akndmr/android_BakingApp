package com.example.android.android_bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class Utils {
    public static final String BUNDLE_RECIPES = "recipes";
    public static final String BUNDLE_RECIPE = "recipe";
    public static final String BUNDLE_STEPS = "steps";
    public static final String BUNDLE_STEP = "step";
    public static final String STEP_ID = "step_id";
    public static final String BUNDLE_INGREDIENTS = "ingredients";

    public static final String STATE_RESUME_WINDOW = "resumeWindow";
    public static final String STATE_RESUME_POSITION = "resumePosition";
    public static final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";


    public static final String RECIPE_LISTING_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static void getRecipeIngredients(@NonNull Context context, String recipe, String ingredients) {
        SharedPreferences prefs = context.getSharedPreferences("recipe_ingredients", Context.MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);

        if (restoredText != null) {
            recipe = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            ingredients = prefs.getString("ingredients", null); //0 is the default value.
        } else {
            recipe = "";
            ingredients = "";
        }


    }
}

