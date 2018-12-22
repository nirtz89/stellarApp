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
