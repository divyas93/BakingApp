package com.bakingapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DivyaSethi on 05/09/18.
 */
public class RecipeSteps implements Serializable {

    @SerializedName("id")
    public int stepId;

    @SerializedName("shortDescription")
    public String shortDescription;

    @SerializedName("description")
    public String description;

    @SerializedName("videoURL")
    public String videoURL;

    @SerializedName("thumbnailURL")
    public String thumbnailURL;

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
