package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO set screen to sign in/sign up option page
        Intent intent = new Intent(getApplicationContext(), PatientScreen.class);
        startActivity(intent);
    }
}