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


public class RegistrationsInbox extends AppCompatActivity {
    ArrayList<User> usersList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations_inbox);

        //obtaining references to the database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference patientRef = db.getReference("Patients");
        DatabaseReference doctorRef = db.getReference("Doctors");

        //retrieve data
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //adds all patients with pending status to list
                usersList.addAll(Patient.getPatients(User.RegistrationStatus.PENDING, snapshot));
                loadListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.addAll(Doctor.getDoctors(User.RegistrationStatus.PENDING, snapshot));
                loadListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });

        //Get user that was clicked on
        ListView listView = findViewById(R.id.listViewRegistrationRequests);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                User selectedUser = (User) listView.getItemAtPosition(position);

                //Intent intent = new Intent(getApplicationContext(), _____.class);
                //intent.putExtra("User", selectedUser);
                //startActivity(intent);
            }
        });
    }

    private void loadListView(){
        ListView listView = findViewById(R.id.listViewRegistrationRequests);
        ArrayAdapter<User> arrayAdapterDoctor = new ArrayAdapter<User>(getApplicationContext(),
                android.R.layout.simple_list_item_single_choice, usersList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapterDoctor);
    }


}