package com.example.android.android_bakingapp.rest;

import com.example.android.android_bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}



