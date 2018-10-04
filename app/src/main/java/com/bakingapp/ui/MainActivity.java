package com.bakingapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bakingapp.Adapter.RecipesAdapter;
import com.bakingapp.AppConstants;
import com.bakingapp.IdlingResources.SimpleIdlingResource;
import com.bakingapp.Network.RetrofitAPIInterface;
import com.bakingapp.Network.RetrofitClient;
import com.bakingapp.POJO.RecipeResults;
import com.bakingapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeClickListener {

    private RetrofitAPIInterface retrofitAPIInterface;
    private RecipesAdapter recipesAdapter;
    private RecyclerView recipeRecyclerView;
    private ProgressDialog progressDialog;

    private List<RecipeResults> recipeResults;

    private Parcelable layoutManagerSavedstate;
    private final String SAVED_LAYOUT_MANAGER = "layoutManager";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecyclerView = (RecyclerView) findViewById(R.id.recipesRecyclerView);
        retrofitAPIInterface = RetrofitClient.getRetorfitAPIInterface(AppConstants.baseURL);
        showRecipesInRecyclerView();

        getIdlingResource();

        getBakingData(mIdlingResource);
    }

    private void getBakingData(final SimpleIdlingResource simpleIdlingResource) {

        if (simpleIdlingResource != null) {
            simpleIdlingResource.setIdleState(false);
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching recipes");
        progressDialog.show();

        Call<ArrayList<RecipeResults>> bakingResultsCall = retrofitAPIInterface.getBakingJson();
        bakingResultsCall.enqueue(new Callback<ArrayList<RecipeResults>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeResults>> call, Response<ArrayList<RecipeResults>> response) {
                if (response.code() == 200) {
                    Log.d("BakingDataGetResponse", response.body().toString());
                    dismissProgressDialog();
                    Log.d("BakingDataGetResponse1", response.body().get(0).getName());
                    recipeResults = response.body();
                    recipesAdapter.setRecipeData(recipeResults);
                    restoreLayoutState();
                    if (simpleIdlingResource != null) {
                        simpleIdlingResource.setIdleState(true);
                    }
                    recipesAdapter.notifyDataSetChanged();
                } else {
                    dismissProgressDialog();
                    Toast.makeText(getApplicationContext(), "Call failed in success method", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeResults>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Call failed in failure method", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showRecipesInRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipesAdapter = new RecipesAdapter(this);
        recipeRecyclerView.setAdapter(recipesAdapter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            layoutManagerSavedstate = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, recipeRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void restoreLayoutState() {
        if (layoutManagerSavedstate != null) {
            recipeRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedstate);
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(AppConstants.RECIPE_DETAILS_INTENT, (Serializable) recipeResults.get(position));
        this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

}
