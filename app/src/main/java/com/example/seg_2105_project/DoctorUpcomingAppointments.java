package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class DoctorUpcomingAppointments extends AppCompatActivity {
    ArrayList<Appointment> appointments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_upcoming_appointments);

        //obtaining references to the database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference doctorRef = db.getReference("Doctors");

        //retrieve data for doctors
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date currentDate = new Date();
                appointments.addAll(User.getSpecificAppointments(snapshot, false, currentDate));
                loadListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

        //Get user that was clicked on
        ListView listView = findViewById(R.id.listViewUpcomingAppointments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Appointment selectedAppointment = (Appointment) listView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), UpcomingAppointmentDisplay.class);
                intent.putExtra("Appointment", selectedAppointment);
                startActivity(intent);
            }
        });


    }
    /**
     * Adds all the upcoming appointments to listview layout
     */
    private void loadListView() {
        ListView listView = findViewById(R.id.listViewUpcomingAppointments);
        ArrayAdapter<Appointment> arrayAdapterDoctor = new ArrayAdapter<Appointment>(getApplicationContext(),
                android.R.layout.simple_list_item_single_choice, appointments);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapterDoctor);
    }
}