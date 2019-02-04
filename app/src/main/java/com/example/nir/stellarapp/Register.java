package com.example.nir.stellarapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    Context ctx = this;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView email = findViewById(R.id.email);
        final TextView pwd = findViewById(R.id.password);
        final TextView fname = findViewById(R.id.fnameText);
        final TextView lname = findViewById(R.id.lnameText);
        Button signupBtn = findViewById(R.id.registerBtn);
        myDb = new DatabaseHelper(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDb.registerUser(pwd.getText().toString(),email.getText().toString(),fname.getText().toString(),lname.getText().toString())) {
                    Toast.makeText(ctx, "User Created Successfuly, you can now login", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(Register.this, Login.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Register.this.startActivity(myIntent);
                    finish();
                }
                else {
                    Toast.makeText(ctx, "Register failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
