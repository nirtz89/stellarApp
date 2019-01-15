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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        TextView postDesc = findViewById(R.id.postDesc);
        View btnNext = findViewById(R.id.btnNext);
        View btnPrev = findViewById(R.id.btnPrev);
        ImageView img = findViewById(R.id.imgBackground);

        Intent intent = getIntent();
        String storyId = intent.getStringExtra("storyId");
        myDb = new DatabaseHelper(this);
        Story story = myDb.getStoryAndPostsByStoryId(Integer.parseInt(storyId));
        Post post = story.getFirstPost();

        img.setImageBitmap(post.getImage());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryActivity.this, "NEXT!", Toast.LENGTH_SHORT).show();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryActivity.this, "PREV!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
