package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UpcomingAppointmentDisplay extends AppCompatActivity {

    Appointment selectedAppt;
    TextView patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appointment_display);

        selectedAppt = (Appointment) getIntent().getSerializableExtra("Appointment");
        patientInfo = (TextView) findViewById(R.id.patientInformation);
        patientInfo.setText(selectedAppt.getPatient().display());
    }

    public void onClickAcceptAppointment(View view){
        //appointment is accepted -> add shift?

        //go back to upcoming appointments list
        startActivity(new Intent(this, DoctorUpcomingAppointments.class));
    }

    public void onClickRejectAppointment(View view){
        //appointment rejected -> idk the protocol


        //go back to upcoming appointments list
        startActivity(new Intent(this, DoctorUpcomingAppointments.class));
    }

    public void onClickBackButton(View view){
        Intent intent = new Intent(getApplicationContext(), DoctorUpcomingAppointments.class);
        startActivity(intent);
    }

}