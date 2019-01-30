package com.example.nir.stellarapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import model.Post;
import model.Story;

public class AddStoryActivity extends AppCompatActivity {

    private int currentPost = 0;
    private Story story;
    public static final int PICK_IMAGE = 1;
    ImageView imgBackground;
    TextView postDesc;
    Context ctx;
    View btnNext;
    View btnPrev;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        myDb = new DatabaseHelper(this);
        ArrayList<Post> posts = new ArrayList<>();
        story = new Story(0,0,posts);
        story.getPosts().add(new Post(0,0,null,"Click here to change this post text"));

        final Button post1Btn = findViewById(R.id.button);
        final Button post2Btn = findViewById(R.id.button2);
        final Button post3Btn = findViewById(R.id.button3);
        Button btnAddStory = findViewById(R.id.postBtn);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        imgBackground = findViewById(R.id.imgBackground);
        postDesc = findViewById(R.id.postDesc);
        ctx = this;

        btnAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDb.addStory(story.getPosts(),1)) {
                    Toast.makeText(ctx, "Story Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ctx, "Failed to add story", Toast.LENGTH_SHORT).show();
                }
            }
        });

        post1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPost = 0;
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , PICK_IMAGE );
                post2Btn.setAlpha(1.0f);
                post2Btn.setEnabled(true);
            }
        });

        post2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPost = 1;
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , PICK_IMAGE );
                if(currentPost >= story.getPosts().size()){
                    story.getPosts().add(new Post(0,0,null,"Click here to change this post text"));
                    changeCurrentPost(1);
                }else{
                    // index exists
                }
                post3Btn.setAlpha(1.0f);
                post3Btn.setEnabled(true);
            }
        });

        post3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPost = 2;
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , PICK_IMAGE );
                if(currentPost >= story.getPosts().size()){
                    story.getPosts().add(new Post(0,0,null,"Click here to change this post text"));
                    changeCurrentPost(2);
                }else{
                    // index exists
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPost+1==story.getNumOfPosts()) {
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
                if (currentPost-1==-1) {
                    currentPost = story.getNumOfPosts()-1;
                }
                else {
                    currentPost--;
                }
                changeCurrentPost(currentPost);
            }
        });

        postDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText txtUrl = new EditText(ctx);
                AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .setTitle("Post " + (currentPost+1) + " text")
                        .setMessage("Add text to your post")
                        .setView(txtUrl)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String selectedText = txtUrl.getText().toString();
                                story.getPosts().get(currentPost).setDesc(selectedText);
                                postDesc.setText(selectedText) ;
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    private void changeCurrentPost(int currentPost) {
        imgBackground.setImageBitmap(story.getPosts().get(currentPost).getImage());
        postDesc.setText(story.getPosts().get(currentPost).getDesc());
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_IMAGE :
                if (null != data) {
                    Uri imageUri = data.getData();
                    try { //Getting the Bitmap from Gallery
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        imgBackground.setImageBitmap(bitmap);
                        story.getPosts().get(currentPost).setImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

}
