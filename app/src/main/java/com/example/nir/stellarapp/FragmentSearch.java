package com.example.nir.stellarapp;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Story;

public class FragmentSearch extends Fragment {
    private static final String TAG = "Stories";
    View view;
    DatabaseHelper myDb;
    ArrayList<Story> stories = new ArrayList<>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);

        final EditText showPosts = view.findViewById(R.id.searchTern);
        final Context ctx = this.getActivity();

        myDb = new DatabaseHelper(this.getContext());
        ImageView searchImageView = view.findViewById(R.id.searchImageView);
        recyclerView = view.findViewById(R.id.recyclerView);
        StoryReyclerViewAdapter storyReyclerViewAdapter = new StoryReyclerViewAdapter(stories,ctx);
        recyclerView.setAdapter(storyReyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = showPosts.getText().toString();
                if(s.length() != 0) {
                    ArrayList found = myDb.getPostsByQuery(s.toString());
                    if (found.size()>0) {
                        stories = myDb.getAllFirstStoriesByIds(found);
                        StoryReyclerViewAdapter storyReyclerViewAdapter = new StoryReyclerViewAdapter(stories,ctx);
                        recyclerView.setAdapter(storyReyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                    }
                }
            }
        });

        return view;
    }

}


