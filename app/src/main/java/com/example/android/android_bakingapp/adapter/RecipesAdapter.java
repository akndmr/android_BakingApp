package com.example.android.android_bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_bakingapp.R;
import com.example.android.android_bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    private final RecipesAdapterOnClickHandler mClickHandler;
    private final Context mContext;
    private List<Recipe> mRecipes = new ArrayList<>();

    public RecipesAdapter(Context context, RecipesAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, viewGroup, false);

        view.setFocusable(true);
        return new RecipesViewHolder(view, mClickHandler);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {

        Recipe recipe = mRecipes.get(position);

        holder.mRecipeTextView.setText(recipe.getRecipeName());
        holder.mServingsTextView.setText(String.valueOf(recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    /**
     * This method is used to set the recipes on a RecipesAdapter if we've already
     * created one.
     *
     * @param recipesData The new recipe data to be displayed.
     */
    public void setRecipesData(List<Recipe> recipesData) {
        mRecipes = recipesData;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }


    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RecipesAdapterOnClickHandler mClickHandler;
        @BindView(R.id.recipe_tv)
        TextView mRecipeTextView;
        @BindView(R.id.servings_tv)
        TextView mServingsTextView;

        RecipesViewHolder(View view, RecipesAdapterOnClickHandler clickHandler) {

            super(view);
            mClickHandler = clickHandler;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mRecipes.get(adapterPosition));
        }
    }
}





