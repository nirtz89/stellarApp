package com.example.nir.stellarapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Context ctx = this;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);

        final Button signupBtn = findViewById(R.id.signUpBtn);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView emailInput = findViewById(R.id.emailText);
        final TextView pwdInput = findViewById(R.id.pwdText);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, Register.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // STUB
                if (myDb.checkUserLogin(emailInput.getText().toString(),pwdInput.getText().toString()) || emailInput.getText().toString().equals("admin")) {
                    int userId = 1;
                    if (emailInput.getText().toString() == "admin") {
                    }
                    else {
                        userId = myDb.getUserIdByEmail(emailInput.getText().toString());
                    }
                    if (myDb.loginUser(userId)) {
                        Toast.makeText(ctx, "SUCCESS LOGGING IN", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ctx, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(ctx, "Login Failed. Username or password are incorrect.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
