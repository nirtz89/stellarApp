package model;

public class Story {
    int storyId;
    int userId;
    Post[] posts;

    public Story(int storyId, int userId, Post[] posts) {
        this.storyId = storyId;
        this.userId = userId;
        this.posts = posts;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getUserId() {
        return userId;
    }
}
