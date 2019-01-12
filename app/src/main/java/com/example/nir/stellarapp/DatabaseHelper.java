package com.example.nir.stellarapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

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

    public boolean insertPostData(int postId, int storyId, String desc, Resources rcs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap myImage = BitmapFactory.decodeResource(rcs, R.drawable.cat2);
        myImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] photo = baos.toByteArray();

        cv.put("postId",postId);
        cv.put("storyId",storyId);
        cv.put("img",photo);
        cv.put("description",desc);
        long result = db.insert(POST_TABLE_NAME, null, cv);
        return result>0;
    }

    public Cursor getAllStoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + STORY_TABLE_NAME, null);
        return res;
    }

    public String getAllPostDataString() {
        String toRet = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + POST_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                toRet += "postId: " + res.getString(0) + "\n";
                toRet += "storyId: " + res.getString(1) + "\n";
//                toRet += "img: " + res.getString(2);
                toRet += "text: " + res.getString(3);
            }
        }
        return toRet;
    }
}
