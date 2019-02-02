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

public class FragmentUser extends Fragment {
    private TextView textView;

    private ArrayList<Story> stories = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        TextView username = view.findViewById(R.id.userName);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);

        Bitmap myImage = BitmapFactory.decodeResource(this.getActivity().getResources(), R.drawable.cat2);
        Post story_1_post = new Post(1,1, myImage, "Look at my cats!");
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(story_1_post);
        stories.add(new Story(1,1, "", posts));

        Bitmap myImage1 = BitmapFactory.decodeResource(this.getActivity().getResources(), R.drawable.dog2);
        Post story_1_post1 = new Post(2,2, myImage1, "My dogs collection!");
        ArrayList<Post> posts1 = new ArrayList<>();
        posts1.add(story_1_post1);
        stories.add(new Story(2,2, "" ,posts1));

        Bitmap myImage2 = BitmapFactory.decodeResource(this.getActivity().getResources(), R.drawable.pancakes2);
        Post story_1_post2 = new Post(2,2, myImage2, "I ❤️ Pancakes!");
        ArrayList<Post> posts2 = new ArrayList<>();
        posts2.add(story_1_post2);
        stories.add(new Story(2,2, "", posts2));

        StoryReyclerViewAdapter storyReyclerViewAdapter = new StoryReyclerViewAdapter(stories,this.getActivity());
        recyclerView.setAdapter(storyReyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
    }
}


