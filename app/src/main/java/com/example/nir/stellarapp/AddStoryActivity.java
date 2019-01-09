package com.example.nir.stellarapp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import model.Post;
import model.Story;

public class AddStoryActivity extends AppCompatActivity {

    int currentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        ArrayList<Post> posts = new ArrayList<>();

        Button addPost1Btn = findViewById(R.id.button);
        Button addPost2Btn = findViewById(R.id.button2);
        Button addPost3Btn = findViewById(R.id.button3);
        final ConstraintLayout post1 = findViewById(R.id.post1);
        final ConstraintLayout post2 = findViewById(R.id.post2);
        final ConstraintLayout post3 = findViewById(R.id.post3);

        addPost1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post2.setVisibility(View.INVISIBLE);
                post3.setVisibility(View.INVISIBLE);
                post1.setVisibility(View.VISIBLE);
                currentPost = 0;
            }
        });

        addPost2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post1.setVisibility(View.INVISIBLE);
                post3.setVisibility(View.INVISIBLE);
                post2.setVisibility(View.VISIBLE);
                currentPost = 1;
            }
        });

        addPost3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post1.setVisibility(View.INVISIBLE);
                post2.setVisibility(View.INVISIBLE);
                post3.setVisibility(View.VISIBLE);
                currentPost = 2;
            }
        });
    }

}
