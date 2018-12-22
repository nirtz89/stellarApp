package com.example.nir.stellarapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //<< this
        setContentView(R.layout.activity_login);

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
                if (emailInput.getText().toString().equalsIgnoreCase("nir")) {
                    Intent intent = new Intent(ctx, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(ctx, "FAIL", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
