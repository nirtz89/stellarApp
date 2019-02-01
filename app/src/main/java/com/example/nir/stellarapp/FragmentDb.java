package com.example.nir.stellarapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentDb extends Fragment {
    private static final String TAG = "Stories";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.db_fragment, container, false);

        Button showPosts = view.findViewById(R.id.showPosts);
        Button showStories = view.findViewById(R.id.showStories);
        Button showUsers = view.findViewById(R.id.showUsers);
        Button addStoryAndPost = view.findViewById(R.id.addStoryAndPost);
        Button removeTables = view.findViewById(R.id.deleteTables);
        final Context ctx = this.getActivity();

        showPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Showing all Posts");
                DatabaseHelper myDb = new DatabaseHelper(ctx);
                String allStories = myDb.getAllPostDataString();
                alertDialog.setMessage(allStories);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        showStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Showing all Stories");
                DatabaseHelper myDb = new DatabaseHelper(ctx);
                String allStories = myDb.getAllStoriesDataString();
                alertDialog.setMessage(allStories);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        showUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Showing all Users");
                DatabaseHelper myDb = new DatabaseHelper(ctx);
                String allUsers = myDb.getAllUsers();
                alertDialog.setMessage(allUsers);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        addStoryAndPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDb = new DatabaseHelper(ctx);
                if (myDb.addStoryAndPostStub(ctx.getResources())) {
                    Toast.makeText(ctx, "Success, story and post added.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ctx, "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        });

        removeTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDb = new DatabaseHelper(ctx);
                if (myDb.removeAllTables()) {
                    Toast.makeText(ctx, "Deleted all tables.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ctx, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}


