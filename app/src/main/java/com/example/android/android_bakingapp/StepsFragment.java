package com.example.android.android_bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.android_bakingapp.adapter.StepsAdapter;
import com.example.android.android_bakingapp.model.Ingredient;
import com.example.android.android_bakingapp.model.Step;
import com.example.android.android_bakingapp.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsFragment.OnStepsInteractionListener} interface
 * to handle interaction events.
 */
public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {
    private static final String TAG = RecipesFragment.class.getSimpleName();
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler.layout";


    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTV;

    @BindView(R.id.steps_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;

    private Context mContext;
    private ArrayList<Ingredient> mIngredients = new ArrayList<>();
    private ArrayList<Step> mSteps = new ArrayList<>();
    private String mRecipeName;
    private StepsAdapter mStepsAdapter;
    private Parcelable mSavedRecyclerLayoutState;

    private OnStepsInteractionListener mListener;

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Utils.BUNDLE_STEPS, mSteps);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(Utils.BUNDLE_INGREDIENTS, mIngredients);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(BUNDLE_RECYCLER_LAYOUT)) {
                mSteps = savedInstanceState.getParcelable(Utils.BUNDLE_STEPS);
                mIngredients = savedInstanceState.getParcelableArrayList(Utils.BUNDLE_INGREDIENTS);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);

        mStepsAdapter = new StepsAdapter(mContext, this);

        if (savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(Utils.BUNDLE_STEPS);
            mIngredients = savedInstanceState.getParcelableArrayList(Utils.BUNDLE_INGREDIENTS);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        /* Association of the LayoutManager with the RecyclerView */
        mRecyclerView.setLayoutManager(mLayoutManager);
        /*
         * Setting to improve performance when changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        Intent intent = getActivity().getIntent();

        if (intent != null) {
            if (intent.hasExtra(Utils.BUNDLE_STEPS)) {
                mIngredients = intent.getParcelableArrayListExtra(Utils.BUNDLE_INGREDIENTS);
                mSteps = intent.getParcelableArrayListExtra(Utils.BUNDLE_STEPS);
                mRecipeName = intent.getStringExtra(Utils.RECIPE_NAME);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mRecipeName);

                ButterKnife.bind(getActivity());
            }
        }

        String ingredientsList = ingredientsToString();
        mIngredientsTV.setText(ingredientsList);
        shareRecipeIngredients(ingredientsList);
        Log.v(TAG, "INGREDIENTS = " + ingredientsList);
        mStepsAdapter.setData(mSteps);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mStepsAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            mListener = (OnStepsInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(Step step) {
        mListener.onStepsInteraction(step);
    }

    private String ingredientsToString() {
        StringBuilder builder = new StringBuilder();

        for (Ingredient ingredient : mIngredients) {
            builder.append(String.valueOf(ingredient.getQuantity())).append(" ");
            builder.append(String.valueOf(ingredient.getMeasure())).append("  ");
            builder.append(ingredient.getIngredient()).append("\n");

        }
        return builder.toString();
    }

    private void shareRecipeIngredients(String ingredients) {

        IngredientsDisplayService.startActionDisplayRecipe(getActivity(), mRecipeName, ingredients);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepsInteractionListener {

        void onStepsInteraction(Step step);
    }
}
