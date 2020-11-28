package com.example.cookit;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private static final String TAG = "RecipeAdapter";
    ArrayList<RecipeList> recipes;
    LayoutInflater inflater;
    Context ctx;
    Boolean offline;

    public RecipeAdapter(Context ctx){
        this.ctx = ctx;
        this.inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_recipe,parent,false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {

        holder.recipeName.setText(recipes.get(position).getTitle());
//        Log.i(TAG, recipes.get(position).getImage_url());
        String url = recipes.get(position).image_url;
        if (offline){
            ContextWrapper cw = new ContextWrapper(ctx);
            File directory = cw.getDir("favourite_recipes", Context.MODE_PRIVATE);
            File myImageFile = new File(directory, recipes.get(position).getTitle());
            Picasso.get().load(myImageFile).placeholder(R.drawable.placholder).resize(1000,1000).centerCrop().into(holder.recipeImg);
        } else {
            Picasso.get().load(url).placeholder(R.drawable.placholder).resize(1000,1000).centerCrop().into(holder.recipeImg);
        }


    }

    @Override
    public int getItemCount() {
        if(recipes==null)
        {
            return 0;
        }
        return recipes.size();
    }

    public class RecipeHolder extends  RecyclerView.ViewHolder{

        ImageView recipeImg;
        TextView recipeName;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            recipeImg = itemView.findViewById(R.id.recipeImg);
            recipeName= itemView.findViewById(R.id.recipeName);

        }
    }

    public void setData(List<RecipeList> recipes){
        this.recipes = (ArrayList<RecipeList>) recipes;
        notifyDataSetChanged();
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }
}
