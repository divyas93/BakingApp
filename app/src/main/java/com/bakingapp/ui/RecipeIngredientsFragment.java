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

import com.bakingapp.Adapter.RecipeIngredientAdapter;
import com.bakingapp.AppConstants;
import com.bakingapp.POJO.RecipeIngredients;
import com.bakingapp.R;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeIngredientsFragment} interface
 * to handle interaction events.
 * Use the {@link RecipeIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeIngredientsFragment extends Fragment {

    private List<RecipeIngredients> recipeIngredients;
    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecyclerView ingredientsRecyclerView;
    private static Parcelable layoutManagerSavedState;

    private RecipeIngredientsFragment.OnFragmentInteractionListener mListener;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecipeIngredientsFragment.
     */
    public static RecipeIngredientsFragment newInstance(List<RecipeIngredients> param1) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.INGREDIENTS_INTENT, (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeIngredients = (List<RecipeIngredients>) getArguments().getSerializable(AppConstants.INGREDIENTS_INTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            layoutManagerSavedState = savedInstanceState.getParcelable(AppConstants.SAVED_POSITION);
        }

        if (mListener != null) {
            mListener.setActionBar(getString(R.string.recipe_ingredients));
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientsRecyclerView = (RecyclerView) view.findViewById(R.id.ingredientRecyclerView);
        setUpRecyclerView();
        loadIngredientsAdapterData();
    }

    private void loadIngredientsAdapterData() {
        recipeIngredientAdapter.setIngredientsData(recipeIngredients);
        if (layoutManagerSavedState != null) {
            restoreLayoutManagerPosition();
        }
        recipeIngredientAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        recipeIngredientAdapter = new RecipeIngredientAdapter();
        ingredientsRecyclerView.setAdapter(recipeIngredientAdapter);
//        recipeIngredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(AppConstants.SAVED_POSITION, ingredientsRecyclerView.getLayoutManager().onSaveInstanceState());
    }


    private void restoreLayoutManagerPosition() {
        if (layoutManagerSavedState != null) {
            ingredientsRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeStepsFragment.OnFragmentInteractionListener) {
            mListener = (RecipeIngredientsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void setActionBar(String appTitle);
    }
}
