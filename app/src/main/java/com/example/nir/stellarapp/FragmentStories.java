package com.example.nir.stellarapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.Post;
import model.Story;

public class FragmentStories extends Fragment {
    private static final String TAG = "Stories";
    private TextView textView;
    DatabaseHelper myDb;

    private ArrayList<Story> stories = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stories_fragment, container, false);
        myDb = new DatabaseHelper(this.getActivity());

        stories = myDb.getAllFirstStories();

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.storyRecyclerView);
        StoryReyclerViewAdapter storyReyclerViewAdapter = new StoryReyclerViewAdapter(stories,this.getActivity());
        recyclerView.setAdapter(storyReyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}


