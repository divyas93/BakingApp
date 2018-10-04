package com.bakingapp.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bakingapp.AppConstants;
import com.bakingapp.POJO.RecipeIngredients;
import com.bakingapp.POJO.RecipeSteps;
import com.bakingapp.R;

import java.util.List;

public class RecipeIngredientsAndStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnFragmentInteractionListener, RecipeIngredientsFragment.OnFragmentInteractionListener {

    private List<RecipeIngredients> recipeIngredients;
    private List<RecipeSteps> recipeSteps;
    private int recipeStepPosition;

    private Parcelable layoutManagerSavedstate;
    private final String SAVED_LAYOUT_MANAGER = "layoutManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients_and_steps);

        if (getIntent().hasExtra(AppConstants.INGREDIENTS_INTENT)) {
            recipeIngredients = (List<RecipeIngredients>) getIntent().getSerializableExtra(AppConstants.INGREDIENTS_INTENT);
            RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipeIngredients);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipeIngredientsContainer, recipeIngredientsFragment)
                    .commit();
        }

        if (getIntent().hasExtra(AppConstants.STEPS_INTENT)) {
            recipeSteps = (List<RecipeSteps>) getIntent().getSerializableExtra(AppConstants.STEPS_INTENT);
            recipeStepPosition = getIntent().getIntExtra(AppConstants.STEPS_INTENT_POSITION, 0);
            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipeSteps.get(recipeStepPosition));

            if (recipeStepPosition + 1 == recipeSteps.size()) {
                recipeStepsFragment.setHideNextButtonVisibility(true);
            }
            else {
                recipeStepsFragment.setHideNextButtonVisibility(false);
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipeIngredientsContainer, recipeStepsFragment)
                    .commit();
        }
    }

    @Override
    public void setActionBar(String appTitle) {
        getSupportActionBar().setTitle(appTitle);
    }

    //For non tablet device
    @Override
    public void onPreviousClicked(int stepId) {
        if (stepId >= 1 && stepId < recipeSteps.size()) {

            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipeSteps.get(stepId-1));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipeIngredientsContainer, recipeStepsFragment)
                    .commit();
        }
    }

    //For non tablet device
    @Override
    public boolean onNextClicked(int stepId) {
        if (stepId >= 0 && stepId < recipeSteps.size()) {

            RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.newInstance(recipeSteps.get(stepId+1));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipeIngredientsContainer, recipeStepsFragment)
                    .commit();
        }

        if (((stepId + 2) >= recipeSteps.size())) {
            return true;
        } else return false;
    }

}
