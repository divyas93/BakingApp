package com.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.POJO.RecipeResults;
import com.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DivyaSethi on 26/09/18.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<RecipeResults> recipeResults;
    private RecipeClickListener mRecipeClickListener;

    public RecipesAdapter(RecipeClickListener recipeClickListener) {
        mRecipeClickListener = recipeClickListener;
    }


    public interface RecipeClickListener {
        void onRecipeClick(int position);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layout = R.layout.recipe_card_view;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String recipeName = recipeResults.get(position).getName();
        if (recipeName.equalsIgnoreCase("Nutella Pie")) {
            holder.recipeName.setText(recipeName);
            Picasso.get().load(R.drawable.nutellapie).into(holder.recipeView);
        } else if (recipeName.equalsIgnoreCase("Brownies")) {
            holder.recipeName.setText(recipeName);
            Picasso.get().load(R.drawable.brownies1).into(holder.recipeView);
        } else if (recipeName.equalsIgnoreCase("Yellow Cake")) {
            holder.recipeName.setText(recipeName);
            Picasso.get().load(R.drawable.yellowcake).into(holder.recipeView);
        } else if (recipeName.equalsIgnoreCase("Cheesecake")) {
            holder.recipeName.setText(recipeName);
            Picasso.get().load(R.drawable.cheesecake1).into(holder.recipeView);
        } else {
            holder.recipeName.setText("Surprise");
            Picasso.get().load(R.drawable.defaultrecipe).into(holder.recipeView);
        }
    }

    @Override
    public int getItemCount() {
        if (recipeResults != null) {
            return recipeResults.size();
        } else
            return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeView;
        TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeView = (ImageView) itemView.findViewById(R.id.recipeImage);
            recipeName = (TextView) itemView.findViewById(R.id.recipeName);
            recipeView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            mRecipeClickListener.onRecipeClick(getAdapterPosition());
        }
    }

    public void setRecipeData(List<RecipeResults> recipeResults) {
        this.recipeResults = recipeResults;
    }
}
