package com.example.android.android_bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @SerializedName("quantity")
    private final float mQuantity;
    @SerializedName("measure")
    private final String mMeasure;
    @SerializedName("ingredient")
    private final String mIngredient;


    public Ingredient(int quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    private Ingredient(Parcel in) {
        mQuantity = in.readFloat();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(mQuantity);
        out.writeString(mMeasure);
        out.writeString(mIngredient);
    }

    public float getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }
}
