package com.example.seg_2105_project.Frontend.PatientActivites;

import com.example.seg_2105_project.Frontend.WelcomeScreen;
import com.example.seg_2105_project.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class
PatientScreen extends AppCompatActivity {

    TextView welcomeMessage;
    String name;///patient name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_screen);
        welcomeMessage = findViewById(R.id.welcomeMessage);

        //Get user profile
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                name = profile.getDisplayName();
            }
        }

        welcomeMessage.setText( "Welcome " + name + "! You are logged in as a patient ");

    }

    public void onClickSignOutButton(View view) {
        //Transition to home screen of app
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(intent);

    }
}