package com.example.cookit;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class RecDetails {

    public Recipe recipe;

    public RecDetails(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecDetails{" +
                "recipe=" + recipe +
                '}';
    }

    public class Recipe {
        public String publisher;
        public ArrayList<String> ingredients;
        public String source_url;
        public String recipe_id;
        public String image_url;
        public String social_rank;
        public String publisher_url;
        public String title;

        public Recipe(String publisher, ArrayList<String> ingredients, String source_url, String recipe_id, String image_url, String social_rank, String publisher_url, String title) {
            this.publisher = publisher;
            this.ingredients = ingredients;
            this.source_url = source_url;
            this.recipe_id = recipe_id;
            this.image_url = image_url;
            this.social_rank = social_rank;
            this.publisher_url = publisher_url;
            this.title = title;
        }

        public String getPublisher() {
            return publisher;
        }

        public ArrayList<String> getIngredients() {
            return ingredients;
        }

        public String getSource_url() {
            return source_url;
        }

        public String getRecipe_id() {
            return recipe_id;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getSocial_rank() {
            return social_rank;
        }

        public String getPublisher_url() {
            return publisher_url;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "Recipe{" +
                    "publisher='" + publisher + '\'' +
                    ", ingredients=" + ingredients +
                    ", source_url='" + source_url + '\'' +
                    ", recipe_id='" + recipe_id + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", social_rank='" + social_rank + '\'' +
                    ", publisher_url='" + publisher_url + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
