package com.example.android.android_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_bakingapp.model.Step;
import com.example.android.android_bakingapp.utils.Utils;

public class StepDetailsActivity extends AppCompatActivity {
    private Step mStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Utils.BUNDLE_STEP)) {
                mStep = intent.getParcelableExtra(Utils.BUNDLE_STEP);
            }
        }


    }


}


