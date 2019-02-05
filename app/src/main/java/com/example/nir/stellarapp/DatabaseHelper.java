package com.example.nir.stellarapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import model.Post;
import model.Story;
import model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "stellar.db";
    public static final String STORY_TABLE_NAME = "story_table";
    public static final String POST_TABLE_NAME = "post_table";
    public static final String SETTINGS_TABLE_NAME = "settings_table";
    public static final String USERS_TABLE_NAME = "users_table";
    public static final String LIKES_TABLE_NAME = "likes_table";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + STORY_TABLE_NAME + " (storyId INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, userName TEXT, likes INTEGER)");
        db.execSQL("create table " + POST_TABLE_NAME + " (postId INTEGER PRIMARY KEY AUTOINCREMENT, storyId INTEGER, img BLOB, description TEXT)");
        db.execSQL("create table " + SETTINGS_TABLE_NAME + " (userId INTEGER PRIMARY KEY)");
        db.execSQL("create table " + USERS_TABLE_NAME + " (userId INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, firstName TEXT, lastName TEXT, dob DATE, bio TEXT)");
        db.execSQL("create table " + LIKES_TABLE_NAME + " (userId INTEGER PRIMARY KEY AUTOINCREMENT, storyId INTEGER)");
        // STUBS
        // addStubs();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME);
        onCreate(db);
    }

    public void addStubs() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // First we add users

        cv.put("storyId",1);
        cv.put("userId",1);
        long result = db.insert(STORY_TABLE_NAME, null, cv);
    }

    public boolean insertData(int storyId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("storyId",storyId);
        cv.put("userId",userId);
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

    public ArrayList getPostsByQuery(String term) {
        ArrayList<Integer> stories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select distinct storyId from " + POST_TABLE_NAME + " WHERE description LIKE '%" + term + "%'", null);
        int ret = 0;
        if (res.getCount() > 0) {
            if (res.getCount() > 0) {
                while (res.moveToNext()) {
                    stories.add(res.getInt(0));
                }
            }
        }
        return stories;
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

    public Boolean addStory(ArrayList<Post> posts, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userId",userId);
        long resultStory = db.insert(STORY_TABLE_NAME, null, cv);

        if (resultStory>-1) {
            int currentStoryId = this.getLastStoryId();
            for (Post p : posts) {
                if (!this.addPost(currentStoryId,0,p.getImage(),p.getDesc())) {
                    return false;
                }
            }
        }

        return true;
    }

    public Boolean addPost(int storyId, int postId, Bitmap img, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] photo = baos.toByteArray();

        cv.put("storyId",storyId);
        cv.put("img",photo);
        cv.put("description",desc);
        long resultPost = db.insert(POST_TABLE_NAME, null, cv);

        return resultPost>-1;
    }

    public Boolean addPostStub(Resources rcs, int storyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int lastPostRecord = this.getLastPostId();
        ContentValues cvPost = new ContentValues();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Random rand = new Random();
        int n = rand.nextInt(3) + 1;

        Bitmap myImage;

        switch (n) {
            case 1:
                myImage = BitmapFactory.decodeResource(rcs, R.drawable.dog2);
                break;
            case 2:
                myImage = BitmapFactory.decodeResource(rcs, R.drawable.cat2);
                break;
            default:
                myImage = BitmapFactory.decodeResource(rcs, R.drawable.pancakes2);
                break;
        }

        myImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] photo = baos.toByteArray();

        cvPost.put("postId",lastPostRecord+1);
        cvPost.put("storyId",storyId);
        cvPost.put("img",photo);
        cvPost.put("description","A string");

        long resultPost = db.insert(POST_TABLE_NAME, null, cvPost);
        return resultPost>0;

    }

    public Boolean addStoryAndPostStub(Resources rcs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int lastStoryRecord = this.getLastStoryId();
        ContentValues cvStory = new ContentValues();

        cvStory.put("storyId",lastStoryRecord+1);
        cvStory.put("userId",1);

        Random rand = new Random();
        int num_posts = rand.nextInt(3) + 1;

        long resultStory = db.insert(STORY_TABLE_NAME, null, cvStory);
        if (resultStory>0) {
            for (int i = 0; i < num_posts; i++) {
                this.addPostStub(rcs, lastStoryRecord + 1);
            }
            return true;
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
                toRet += "text: " + res.getString(3) + "\n\n";
            }
        }
        return toRet;
    }

    public int getUserIdFromSettings() {
        String toRet = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select userId from " + SETTINGS_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                return res.getInt(0);
            }
        }
        return -1;
    }

    public Boolean doesUserLikedStory(int storyId) {
        String toRet = "";
        SQLiteDatabase db = this.getWritableDatabase();
        int userId = getUserIdFromSettings();
        Cursor res = db.rawQuery("select userId from " + LIKES_TABLE_NAME + " where userId = " + userId + " and storyId = " + storyId, null);
        return (res.getCount() > 0);
    }

    public int getNumberOfLikesForStory(int storyId) {
        String toRet = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(1) from " + LIKES_TABLE_NAME + " where storyId = " + storyId, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                return res.getInt(0);
            }
        }
        return 0;
    }

    public User getUserById(int userId) {
        User user = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + SETTINGS_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                user = new User(res.getInt(0),res.getString(1),res.getString(3),res.getString(4),res.getString(6),res.getString(5));
            }
            return user;
        }
        return null;
    }

    public Story getStoryAndPostsByStoryId(int storyId) {
        Story story = null;
        ArrayList<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + STORY_TABLE_NAME + ".*, firstName, lastName from " + STORY_TABLE_NAME + " JOIN " + USERS_TABLE_NAME + " ON " + STORY_TABLE_NAME + ".userId = " + USERS_TABLE_NAME + ".userId where storyId = " + storyId, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                String fullUserName = res.getString(4) + " " + res.getString(5);
                story = new Story(res.getInt(0),res.getInt(1),fullUserName,posts);
            }
        }

        Cursor res2 = db.rawQuery("select * from " + POST_TABLE_NAME + " where storyId = " + storyId, null);
        if (res2.getCount() > 0) {
            while (res2.moveToNext()) {
                Bitmap myImage = DbBitmapUtility.getImage(res2.getBlob(2));
                Post post = new Post(res2.getInt(0),res2.getInt(1),myImage,res2.getString(3));
                posts.add(post);
            }
        }
        return story;
    }

    public ArrayList<Story> getAllFirstStories() {
        ArrayList<Story> stories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select p.*, u.firstName, u.lastName from " + POST_TABLE_NAME + " as p JOIN " + STORY_TABLE_NAME + " as s ON p.storyId = s.storyId JOIN " + USERS_TABLE_NAME + " as u ON s.userId = u.userId", null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                Bitmap myImage = DbBitmapUtility.getImage(res.getBlob(2));
                Post post = new Post(res.getInt(0),res.getInt(1), myImage, res.getString(3));
                Boolean storyFound = false;
                for (Story s : stories) {
                    if (s.getStoryId() == res.getInt(1)) {
                        storyFound = true;
                        s.getPosts().add(post);
                        break;
                    }
                }
                if (!storyFound) {
                    ArrayList<Post> posts = new ArrayList<>();
                    posts.add(post);
                    stories.add(new Story(res.getInt(1), 1, res.getString(4) + " " + res.getString(5), posts));
                }
            }
        }
        return stories;
    }

    public ArrayList<Story> getAllFirstStoriesByIds(ArrayList<Integer> storiesToGet) {
        ArrayList<Story> stories = new ArrayList<>();
        String findString = "";

        for (Integer storyId : storiesToGet) {
            findString += storyId +",";
        }

        findString = findString.substring(0, findString.length() - 1);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select p.*, u.firstName, u.lastName from " + POST_TABLE_NAME + " as p JOIN " + STORY_TABLE_NAME + " as s ON p.storyId = s.storyId JOIN " + USERS_TABLE_NAME + " as u ON s.userId = u.userId WHERE p.storyId IN (" + findString + ")", null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                Bitmap myImage = DbBitmapUtility.getImage(res.getBlob(2));
                Post post = new Post(res.getInt(0),res.getInt(1), myImage, res.getString(3));
                Boolean storyFound = false;
                for (Story s : stories) {
                    if (s.getStoryId() == res.getInt(1)) {
                        storyFound = true;
                        s.getPosts().add(post);
                        break;
                    }
                }
                if (!storyFound) {
                    ArrayList<Post> posts = new ArrayList<>();
                    posts.add(post);
                    stories.add(new Story(res.getInt(1), 1, res.getString(4) + " " + res.getString(5), posts));
                }
            }
        }
        return stories;
    }

    public Boolean isUserLoggedIn() {
        Boolean toRet = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select userId from " + SETTINGS_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                if (res.getInt(0)!=-1) {
                    toRet = true;
                }
            }
        }
        return toRet;
    }

    public Boolean registerUser(String pwd, String email, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // Get latest userId
        Cursor res = db.rawQuery("select userId from " + USERS_TABLE_NAME + " limit 0,1", null);
        int userLastId;
        if (res.getCount() > 0) {
            cv.putNull("userId");
        }
        else {
            cv.put("userId",1);
        }
        cv.put("password",pwd);
        cv.put("email",email);
        cv.put("firstName",firstName);
        cv.put("lastName",lastName);
        cv.put("dob","");
        cv.put("bio","");
        long resultPost = db.insert(USERS_TABLE_NAME, null,cv);
        return resultPost>-1;
    }

    public Boolean loginUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId",userId);

        Cursor res = db.rawQuery("select userId from " + SETTINGS_TABLE_NAME, null);
        if (res.getCount() > 0) {
            long resultPost = db.update(SETTINGS_TABLE_NAME, cv, null,null);
            return resultPost>0;
        }
            long resultPost = db.insert(SETTINGS_TABLE_NAME, null,cv);
        return resultPost>0;
    }

    public Boolean removeAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LIKES_TABLE_NAME);
        db.execSQL("create table " + STORY_TABLE_NAME + " (storyId INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, userName TEXT, likes INTEGER)");
        db.execSQL("create table " + POST_TABLE_NAME + " (postId INTEGER PRIMARY KEY AUTOINCREMENT, storyId INTEGER, img BLOB, description TEXT)");
        db.execSQL("create table " + SETTINGS_TABLE_NAME + " (userId INTEGER PRIMARY KEY)");
        db.execSQL("create table " + USERS_TABLE_NAME + " (userId INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, firstName TEXT, lastName TEXT, dob DATE, bio TEXT)");
        db.execSQL("create table " + LIKES_TABLE_NAME + " (userId INTEGER PRIMARY KEY AUTOINCREMENT, storyId INTEGER)");
        return true;
    }

    public String getAllStoriesDataString() {
        String toRet = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + STORY_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                toRet += "storyId: " + res.getString(0) + "\n";
                toRet += "userId: " + res.getString(1) + "\n\n\n";
            }
        }
        return toRet;
    }

    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select userId from " + USERS_TABLE_NAME + " where email = '" + email + "'", null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                return res.getInt(0);
            }
        }
        return 0;
    }

    public Boolean checkUserLogin(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select userId from " + USERS_TABLE_NAME + " where email = '" + email + "' and password = '" + password + "'", null);
        if (res.getCount() > 0) {
            return true;
        }
        return false;
    }

    public Boolean logout() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId",-1);

        long resultPost = db.update(SETTINGS_TABLE_NAME, cv, null,null);
        return resultPost>-1;
    }

    public String getAllUsers() {
        String toRet = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USERS_TABLE_NAME, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                toRet += "UserId: " + res.getString(0) + "\n";
                toRet += "UserEmail: " + res.getString(1) + "\n";
                toRet += "Password: " + res.getString(2) + "\n";
                toRet += "First Name: " + res.getString(3) + "\n";
                toRet += "Last Name: " + res.getString(4) + "\n\n\n";
            }
        }
        return toRet;
    }

    public ArrayList<Story> getAllStoriesByUser(int userId) {
        ArrayList<Story> stories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select p.*, u.firstName, u.lastName from " + POST_TABLE_NAME + " as p JOIN " + STORY_TABLE_NAME + " as s ON p.storyId = s.storyId JOIN " + USERS_TABLE_NAME + " as u ON s.userId = u.userId WHERE u.userId = " + userId, null);
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                Bitmap myImage = DbBitmapUtility.getImage(res.getBlob(2));
                Post post = new Post(res.getInt(0),res.getInt(1), myImage, res.getString(3));
                Boolean storyFound = false;
                for (Story s : stories) {
                    if (s.getStoryId() == res.getInt(1)) {
                        storyFound = true;
                        s.getPosts().add(post);
                        break;
                    }
                }
                if (!storyFound) {
                    ArrayList<Post> posts = new ArrayList<>();
                    posts.add(post);
                    stories.add(new Story(res.getInt(1), 1, res.getString(4) + " " + res.getString(5), posts));
                }
            }
        }
        return stories;

    }

    public Boolean addLike(int storyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int userId = getUserIdFromSettings();
        cv.put("storyId",storyId);
        cv.put("userId",userId);
        long result = db.insert(LIKES_TABLE_NAME, null, cv);
        return result>0;
    }
}
