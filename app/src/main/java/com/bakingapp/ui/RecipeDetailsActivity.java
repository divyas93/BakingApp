package com.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bakingapp.AppConstants;
import com.bakingapp.POJO.RecipeIngredients;
import com.bakingapp.POJO.RecipeResults;
import com.bakingapp.POJO.RecipeSteps;
import com.bakingapp.R;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.RecipeDetailClickListener,
        RecipeIngredientsFragment.OnFragmentInteractionListener, RecipeStepsFragment.OnFragmentInteractionListener {

    private RecipeResults recipeResults;
    private List<RecipeSteps> recipeSteps;
    private List<RecipeIngredients> recipeIngredients;
    private ImageView defaultImage;
    private FragmentManager fragmentManager;
    private FrameLayout frameLayout;
    private boolean isTwoPane;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
//        defaultImage = (ImageView) findViewById(R.id.noDetail);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        if (getIntent().hasExtra(AppConstants.RECIPE_DETAILS_INTENT)) {
            recipeResults = (RecipeResults) getIntent().getSerializableExtra(AppConstants.RECIPE_DETAILS_INTENT);

            if (recipeResults != null) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(recipeResults.getName());
                }

                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeIngredients = recipeResults.getRecipeIngredients();
                recipeDetailsFragment.setRecipeIngredientsData(recipeIngredients);
                recipeSteps = recipeResults.getRecipeSteps();
                recipeDetailsFragment.setRecipeStepsData(recipeSteps);

                fragmentManager = getSupportFragmentManager();


                if (findViewById(R.id.instructionContainerTwoPane) != null) {
                    isTwoPane = true;

                    fragmentManager.beginTransaction()
                            .add(R.id.recipeDetailsTwoPane, recipeDetailsFragment)
                            .commit();


                    RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipeIngredients);
                    fragmentManager.beginTransaction()
                            .add(R.id.instructionContainerTwoPane, recipeIngredientsFragment)
                            .commit();

                } else {
                    isTwoPane = false;
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, recipeDetailsFragment)
                            .commit();
                }
            }
        } else {
            frameLayout.setVisibility(View.GONE);
//            defaultImage.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void recipeIngredientsClick() {
        if (isTwoPane) {

            RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipeIngredients);
            fragmentManager.beginTransaction()
                    .replace(R.id.instructionContainerTwoPane, recipeIngredientsFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeIngredientsAndStepsActivity.class);
            intent.putExtra(AppConstants.INGREDIENTS_INTENT, (Serializable) recipeIngredients);
            startActivity(intent);
        }

    }

    @Override
    public void recipeStepsClick(int position) {
        if (isTwoPane) {
            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipeSteps.get(position));

            if (position + 1 == recipeSteps.size()) {
                recipeStepsFragment.setHideNextButtonVisibility(true);
            } else {
                recipeStepsFragment.setHideNextButtonVisibility(false);
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.instructionContainerTwoPane, recipeStepsFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeIngredientsAndStepsActivity.class);
            intent.putExtra(AppConstants.STEPS_INTENT_POSITION, position);
            intent.putExtra(AppConstants.STEPS_INTENT, (Serializable) recipeSteps);
            startActivity(intent);
        }

    }

    @Override
    public void setActionBar(String appTitle) {
        getSupportActionBar().setTitle(appTitle);
    }

    //For tablet device
    @Override
    public void onPreviousClicked(int stepId) {

        if (isTwoPane) {
            if (stepId >= 1 && stepId < recipeSteps.size()) {

                RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipeSteps.get(stepId - 1));
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.instructionContainerTwoPane, recipeStepsFragment)
                        .commit();
            }
        }

    }

    //For tablet device
    @Override
    public boolean onNextClicked(int stepId) {
        if (isTwoPane) {
            if (stepId >= 0 && stepId < recipeSteps.size()) {

                RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipeSteps.get(stepId + 1));
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.instructionContainerTwoPane, recipeStepsFragment)
                        .commit();
            }

            if (((stepId + 2) >= recipeSteps.size())) {
                return true;
            } else return false;
        } else return false;
    }
}
