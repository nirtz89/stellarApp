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
import java.util.ArrayList;

import model.Post;
import model.Story;

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

    public int getLastStoryId() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select storyId from " + STORY_TABLE_NAME, null);
        int ret = 0;
        if (res.getCount() > 0) {
            if (res.getCount() > 0) {
                while (res.moveToNext()) {
                    ret = res.getInt(0);
                }
            }
        }
        return ret;
    }

    public int getLastPostId() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select postId from " + POST_TABLE_NAME, null);
        int ret = 0;
        if (res.getCount() > 0) {
            if (res.getCount() > 0) {
                while (res.moveToNext()) {
                    ret = res.getInt(0);
                }
            }
        }
        return ret;
    }

    public Boolean addStoryAndPostStub(Resources rcs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int lastStoryRecord = this.getLastStoryId();
        int lastPostRecord = this.getLastPostId();
        ContentValues cvPost = new ContentValues();
        ContentValues cvStory = new ContentValues();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap myImage = BitmapFactory.decodeResource(rcs, R.drawable.cat2);
        myImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] photo = baos.toByteArray();

        cvStory.put("storyId",lastStoryRecord+1);
        cvStory.put("userId",1);

        cvPost.put("postId",lastPostRecord+1);
        cvPost.put("storyId",lastStoryRecord+1);
        cvPost.put("img",photo);
        cvPost.put("description","A string");
        long resultStory = db.insert(STORY_TABLE_NAME, null, cvStory);
        if (resultStory>0) {
            long resultPost = db.insert(POST_TABLE_NAME, null, cvPost);
            return resultPost>0;
        }
        return false;
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

    public ArrayList<Story> getAllFirstStories() {
        ArrayList<Story> stories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + POST_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                Bitmap myImage = DbBitmapUtility.getImage(res.getBlob(2));
                Post post = new Post(res.getInt(0),res.getInt(1), myImage, res.getString(3));
                ArrayList<Post> posts = new ArrayList<>();
                posts.add(post);
                stories.add(new Story(res.getInt(1),1, posts));
            }
        }
        return stories;
    }

    public Boolean removeAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        db.execSQL("create table " + STORY_TABLE_NAME + " (storyId INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER)");
        db.execSQL("create table " + POST_TABLE_NAME + " (postId INTEGER PRIMARY KEY AUTOINCREMENT, storyId INTEGER, img BLOB, description TEXT)");
        return true;
    }
}
