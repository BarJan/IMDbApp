package com.example.imdbapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "IMDbApp.db";
    private static final String TABLE_NAME = "moviesList";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_RATING = "rating";
    private static final String KEY_RELEASE_YEAR = "releaseYear";
    private static final String KEY_GENRE = "genre";

    private final static String DB_PATH = "/data/data/IMDbApp/databases/";
    String dbName;
    File dbFile;


    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.dbName = DATABASE_NAME;
        dbFile= new File(DB_PATH + dbName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        String create = "CREATE TABLE " + TABLE_NAME + "("
                + "id INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_IMAGE + " TEXT," +
                KEY_RATING + " TEXT," +
                KEY_RELEASE_YEAR + " TEXT," +
                KEY_GENRE + " TEXT" + ");";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVer, int newVer){
        //Drop old if exists one
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(MovObj mov) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(!Exists(mov.getTitle(),db)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TITLE, mov.getTitle());
            contentValues.put(KEY_IMAGE, mov.getImage());
            contentValues.put(KEY_RATING, mov.getRating());
            contentValues.put(KEY_RELEASE_YEAR, mov.getReleaseYear());
            contentValues.put(KEY_GENRE, mov.stringtify_genre());
            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }

        return false;
    }

    public  MovObj getMovie(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM "+ TABLE_NAME + " WHERE " + KEY_TITLE+" =?;";
        Cursor cursor = db.rawQuery(selectQuery,new String[]{title});
        MovObj mov = new MovObj("1", "2", 3.0, 4, new ArrayList<String>());
        if (cursor.moveToFirst()) {
            String tit = cursor.getString(1);
            String im = cursor.getString(2);
            double rate = cursor.getDouble(3);
            int year = cursor.getInt(4);
            List<String> genr = new ArrayList<String>();
            String[] genr_str = cursor.getString(5).split(",");
            for (String word : genr_str) {
                genr.add(word);
            }
            mov = new MovObj(tit, im, rate, year, genr);
            // Adding  to list
        }
        return mov;
    }

    // Getting All
    public List<MovObj> getAllMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<MovObj> moviesList = new ArrayList<MovObj>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_RELEASE_YEAR +" DESC;";
        Cursor cursor = db.rawQuery(selectQuery,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String tit = cursor.getString(1);
                String im = cursor.getString(2);
                double rate = cursor.getDouble(3);
                int year = cursor.getInt(4);
                List<String> genr = new ArrayList<String>();
                String[] genr_str = cursor.getString(5).split(",");
                for (String word : genr_str) {
                    genr.add(word);
                }
                MovObj mov = new MovObj(tit,im,rate,year,genr);
                // Adding  to list
                moviesList.add(mov);
            } while (cursor.moveToNext());
        }
        // return  list
        return moviesList;
    }

    public boolean Exists(String tit,SQLiteDatabase db){
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + KEY_TITLE+" =?;";
        Cursor cursor = db.rawQuery(query,new String[]{tit});
        return (cursor.getCount()>0);
    }

    // Getting  Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }
}