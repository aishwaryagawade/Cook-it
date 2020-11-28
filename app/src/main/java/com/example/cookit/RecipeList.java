package com.example.cookit;

public class RecipeList {

    public String publisher;
    public String title;
    public String source_url;
    public String recipe_id;
    public String image_url;
    public String social_rank;
    public String publisher_url;

    public RecipeList(String publisher, String title, String source_url, String recipe_id, String image_url, String social_rank, String publisher_url) {
        this.publisher = publisher;
        this.title = title;
        this.source_url = source_url;
        this.recipe_id = recipe_id;
        this.image_url = image_url;
        this.social_rank = social_rank;
        this.publisher_url = publisher_url;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
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

    @Override
    public String toString() {
        return "Recipe{" +
                "publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", source_url='" + source_url + '\'' +
                ", recipe_id='" + recipe_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank='" + social_rank + '\'' +
                ", publisher_url='" + publisher_url + '\'' +
                '}';
    }
}
