package model;

import android.graphics.Bitmap;

public class Post {
    int posdId;
    int storyId;
    Bitmap image;
    String desc;

    public Post(int posdId, int storyId, Bitmap image, String desc) {
        this.posdId = posdId;
        this.storyId = storyId;
        this.image = image;
        this.desc = desc;
    }

    public int getPosdId() {
        return posdId;
    }

    public void setPosdId(int posdId) {
        this.posdId = posdId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStoryId() {

        return storyId;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }
}
