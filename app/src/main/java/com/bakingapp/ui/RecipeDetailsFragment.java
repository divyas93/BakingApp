package com.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.Adapter.RecipeDetailAdapter;
import com.bakingapp.POJO.RecipeIngredients;
import com.bakingapp.POJO.RecipeSteps;
import com.bakingapp.R;

import java.util.List;

/**
 * Created by DivyaSethi on 26/09/18.
 */
public class RecipeDetailsFragment extends Fragment implements RecipeDetailAdapter.RecipeStepClickListener {

    private RecyclerView recipeDetailsRecyclerView;
    private RecipeDetailAdapter recipeDetailAdapter;
    private final String SAVED_LAYOUT_MANAGER = "layoutManager";
    private Parcelable layoutManagerSavedState;
    private List<RecipeSteps> recipeStepsData;
    private List<RecipeIngredients> recipeIngredients;
    private TextView ingredientsTextView;
    private RecipeDetailClickListener recipeDetailClickListener;

    public RecipeDetailsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeDetailClickListener) {
            recipeDetailClickListener = (RecipeDetailClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null) {
//            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
        }

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeDetailsRecyclerView = (RecyclerView) view.findViewById(R.id.recipeDetailRecylerView);
        ingredientsTextView = (TextView) view.findViewById(R.id.recipeIngredientsLabel);

        showRecipeDetailsInRecyclerView();
        loadDataInRecylerView();
        ingredientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDetailClickListener.recipeIngredientsClick();
            }
        });
    }

    private void showRecipeDetailsInRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recipeDetailsRecyclerView.setLayoutManager(layoutManager);
        recipeDetailAdapter = new RecipeDetailAdapter(this);
        recipeDetailsRecyclerView.setAdapter(recipeDetailAdapter);
    }

    private void loadDataInRecylerView() {
        recipeDetailAdapter.setRecipeStepsData(recipeStepsData);
        restoreLayoutState();
        recipeDetailAdapter.notifyDataSetChanged();
    }

    private void restoreLayoutState() {
        if (layoutManagerSavedState != null) {
            recipeDetailsRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, recipeDetailsRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    public void setRecipeStepsData(List<RecipeSteps> recipeStepsData) {
        this.recipeStepsData = recipeStepsData;
    }

    public void setRecipeIngredientsData(List<RecipeIngredients> recipeIngredientsData) {
        this.recipeIngredients = recipeIngredients;
    }

    @Override
    public void recipeStepClick(int position) {
        recipeDetailClickListener.recipeStepsClick(position);
    }

    public interface RecipeDetailClickListener {
        void recipeIngredientsClick();

        void recipeStepsClick(int position);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeDetailClickListener = null;
    }
}
