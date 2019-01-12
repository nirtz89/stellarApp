package com.example.nir.stellarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "stellar.db";
    public static final String STORY_TABLE_NAME = "story_table";
    public static final String POST_TABLE_NAME = "post_table";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + STORY_TABLE_NAME + " (storyId INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER)");
        db.execSQL("create table " + POST_TABLE_NAME + " (postId INTEGER PRIMARY KEY AUTOINCREMENT, storyId INTEGER, img BLOB, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int storyId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("storyId",storyId);
        cv.put("userId",storyId);
        long result = db.insert(STORY_TABLE_NAME, null, cv);
        return result>0;
    }

    public Cursor getAllStoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + STORY_TABLE_NAME, null);
        return res;
    }

    public Cursor getAllPostsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + POST_TABLE_NAME, null);
        return res;
    }
}
