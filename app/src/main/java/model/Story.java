package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Story {
    int storyId;
    int userId;
    String userFullName;
    ArrayList<Post> posts;

    public Story(int storyId, int userId, String userFullName, ArrayList<Post> posts) {
        this.storyId = storyId;
        this.userId = userId;
        this.posts = posts;
        this.userFullName = userFullName;
    }

    public int getStoryId() {
        return storyId;
    }

    public Post getFirstPost() {
        return posts.get(0);
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getUserId() {
        return userId;
    }

    public int getNumOfPosts() {
        return this.posts.size();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserFullName() {

        return userFullName;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> getPosts() {

        return posts;
    }
}
