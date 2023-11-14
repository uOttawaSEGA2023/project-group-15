package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoctorScreen extends AppCompatActivity {

    TextView welcomeMessage;
    Doctor doctor;
    String name;///Doctor name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_screen);
        welcomeMessage = findViewById(R.id.welcomeMessage);

        //Get user profile
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                name = profile.getDisplayName();
            }
        }

        //Get doctor
        this.doctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        welcomeMessage.setText( "Welcome " + name + "! You are logged in as a doctor ");

    }

    public void onClickSignOutButton(View view) {
        //Transition to home screen of app
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(intent);

    }

    public void onClickUpcomingAppointments(View view) {
        Intent intent = new Intent(getApplicationContext(), DoctorUpcomingAppointments.class);
        intent.putExtra("Doctor", doctor);
        startActivity(intent);
    }

    public void onClickPastAppointments(View view) {
        Intent intent = new Intent(getApplicationContext(), DoctorPastAppointments.class);
        intent.putExtra("Doctor", doctor);
        startActivity(intent);
    }

    public void onClickUpcomingShifts(View view) {
        Intent intent = new Intent(getApplicationContext(), DoctorShifts.class);
        intent.putExtra("Doctor", doctor);
        startActivity(intent);
    }
}