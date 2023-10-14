package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {
    Button signUpButton;
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        signUpButton = findViewById(R.id.button2);
        signInButton = findViewById(R.id.button);
    }

    public void onClick_SingIn(View view){
        startActivity(new Intent(getApplicationContext(),SignIn.class));
    }

    public void onClick_SignUp(View view){
        startActivity(new Intent(getApplicationContext(),SignUpPage.class));
    }
}