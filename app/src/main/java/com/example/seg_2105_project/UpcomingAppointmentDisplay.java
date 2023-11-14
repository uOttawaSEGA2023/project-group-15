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
        selectedAppt.updateStatus(Status.APPROVED);

        //go back to upcoming appointments list
        Intent intent = new Intent(this, DoctorUpcomingAppointments.class);
        intent.putExtra("Doctor", getIntent().getSerializableExtra("Doctor"));
        startActivity(intent);
    }

    public void onClickRejectAppointment(View view){
        selectedAppt.updateStatus(Status.REJECTED);

        //go back to upcoming appointments list
        Intent intent = new Intent(this, DoctorUpcomingAppointments.class);
        intent.putExtra("Doctor", getIntent().getSerializableExtra("Doctor"));
        startActivity(intent);
    }

    public void onClickBackButton(View view){
        Intent intent = new Intent(this, DoctorUpcomingAppointments.class);
        intent.putExtra("Doctor", getIntent().getSerializableExtra("Doctor"));
        startActivity(intent);
    }

}