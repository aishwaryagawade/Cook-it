package com.example.cookit;


import java.util.List;
;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("search")
    Call<Recipes> searchRecipes(@Query("q") String search);

    @GET("get")
    Call<RecDetails> getRecDetails(@Query("rId") String id);

}
