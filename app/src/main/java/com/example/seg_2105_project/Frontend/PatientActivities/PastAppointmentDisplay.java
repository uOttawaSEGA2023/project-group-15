package com.example.seg_2105_project.Frontend.PatientActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.seg_2105_project.Backend.Appointment;
import com.example.seg_2105_project.Backend.Doctor;
import com.example.seg_2105_project.R;

public class PastAppointmentDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_past_appointment_display);


        Appointment appt = (Appointment) getIntent().getSerializableExtra("Appointment");

        TextView apptInfo = findViewById(R.id.appointmentInformation);

        String information = appt.getDoctor().display(); //TO ADD: appointment date and time information as well

        apptInfo.setText(information);


    }
}