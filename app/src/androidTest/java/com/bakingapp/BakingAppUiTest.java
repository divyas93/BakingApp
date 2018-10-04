package com.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by DivyaSethi on 02/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppUiTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActiveTestRule = new ActivityTestRule<>(MainActivity.class);

    private final String PACKAGE_NAME = "com.bakingapp";

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mainActivityActiveTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void uiTest() {
        EspressoUtils.checkIsDisplayed(R.id.recipesRecyclerView);
//        EspressoUtils.checkIsDisplayed(R.id.recipeImage);
//        EspressoUtils.checkIsDisplayed(R.id.recipeName);
    }

    @Test
    public void clickOnRecipeAndVerifyDetailActivityUiTest() {
        EspressoUtils.clickOnRecyclerView(R.id.recipesRecyclerView, 0);
        EspressoUtils.waitForViewUsingIdlingResource(R.id.recipeIngredientsLabel);
        EspressoUtils.checkIsDisplayedWithText(R.id.recipeIngredientsLabel, R.string.ingredients);
        EspressoUtils.checkIsDisplayedWithText(R.id.recipeStepsLabel, R.string.recipe_steps);
        EspressoUtils.checkIsDisplayed(R.id.recipeDetailRecylerView);
    }

    @Test
    public void verifyRecipeIngredientsUiTest() {
        EspressoUtils.clickOnRecyclerView(R.id.recipesRecyclerView, 0);
        EspressoUtils.waitForViewUsingIdlingResource(R.id.recipeIngredientsLabel);
        EspressoUtils.clickWithId(R.id.recipeIngredientsLabel);
        EspressoUtils.checkIsDisplayed(R.id.ingredientRecyclerView);
        EspressoUtils.checkIsDisplayedWithText(mainActivityActiveTestRule.getActivity().getString(R.string.recipe_ingredients));
    }

    @Test
    public void verifyRecipeStepsUiTest() {
        EspressoUtils.clickOnRecyclerView(R.id.recipesRecyclerView, 0);
        EspressoUtils.waitForViewUsingIdlingResource(R.id.recipeIngredientsLabel);
        EspressoUtils.clickOnRecyclerView(R.id.recipeDetailRecylerView, 0);
        EspressoUtils.checkIsDisplayedWithText(mainActivityActiveTestRule.getActivity().getString(R.string.recipe_steps));
        EspressoUtils.checkIsDisplayed(R.id.stepDesc);
        EspressoUtils.checkIsDisplayed(R.id.rightButton);
//        EspressoUtils.checkIsDisplayed(R.id.playerView);
    }

    @Test
    public void verifyLeftAndRightButtonsAreShownTest() {
        EspressoUtils.clickOnRecyclerView(R.id.recipesRecyclerView, 0);
        EspressoUtils.waitForViewUsingIdlingResource(R.id.recipeIngredientsLabel);
        EspressoUtils.clickOnRecyclerView(R.id.recipeDetailRecylerView, 1);
        EspressoUtils.checkIsDisplayed(R.id.leftbutton);
        EspressoUtils.checkIsDisplayed(R.id.rightButton);
    }


//    @Test
//    public void intentTest() throws Exception {
//        stubAllExternalIntents();
//        EspressoUtils.waitForViewToBeDisplayed(R.id.recipesRecyclerView, 10);
//        onView(withId(R.id.recipesRecyclerView))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
//        Intents.intended(Matchers.allOf( toPackage(PACKAGE_NAME),hasComponent("com.bakingapp.ui.RecipeDetailsActivity")
//        , hasExtraWithKey(AppConstants.RECIPE_DETAILS_INTENT)));
//        Espresso.pressBack();
//    }
//
//    private void stubAllExternalIntents() {
//        Intents.init();
//        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
//        // every test run. In this case all external Intents will be blocked.
//        Intents.intending(isInternal()).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
//    }

    @After
    public void tearDown() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
        mainActivityActiveTestRule.getActivity().finish();
    }

}
