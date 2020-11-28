package com.example.cookit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String FAVOURITES_TABLE_NAME = "favourite_recipes";
    public static final String COLUMN_ID = "id";
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_TITLE = "title";
    public static final String INGREDIENTS = "ingredients";
    public static final String HTML = "html";
    private static final String TAG = "DBHelper";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + FAVOURITES_TABLE_NAME + " ("
                + RECIPE_ID + " VARCHAR PRIMARY KEY, "
                + RECIPE_TITLE + " VARCHAR,"
                + INGREDIENTS + " VARCHAR, " + HTML
                + " VARCHAR)");
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVOURITES_TABLE_NAME);
        onCreate(db);
    }

    public void insertRecipe(RecDetails.Recipe recipe, String html, String ingredients) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(RECIPE_ID, recipe.getRecipe_id());
        contentValues.put(RECIPE_TITLE, recipe.getTitle());
        contentValues.put(HTML, html);
        contentValues.put(INGREDIENTS, ingredients);
        db.insert(FAVOURITES_TABLE_NAME, null, contentValues);

    }

    public void deleteRecipe(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAVOURITES_TABLE_NAME,
                RECIPE_ID + " = ? ",
                new String[]{id});
    }

    public List<RecipeList> getRecipeList() {
        List<RecipeList> recipeLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FAVOURITES_TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            recipeLists.add(new RecipeList(null,
                    res.getString(res.getColumnIndex(RECIPE_TITLE)),
                    null,
                    res.getString(res.getColumnIndex(RECIPE_ID)),
                    null,
                    null,
                    null));
            //Log.i(TAG, String.valueOf(res.getInt(res.getColumnIndex(COLUMN_ID))));
            res.moveToNext();
        }
        res.close();
        return recipeLists;
    }

    public boolean isPresent(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + FAVOURITES_TABLE_NAME + " WHERE " + RECIPE_ID + "=" + id + "", null);

        if (res.getCount() <= 0) {
            res.close();
            return false;
        } else {
            return true;
        }
    }

    public void emptyTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + FAVOURITES_TABLE_NAME);
    }

    public int getId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery("SELECT * FROM " + FAVOURITES_TABLE_NAME + " WHERE " + RECIPE_ID + " = " + id, null);

            if (res.getCount() > 0) {
                res.moveToFirst();
                int columnId = res.getInt(res.getColumnIndex(COLUMN_ID));
                res.close();
                return columnId;

            } else {
                return -1;
            }

        }

    }


