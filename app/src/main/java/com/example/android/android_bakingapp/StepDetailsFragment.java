package com.example.android.android_bakingapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.android_bakingapp.model.Step;
import com.example.android.android_bakingapp.utils.Utils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepDetailsFragment.OnStepInteractionListener} interface
 * to handle interaction events.
 */
public class StepDetailsFragment extends Fragment {

    private static final String BUNDLE_STEP = "step";

    @BindView(R.id.step_number)
    TextView mStepNumberTV;
    @BindView(R.id.step_short_desc_tv)
    TextView mStepShortDescTV;
    @BindView(R.id.step_desc_tv)
    TextView mStepDescTV;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_iv)
    ImageView mStepImageView;
    @BindView(R.id.short_desc_container)
    LinearLayout mDescContainer;
    @BindView(R.id.prev_button)
    Button mButtonPrev;
    @BindView(R.id.next_button)
    Button mButtonNext;


    /*    @BindView(R.id.pb_loading_indicator)
        ProgressBar mLoadingIndicator;
        @BindView(R.id.tv_error_message_display)
        TextView mErrorMessageDisplay;
    */
    private Context mContext;
    private Step mStep;
    private ArrayList<Step> mSteps;
    private Parcelable mSavedRecyclerLayoutState;
    private SimpleExoPlayer mExoPlayer;
    //   private boolean mPlayWhenReady = false;
    private long mCurrentPosition;

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getContext();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_STEP, mStep);
        outState.putLong("exoplayer_position", mCurrentPosition);
        //   outState.putBoolean("exoplayer_ready", mPlayWhenReady);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getLong("exoplayer_position");
            if (savedInstanceState.containsKey(BUNDLE_STEP)) {
                mStep = savedInstanceState.getParcelable(BUNDLE_STEP);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);


        Intent intent = getActivity().getIntent();

        if (intent != null) {
            if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_STEP)) {
                mStep = savedInstanceState.getParcelable(BUNDLE_STEP);
            } else {
                if (intent.hasExtra(Utils.BUNDLE_STEP)) {
                    mStep = intent.getParcelableExtra(Utils.BUNDLE_STEP);
                    mSteps = intent.getParcelableArrayListExtra(Utils.BUNDLE_STEPS);
                } else {
                    mStep = getArguments().getParcelable(Utils.BUNDLE_STEP);
                }
            }
        }
        ButterKnife.bind(this, rootView);

        mButtonPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mStep.getStepId() > 0) {

                    mStep = mSteps.get(mStep.getStepId() - 1);
                    Toast.makeText(getActivity(), "Button1 clicked." + mStep.getStepId(), Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(getActivity(), "Button1 clicked." + mStep.getStepId(), Toast.LENGTH_SHORT).show();

                } else {
                    mButtonNext.setClickable(false);
                }

            }
        });


        StringTokenizer tokens = new StringTokenizer(mStep.getStepDesc(), ".");
        String stepNumber = tokens.nextToken() + ". ";
        //if there is no stepNumber aka the length of first word is more than 4 digits
        if (stepNumber.length() > 4) {
            stepNumber = " ";
        }

        mStepNumberTV.setText(stepNumber);
        mStepShortDescTV.setText(mStep.getStepShortDesc());
        mStepDescTV.setText(mStep.getStepDesc());

        if (!mStep.getVideoUrl().isEmpty()) {
            mStepImageView.setVisibility(View.INVISIBLE);
            // Initialize the player.
            initializePlayer(Uri.parse(mStep.getVideoUrl()));
        } else if (!mStep.getThumbnailUrl().isEmpty()) {
            mPlayerView.setVisibility(View.INVISIBLE);
            Picasso.with(getActivity()).
                    load(mStep.getThumbnailUrl()).placeholder(R.drawable.bowl).error(R.drawable.bowl).into(mStepImageView);
            mStepImageView.setVisibility(View.VISIBLE);
        }


        return rootView;
    }


    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mPlayerView.setVisibility(View.VISIBLE);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity().getBaseContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);


            if (mCurrentPosition != C.TIME_UNSET) {
                mExoPlayer.seekTo(mCurrentPosition);
            }
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            mCurrentPosition = mExoPlayer.getCurrentPosition();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer != null) {
            mExoPlayer.seekTo(0, mCurrentPosition);
        }

    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mCurrentPosition = mExoPlayer.getCurrentPosition();
            releasePlayer();

        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            mCurrentPosition = mExoPlayer.getCurrentPosition();
            releasePlayer();

        }
    }


}
