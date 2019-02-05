package com.example.nir.stellarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


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
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_db:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                if (myDb.logout()) {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(MainActivity.this, Login.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainActivity.this.startActivity(myIntent);
                    finish();
                }
                return true;
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.db:
                viewPager.setCurrentItem(2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);

        myDb.removeAllTables();

        if (!myDb.isUserLoggedIn()) {
            Intent myIntent = new Intent(MainActivity.this, Login.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.this.startActivity(myIntent);
            finish();
        }

        viewPager = findViewById(R.id.fragmentContainer);
        setupViewPager(viewPager);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddStoryActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivityForResult(myIntent, 1);

//                    viewPager.setCurrentItem(2);
//                    mTextMessage.setText(R.string.title_dashboard);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                Toast.makeText(this, strEditText, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        StatePagerAdapter adapter = new StatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentStories(), "Stories");
        adapter.addFragment(new FragmentUser(), "User");
        adapter.addFragment(new FragmentDb(), "DB");
        adapter.addFragment(new FragmentSearch(), "Search");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }

}
