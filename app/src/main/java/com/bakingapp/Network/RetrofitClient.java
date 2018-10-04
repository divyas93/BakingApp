package com.bakingapp.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DivyaSethi on 04/09/18.
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String baseUrl) {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;

    }

    public static RetrofitAPIInterface getRetorfitAPIInterface(String url) {
        Retrofit client = getRetrofitClient(url);
        return client.create(RetrofitAPIInterface.class);
    }

}
