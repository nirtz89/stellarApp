package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Story {
    int storyId;
    int userId;
    ArrayList<Post> posts;

    public Story(int storyId, int userId, ArrayList<Post> posts) {
        this.storyId = storyId;
        this.userId = userId;
        this.posts = posts;
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
}
