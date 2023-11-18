package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DataSnapshot doctorSnapshot;
    DataSnapshot patientSnapshot;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(getApplicationContext(), BookAppointment.class);
        //startActivity(intent);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorSnapshot = snapshot;
                intent.putExtra("Doctor", Doctor.getDoctor("apath059@uottawa.ca", "password", doctorSnapshot));
                start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Patients");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientSnapshot = snapshot;
                intent.putExtra("Patient", Patient.getPatient("aniverma15@gmail.com", "password", patientSnapshot));
                start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void start() {
        if (doctorSnapshot != null && patientSnapshot != null) {
            startActivity(intent);
        }
    }

}