package com.example.android.android_bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_bakingapp.R;
import com.example.android.android_bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private final StepsAdapterOnClickHandler mClickHandler;
    private final Context mContext;
    private List<Step> mSteps = new ArrayList<>();

    public StepsAdapter(Context context, StepsAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_list_item, viewGroup, false);

        view.setFocusable(true);
        return new StepsViewHolder(view, mClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {

        Step step = mSteps.get(position);
        holder.mStepTextView.setText(step.getStepShortDesc());
    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    /**
     * This method is used to set the recipes on a RecipesAdapter if we've already
     * created one.
     *
     * @param data The new recipe data to be displayed.
     */
    public void setData(List<Step> data) {
        mSteps = data;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface StepsAdapterOnClickHandler {
        void onClick(Step step);
    }


    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final StepsAdapterOnClickHandler mClickHandler;
        @BindView(R.id.step_tv)
        TextView mStepTextView;

        StepsViewHolder(View view, StepsAdapterOnClickHandler clickHandler) {

            super(view);
            mClickHandler = clickHandler;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mSteps.get(adapterPosition));
        }
    }
}





