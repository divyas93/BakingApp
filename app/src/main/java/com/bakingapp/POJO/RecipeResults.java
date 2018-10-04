package com.bakingapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DivyaSethi on 04/09/18.
 */
public class RecipeResults implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("servings")
    public int servings;

    @SerializedName("ingredients")
    public List<RecipeIngredients> recipeIngredients = new ArrayList<>();

    @SerializedName("steps")
    public List<RecipeSteps> recipeSteps = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList getRecipeIngredients() {
        return (ArrayList) recipeIngredients;
    }

    public void setRecipeIngredients(List recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public ArrayList getRecipeSteps() {
        return (ArrayList) recipeSteps;
    }

    public void setRecipeSteps(List recipeSteps) {
        this.recipeSteps = recipeSteps;
    }
}
