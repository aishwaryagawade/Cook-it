package com.example.cookit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "HomeScreenActivity" ;
    SearchView searchView;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    List<RecipeList> recipes = new ArrayList<>();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        searchView = findViewById(R.id.searchbar);
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recipeAdapter = new RecipeAdapter(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipeAdapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchRecipe(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recipes.clear();
                recipeAdapter.notifyDataSetChanged();
                return false;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), RecipePageActivity.class);
                intent.putExtra("id",recipes.get(position).recipe_id);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }


    public void searchRecipe(String search){

        Call<Recipes> recipesCall = RetrofitClient.getInstance().getApi().searchRecipes(search);
        recipesCall.enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {
                if(response.isSuccessful()){
                    if(response.body()!= null){
                        Log.i(TAG, response.body().toString());
                        recipes = response.body().recipes;
                        recipeAdapter.setOffline(false);
                        recipeAdapter.setData(recipes);
                    }
                }
                else{
                    Log.i(TAG, "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.favourites){
            Intent intent = new Intent(getApplicationContext(), SavedActivity.class);
            startActivity(intent);
        }
        return true;
    }
}