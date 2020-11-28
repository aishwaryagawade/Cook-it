package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {

    DBHelper dbHelper;
    List<RecipeList> recipes = new ArrayList<>();
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        recyclerView = findViewById(R.id.saved_recycle_view);


        dbHelper = new DBHelper(this);
        recipes = dbHelper.getRecipeList();

        recipeAdapter = new RecipeAdapter(this);
        recipeAdapter.setOffline(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setData(recipes);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), RecipePageActivity.class);
                intent.putExtra("id", recipes.get(position).getRecipe_id());
                intent.putExtra("isOffile", true);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }
}