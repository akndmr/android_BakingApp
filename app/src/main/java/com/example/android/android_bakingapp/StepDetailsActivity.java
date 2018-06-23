package com.example.android.android_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_bakingapp.model.Step;
import com.example.android.android_bakingapp.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {
    private Step mStep;
    private ArrayList<Step> mSteps = new ArrayList<>();
    @BindView(R.id.prev_button)
    Button mButtonPrev;
    @BindView(R.id.next_button)
    Button mButtonNext;


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

            if (savedInstanceState == null) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(Utils.BUNDLE_STEP, mStep);
                StepDetailsFragment fragment = new StepDetailsFragment();

                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_step_container, fragment).addToBackStack(null).commit();
            }
        }


        ButterKnife.bind(this);
        mButtonPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mStep.getStepId() > 0) {

                    mStep = mSteps.get(mStep.getStepId() - 1);
                    Toast.makeText(getApplicationContext(), "Button1 clicked." + mStep.getStepId(), Toast.LENGTH_SHORT).show();
                    getStep(mStep);


                } else {
                    mButtonPrev.setClickable(false);
                }

            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mStep.getStepId() < mSteps.size() - 1) {
                    mStep = mSteps.get(mStep.getStepId() + 1);
                    Toast.makeText(getApplicationContext(), "Button1 clicked." + mStep.getStepId(), Toast.LENGTH_SHORT).show();
                    getStep(mStep);

                } else {
                    mButtonNext.setClickable(false);
                }

            }
        });

    }


    public void getStep(Step step) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(Utils.BUNDLE_STEP, step);
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_step_container, fragment).addToBackStack(null).commit();

    }


}


