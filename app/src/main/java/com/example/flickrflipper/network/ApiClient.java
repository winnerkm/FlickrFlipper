package com.example.flickrflipper.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by kuliza-234 on 16/06/17.
 */

public class ApiClient {

    public static final String BASE_URL = "https://api.flickr.com/services/feeds/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public interface FlickrInterface {

        @GET("photos_public.gne?format=json")
        Call<ResponseBody> getUserData();

    }
}
