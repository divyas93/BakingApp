package com.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.POJO.RecipeIngredients;
import com.bakingapp.R;

import java.util.List;

/**
 * Created by DivyaSethi on 29/09/18.
 */
public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientViewHolder> {

    private List<RecipeIngredients> recipeIngredients;

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_ingredient_view, viewGroup, false);
        RecipeIngredientViewHolder viewHolder = new RecipeIngredientViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder recipeIngredientViewHolder, int i) {
        String ingredientName = recipeIngredients.get(i).getIngredient();
        String ingredientMeasure = recipeIngredients.get(i).getMeasure();
        Float ingredientQuantity = recipeIngredients.get(i).getQuantity();
        String quanity = ingredientQuantity + " " + ingredientMeasure;
        recipeIngredientViewHolder.ingredientTextView.setText(ingredientName);
        recipeIngredientViewHolder.quantityTextView.setText(quanity);
    }

    @Override
    public int getItemCount() {
        if(recipeIngredients!= null) {
            return recipeIngredients.size();
        }
        else {
            return 0;
        }
    }

    class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;
        TextView quantityTextView;
        public RecipeIngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = (TextView) itemView.findViewById(R.id.ingredientTextView);
            quantityTextView = (TextView) itemView.findViewById(R.id.ingredientQuantityTextView);
        }
    }

    public void setIngredientsData(List<RecipeIngredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
