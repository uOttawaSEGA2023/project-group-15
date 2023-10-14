package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    //method to direct user to the Sign In page
    public void onClickSignIn(View view){
        startActivity(new Intent(getApplicationContext(),SignIn.class));
    }


    //method to direct user to the Sign Up page
    public void onClickSignUp(View view){
        startActivity(new Intent(getApplicationContext(),SignUpPage.class));
    }
}