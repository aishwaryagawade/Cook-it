package com.example.cookit;

import java.util.List;

public class Recipes {

    public int count;
    public List<RecipeList> recipes;

    public Recipes(int count, List<RecipeList> recipeList) {
        this.count = count;
        this.recipes = recipeList;
    }

    public int getCount() {
        return count;
    }

    public List<RecipeList> getRecipeList() {
        return recipes;
    }

    public void setRecipeList(List<RecipeList> recipeList) {
        this.recipes = recipeList;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "count=" + count +
                ", recipeList=" + recipes+
                '}';
    }
}



