package com.example.android.android_bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.android_bakingapp.adapter.RecipesAdapter;
import com.example.android.android_bakingapp.model.Recipe;
import com.example.android.android_bakingapp.rest.ApiClient;
import com.example.android.android_bakingapp.rest.ApiInterface;
import com.example.android.android_bakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragment extends Fragment implements RecipesAdapter.RecipesAdapterOnClickHandler {
    private static final String TAG = RecipesFragment.class.getSimpleName();
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler.layout";

    @BindView(R.id.recipes_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;

    private Context mContext;
    private ArrayList<Recipe> mRecipes;
    private RecipesAdapter mRecipesAdapter;
    private Parcelable mSavedRecyclerLayoutState;
    private OnRecipeInteractionListener mListener;
    private GridLayoutManager mLayoutManager;

    public RecipesFragment() {
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
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(Utils.BUNDLE_RECIPES, mRecipes);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Utils.BUNDLE_RECIPES)) {
                mRecipes = savedInstanceState.getParcelableArrayList(Utils.BUNDLE_RECIPES);
            }
            if (savedInstanceState.containsKey(BUNDLE_RECYCLER_LAYOUT)) {
                mSavedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
                mLayoutManager.onRestoreInstanceState(mSavedRecyclerLayoutState);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, rootView);

        mRecipesAdapter = new RecipesAdapter(mContext, this);

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(Utils.BUNDLE_RECIPES);
            mSavedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }

        mLayoutManager = new GridLayoutManager(mContext, getResources().getInteger(R.integer.no_of_columns));

        /* Association of the LayoutManager with the RecyclerView */
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*
         * Setting to improve performance when changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        recipesRequest();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void recipesRequest() {

        mLoadingIndicator.setVisibility(View.VISIBLE);

        if (mRecipes == null) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<Recipe>> call = apiService.getRecipes();
            call.enqueue(new Callback<List<Recipe>>() {

                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {

                    mRecipes = (ArrayList<Recipe>) response.body();
                    Log.d(TAG, "Number of results received: " + mRecipes.size());
                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                    if (mRecipes != null) {
                        mRecipesAdapter.setRecipesData(mRecipes);

                        /* Setting the adapter attaches it to the RecyclerView in our layout. */
                        mRecyclerView.setAdapter(mRecipesAdapter);

                        showDataView();
                    } else {
                        showErrorMessage();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    showErrorMessage();
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        } else {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mRecipesAdapter.setRecipesData(mRecipes);
            /* Setting the adapter attaches it to the RecyclerView in the layout. */
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
            mRecyclerView.setAdapter(mRecipesAdapter);
            showDataView();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnRecipeInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the movies are visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Recipe recipe) {
        mListener.onRecipeInteraction(recipe);
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
    public interface OnRecipeInteractionListener {

        void onRecipeInteraction(Recipe recipe);
    }
}
