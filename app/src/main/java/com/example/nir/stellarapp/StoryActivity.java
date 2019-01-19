package com.example.nir.stellarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import model.Post;
import model.Story;

public class StoryActivity extends AppCompatActivity {


    DatabaseHelper myDb;

    TextView postDesc;
    View btnNext;
    View btnPrev;
    View post1Bar;
    View post2Bar;
    View post3Bar;
    ImageView img;
    int currentPost;
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        postDesc = findViewById(R.id.postDesc);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        post1Bar = findViewById(R.id.post1Bar);
        post2Bar = findViewById(R.id.post2Bar);
        post3Bar = findViewById(R.id.post3Bar);
        img = findViewById(R.id.imgBackground);
        currentPost = 0;

        Intent intent = getIntent();
        String storyId = intent.getStringExtra("storyId");
        myDb = new DatabaseHelper(this);
        story = myDb.getStoryAndPostsByStoryId(Integer.parseInt(storyId));
        Post post = story.getFirstPost();

        final int num_posts = story.getNumOfPosts()-1;
        switch (num_posts) {
            case 0:
                post2Bar.setVisibility(View.GONE);
                post3Bar.setVisibility(View.GONE);
                break;
            case 1:
                post3Bar.setVisibility(View.GONE);
                break;
        }

        img.setImageBitmap(post.getImage());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPost==num_posts) {
                    currentPost = 0;
                }
                else {
                    currentPost++;
                }
                changeCurrentPost(currentPost);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPost==0) {
                    currentPost = num_posts;
                }
                else {
                    currentPost--;
                }
                changeCurrentPost(currentPost);
            }
        });

    }

    private void changeCurrentPost(int currentPost) {
        img.setImageBitmap(story.getPosts().get(currentPost).getImage());
        postDesc.setText(story.getPosts().get(currentPost).getDesc());
        switch (currentPost) {
            case 0:
                post1Bar.setAlpha(1);
                post2Bar.setAlpha(0.5f);
                post3Bar.setAlpha(0.5f);
                break;
            case 1:
                post1Bar.setAlpha(0.5f);
                post2Bar.setAlpha(1);
                post3Bar.setAlpha(0.5f);
                break;
            case 2:
                post1Bar.setAlpha(0.5f);
                post2Bar.setAlpha(0.5f);
                post3Bar.setAlpha(1);
                break;
        }
    };



}
