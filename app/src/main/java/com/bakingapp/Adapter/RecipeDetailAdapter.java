package com.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.POJO.RecipeSteps;
import com.bakingapp.R;

import java.util.List;

/**
 * Created by DivyaSethi on 26/09/18.
 */
public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder> {

    private List<RecipeSteps> recipeStepsData;
    private RecipeStepClickListener recipeStepClickListener;

    public RecipeDetailAdapter(RecipeStepClickListener recipeClickListener) {
        recipeStepClickListener = recipeClickListener;
    }

    public interface RecipeStepClickListener {
        void recipeStepClick(int position);
    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_detail_view, parent, false);
        RecipeDetailViewHolder recipeDetailViewHolder = new RecipeDetailViewHolder(view);
        return recipeDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder holder, int position) {
        holder.detailTextView.setText(recipeStepsData.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(recipeStepsData != null) {
            return recipeStepsData.size();
        }
        else {
            return 0;
        }
    }

    class RecipeDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView detailTextView;
        public RecipeDetailViewHolder(View itemView) {
            super(itemView);
            detailTextView = (TextView) itemView.findViewById(R.id.recipeStepsDesc);
            detailTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recipeStepClickListener.recipeStepClick(getAdapterPosition());
        }
    }

    public void setRecipeStepsData(List<RecipeSteps> recipeStepsData) {
        this.recipeStepsData = recipeStepsData;
    }
}
