package com.bakingapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DivyaSethi on 05/09/18.
 */
public class RecipeIngredients implements Serializable{

    @SerializedName("quantity")
    public float quantity;

    @SerializedName("measure")
    public String measure;

    @SerializedName("ingredient")
    public String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
