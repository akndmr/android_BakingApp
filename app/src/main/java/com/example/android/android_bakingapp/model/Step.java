package com.example.android.android_bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {
    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @SerializedName("id")
    private final int mStepId;
    @SerializedName("shortDescription")
    private final String mStepShortDesc;
    @SerializedName("description")
    private final String mStepDesc;
    @SerializedName("videoURL")
    private final String mVideoUrl;
    @SerializedName("thumbnailURL")
    private final String mThumbnailUrl;

    public Step(int stepId, String stepShortDesc, String stepDesc, String videoUrl, String thumbnailUrl) {
        mStepId = stepId;
        mStepShortDesc = stepShortDesc;
        mStepDesc = stepDesc;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    private Step(Parcel in) {
        mStepId = in.readInt();
        mStepShortDesc = in.readString();
        mStepDesc = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mStepId);
        out.writeString(mStepShortDesc);
        out.writeString(mStepDesc);
        out.writeString(mVideoUrl);
        out.writeString(mThumbnailUrl);
    }

    public int getStepId() {
        return mStepId;
    }

    public String getStepShortDesc() {
        return mStepShortDesc;
    }

    public String getStepDesc() {
        return mStepDesc;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }
}
