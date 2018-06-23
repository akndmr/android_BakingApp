package com.example.android.android_bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @SerializedName("id")
    private final int mRecipeId;
    @SerializedName("name")
    private final String mRecipeName;
    @SerializedName("ingredients")
    private final ArrayList<Ingredient> mIngredients;
    @SerializedName("steps")
    private final ArrayList<Step> mSteps;
    @SerializedName("servings")
    private final int mServings;
    @SerializedName("image_url")
    private final String mImageUrl;

    public Recipe(int recipeId, String recipeName, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String imageUrl) {
        mRecipeId = recipeId;
        mRecipeName = recipeName;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImageUrl = imageUrl;
    }

    private Recipe(Parcel in) {
        mRecipeId = in.readInt();
        mRecipeName = in.readString();
        mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        mSteps = in.createTypedArrayList(Step.CREATOR);
        mServings = in.readInt();
        mImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mRecipeId);
        out.writeString(mRecipeName);
        out.writeList(mIngredients);
        out.writeList(mSteps);
        out.writeInt(mServings);
        out.writeString(mImageUrl);
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
