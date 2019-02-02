package com.example.nir.stellarapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Post;
import model.Story;

public class StoryReyclerViewAdapter extends RecyclerView.Adapter<StoryReyclerViewAdapter.ViewHolder> {

    private ArrayList<Story> stories = new ArrayList<>();
    private Context mContext;

    public StoryReyclerViewAdapter(ArrayList<Story> stories, Context mContext) {
        this.stories = stories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_story_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = stories.get(position).getFirstPost();
        holder.txt.setText(post.getDesc());
        holder.imgView.setImageBitmap(post.getImage());
        holder.comprised.setText("Comprised of " + stories.get(position).getNumOfPosts() + " tellings");
        holder.uploadedBy.setText("As told by " + stories.get(position).getUserFullName());
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, StoryActivity.class);
                myIntent.putExtra("storyId", ""+post.getStoryId());
                mContext.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;
        TextView comprised;
        ImageView imgView;
        FrameLayout frameLayout;
        TextView uploadedBy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comprised = itemView.findViewById(R.id.comprisedLabel);
            img = itemView.findViewById(R.id.imageView);
            txt = itemView.findViewById(R.id.textView8);
            imgView = itemView.findViewById(R.id.imgBackground);
            frameLayout = itemView.findViewById(R.id.parentLayout);
            uploadedBy = itemView.findViewById(R.id.uploadedBy);

        }
    }

}
