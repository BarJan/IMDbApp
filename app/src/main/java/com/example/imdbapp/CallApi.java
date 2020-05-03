package com.example.imdbapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CallApi {
    String BASE_URL = "https://api.androidhive.info/json/";

    @GET("movies.json")
    Call<List<MovObj>> getMovieList();
}
