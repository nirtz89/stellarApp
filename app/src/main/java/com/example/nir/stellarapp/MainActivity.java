package com.example.nir.stellarapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;



public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private StatePagerAdapter mStatePagerAdapter;
    private ViewPager viewPager;
    DatabaseHelper myDb;

//Roy Comment
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    Intent myIntent = new Intent(MainActivity.this, AddStoryActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
//                    viewPager.setCurrentItem(2);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_db:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        viewPager = findViewById(R.id.fragmentContainer);
        setupViewPager(viewPager);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        StatePagerAdapter adapter = new StatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentStories(), "Stories");
        adapter.addFragment(new FragmentUser(), "User");
        adapter.addFragment(new FragmentDb(), "DB");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }

}
