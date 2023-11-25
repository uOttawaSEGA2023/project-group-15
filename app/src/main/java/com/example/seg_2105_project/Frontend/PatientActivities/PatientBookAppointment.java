package com.example.seg_2105_project.Frontend.PatientActivities;

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
import android.widget.Filter;
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
    ArrayList<Doctor> doctors = new ArrayList<>();

    ArrayAdapter<Doctor> arrayAdapterDoctor;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_appointment2);

        listViewDoctors = findViewById(R.id.listViewDoctors);
        searchView = findViewById(R.id.searchView);

        listViewDoctors.setVisibility(View.GONE);

        //obtaining references to the database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference doctorsRef = db.getReference("Doctors");

        arrayAdapterDoctor = new ArrayAdapter<Doctor>(this, android.R.layout.simple_list_item_single_choice, doctors) {
            @NonNull
            @Override
            public Filter getFilter() {
                System.out.println("HERE");
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        String filterPattern = constraint.toString().toLowerCase().trim();
                        ArrayList<Doctor> filteredDoctors = new ArrayList<>();


                        for (Doctor doctor : doctors) {
                            if (doctor.getSpecialties() != null) {
                                for (String specialty : doctor.getSpecialties()) {
                                    if (specialty.toLowerCase().contains(filterPattern)) {
                                        filteredDoctors.add(doctor);
                                        break;
                                    }
                                }
                            }
                        }

                        FilterResults results = new FilterResults();
                        results.values = filteredDoctors;
                        results.count = filteredDoctors.size();
                        return results;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        clear();
                        addAll((ArrayList<Doctor>) results.values);
                        notifyDataSetChanged();
                    }

                };
            }
        };

        listViewDoctors.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewDoctors.setAdapter(arrayAdapterDoctor);


        doctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    doctors.clear();
                    for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                        Doctor doctor = doctorSnapshot.getValue(Doctor.class);

                        if (doctor != null) {
                            //Adding all the doctor specialties to the list
                            if (doctor.getSpecialties() != null) {
                                // The doctor has the targeted specialty
                                doctors.add(doctor);
                            }
                        }
                    }
                    arrayAdapterDoctor.notifyDataSetChanged();
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

                Intent intent = new Intent(getApplicationContext(), BookAppointment.class);
                intent.putExtra("Doctor", selectedAppointment);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchView.setQueryHint("Type here to search for doctor by specialty");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listViewDoctors.setVisibility(View.VISIBLE);
                arrayAdapterDoctor.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }


}