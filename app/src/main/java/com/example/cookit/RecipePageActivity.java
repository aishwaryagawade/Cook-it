package com.example.cookit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.SQLException;

public class RecipePageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RecipePage" ;
    ImageView recipeImage;
    Intent intent;
    String id;
    RecDetails.Recipe recipeDet;
    ListView listView;
    ArrayAdapter arrayAdapter;
    TextView recTitle;
    Button button;
    ImageButton favBtn;
    DBHelper dbHelper;
    Boolean liked = false;
    Boolean isOffline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        listView = findViewById(R.id.listview);
        recipeImage = findViewById(R.id.rec_img);
        recTitle = findViewById(R.id.rec_title);
        intent = getIntent();
        id = intent.getStringExtra("id");
        isOffline = intent.getBooleanExtra("isOffline",false);
        button = findViewById(R.id.button);
        favBtn = findViewById(R.id.imageButton);

        dbHelper = new DBHelper(this);

        button.setOnClickListener(this);
        favBtn.setOnClickListener(this);

        if(isOffline){

        }


        Call<RecDetails> recDetailsCall = RetrofitClient.getInstance().getApi().getRecDetails(id);
        recDetailsCall.enqueue(new Callback<RecDetails>() {
            @Override
            public void onResponse(Call<RecDetails> call, Response<RecDetails> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    recipeDet = response.body().recipe;
                    liked = dbHelper.isPresent(id);

                    changeIcon();
                    recTitle.setText(recipeDet.getTitle());
                    Log.i(TAG, recipeDet.getImage_url());
                    //Glide.with(getApplicationContext()).load("https://forkify-api.herokuapp.com/images/best_pizza_dough_recipe1b20.jpg").into(recipeImage);
                    Picasso.get().load(recipeDet.getImage_url()).placeholder(R.drawable.placholder).resize(1000,1000).centerCrop().into(recipeImage);
                    Log.i(TAG, response.body().recipe.toString());
                    arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, recipeDet.ingredients);
                    listView.setAdapter(arrayAdapter);


                }
            }

            @Override
            public void onFailure(Call<RecDetails> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            WebView wv = new WebView(this);
            wv.loadUrl(recipeDet.source_url);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);

                    return true;
                }
            });

            alert.setView(wv);
            alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else if (v.getId() == R.id.imageButton){
            if(liked){
                liked = false;
                changeIcon();
                dbHelper.deleteRecipe(recipeDet.recipe_id);
                deleteImage(getApplicationContext(), recipeDet.getTitle());
            }else {
                liked = true;
                changeIcon();
                dbHelper.insertRecipe( recipeDet,"","");
                Picasso.get().load(recipeDet.getImage_url()).into(new SaveImageHelper(getApplicationContext(),recipeDet.getTitle(),"favourite_recipes"));
            }

        }
    }

    public void changeIcon(){
        if (liked){
            favBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
        } else {
            favBtn.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24_white));
        }
    }
    public static void deleteImage(Context context, String name ){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("favourite_recipes", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, name);
        if(myImageFile.delete()){
            Log.i(TAG, "deleted");
        }
        else {
            Log.i(TAG, "not deleteImage: ");
        }
    }
}