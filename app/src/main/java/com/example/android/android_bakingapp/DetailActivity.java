package com.example.android.android_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_bakingapp.model.Step;
import com.example.android.android_bakingapp.utils.Utils;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements StepsFragment.OnStepsInteractionListener, StepDetailsFragment.OnStepInteractionListener {
    private ArrayList<Step> mSteps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setElevation(0f);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        boolean mTwoPane = findViewById(R.id.recipe_details_container) != null;
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Utils.BUNDLE_STEPS)) {
                mSteps = intent.getParcelableArrayListExtra(Utils.BUNDLE_STEPS);


                if (mTwoPane) {
                    if (savedInstanceState == null) {

                        Bundle arguments = new Bundle();
                        arguments.putParcelable(Utils.BUNDLE_STEP, mSteps.get(0));
                        StepDetailsFragment fragment = new StepDetailsFragment();

                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction().add(R.id.recipe_details_container, fragment).addToBackStack(null).commit();

                    }
                }
            }
        }
    }


    @Override
    public void onStepsInteraction(Step step) {

        boolean mTwoPane = findViewById(R.id.recipe_details_container) != null;
        if (!mTwoPane) {

            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(Utils.BUNDLE_STEP, step);
            startActivity(intent);

        } else {
            Bundle arguments = new Bundle();
            arguments.putParcelable(Utils.BUNDLE_STEP, step);
            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_details_container, fragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onStepInteraction(Step step) {

    }
}
