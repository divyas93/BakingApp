package com.bakingapp.Network;

import com.bakingapp.AppConstants;
import com.bakingapp.POJO.RecipeResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DivyaSethi on 04/09/18.
 */
public interface RetrofitAPIInterface {

    @GET(AppConstants.bakingAppPath)
    Call<ArrayList<RecipeResults>> getBakingJson();
}
