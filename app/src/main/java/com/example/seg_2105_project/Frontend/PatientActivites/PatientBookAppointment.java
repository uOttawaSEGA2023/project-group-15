package com.example.seg_2105_project.Frontend.PatientActivites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.seg_2105_project.Backend.Appointment;
import com.example.seg_2105_project.Backend.Doctor;
import com.example.seg_2105_project.Frontend.DoctorActivities.UpcomingAppointmentDisplay;
import com.example.seg_2105_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientBookAppointment extends AppCompatActivity {

    ListView listViewDoctors;
    ArrayAdapter<String> arrayAdapter;

    ArrayList<Doctor> doctors = new ArrayList<>();

    ArrayAdapter<Doctor> arrayAdapterDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_appointment2);

        listViewDoctors = findViewById(R.id.listViewDoctors);

        //obtaining references to the database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference doctorsRef = db.getReference("Doctors");
        String target = "one"; //for testing


        doctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                        Doctor doctor = doctorSnapshot.getValue(Doctor.class);

                        if (doctor != null) {
                            //Adding all the doctor specialties to the list
                            if (doctor.getSpecialties() != null) {
                                // The doctor has the targeted specialty
                                doctors.add(doctor);
                                loadListView();
                            }
                        }
                    }
                } else {
                    // No doctors found in the database
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get doctor that was clicked on
        listViewDoctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Doctor selectedAppointment = (Doctor) listViewDoctors.getItemAtPosition(position);
                //add intent for next class
                /*
                Intent intent = new Intent(getApplicationContext(), _____.class);
                intent.putExtra("Doctor", doctor);
                startActivity(intent); */
            }
        });
    }

    /**
     * Adds all the doctors appointments to listview layout
     */
    private void loadListView() {
        ListView listView = findViewById(R.id.listViewDoctors);
        arrayAdapterDoctor = new ArrayAdapter<Doctor>(getApplicationContext(),
                android.R.layout.simple_list_item_single_choice, doctors);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapterDoctor);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search for doctor by specialty");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapterDoctor.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }


}