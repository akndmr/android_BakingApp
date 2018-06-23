package com.example.android.android_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_bakingapp.model.Step;
import com.example.android.android_bakingapp.utils.Utils;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnStepInteractionListener {
    private Step mStep;
    private ArrayList<Step> mSteps = new ArrayList<>();



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
            if (intent.hasExtra(Utils.BUNDLE_STEPS)) {
                mSteps = intent.getParcelableArrayListExtra(Utils.BUNDLE_STEPS);
            }
        }
    }


    @Override
    public void onStepInteraction(Step step) {
        mStep = step;
        Bundle arguments = new Bundle();
        arguments.putParcelable(Utils.BUNDLE_STEP, step);
        arguments.putParcelableArrayList(Utils.BUNDLE_STEPS, mSteps);
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.recipe_desc_container, fragment).addToBackStack(null).commit();

    }
}


