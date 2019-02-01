package com.example.nir.stellarapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    Context ctx = this;
//    TextView email = findViewById(R.id.email);
//    TextView pwd = findViewById(R.id.password);
    Button signupBtn = findViewById(R.id.registerBtn);
//    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //<< this
        setContentView(R.layout.activity_register);

//        myDb = new DatabaseHelper(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDb.registerUser(pwd.getText().toString(),email.getText().toString(),"Nir","Tzezana")) {
                    Toast.makeText(ctx, "User Created Successfuly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
