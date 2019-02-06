package com.example.nir.stellarapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import model.Post;
import model.Story;
import model.User;

public class FragmentUser extends Fragment {
    private TextView textView;

    private ArrayList<Story> stories = new ArrayList<>();
    View view;
    DatabaseHelper myDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        TextView username = view.findViewById(R.id.userName);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        myDb = new DatabaseHelper(this.getContext());

        stories = myDb.getAllStoriesByUser(myDb.getUserIdFromSettings());
        User user = myDb.getUserById(myDb.getUserIdFromSettings());

        TextView userName = view.findViewById(R.id.userName);
        TextView userStoriesLabel = view.findViewById(R.id.userStoriesTextView);
        userName.setText(user.getfName() + " " + user.getlName());
        userStoriesLabel.setText(user.getfName() + "'s Stories");


        StoryReyclerViewAdapter storyReyclerViewAdapter = new StoryReyclerViewAdapter(stories,this.getActivity());
        recyclerView.setAdapter(storyReyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }

    private void initRecyclerView() {
    }
}


