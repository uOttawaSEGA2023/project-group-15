package com.example.seg_2105_project.Frontend.PatientActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.seg_2105_project.Backend.Appointment;
import com.example.seg_2105_project.Backend.Doctor;
import com.example.seg_2105_project.R;

public class PastAppointmentDisplay extends AppCompatActivity {

    RatingBar rating;
    Doctor doctor;
    Appointment appt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_past_appointment_display);

        appt = (Appointment) getIntent().getSerializableExtra("Appointment");
        doctor = appt.getDoctor();

        //setting the text with the appointment information
        TextView apptInfo = findViewById(R.id.appointmentInformation);
        String information = appt.getDateAndTime() + "\nDoctor: " + doctor.getLastName() + ", " + doctor.getFirstName();
        apptInfo.setText(information);

        rating = findViewById(R.id.ratingBar);

    }

    public void onClickSubmitRating(View view){
        Float ratingNumber = rating.getRating();
        appt.rateDoctor(ratingNumber);

        Button button = findViewById(R.id.submitRating);
        button.setVisibility(View.INVISIBLE);
        rating.setVisibility(View.INVISIBLE);

        TextView textView = findViewById(R.id.rateDoctorText);
        textView.setText("You have already rated this doctor.");
    }
}