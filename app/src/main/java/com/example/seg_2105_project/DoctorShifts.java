package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DoctorShifts extends AppCompatActivity {

    private ListView listViewShifts;
    private Button buttonDeleteShift;
    private Button buttonAddShift;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> shiftsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_shifts);

        // Initialize UI elements
        listViewShifts = findViewById(R.id.listViewShifts);
        buttonDeleteShift = findViewById(R.id.buttonDeleteShift);
        buttonAddShift = findViewById(R.id.buttonAddShift);

        // Initialize shifts list
        shiftsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shiftsList);
        listViewShifts.setAdapter(adapter);

        // click listeners
        listViewShifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Sedra your code should be going here!!!!!
            }
        });

        buttonDeleteShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorShifts.this, ShiftCreation.class);
                startActivity(intent);
            }
        });

        buttonAddShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorShifts.this, ShiftCreation.class);
                startActivity(intent);
            }
        });

        // Fetch shifts from Firebase
        fetchShiftsFromFirebase();
    }

    /*
     * Fetches shifts data from Firebase and updates the list view
     */
    private void fetchShiftsFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String doctorId = currentUser.getUid();
            DatabaseReference shiftsRef = FirebaseDatabase.getInstance().getReference("Doctors")
                    .child(doctorId)
                    .child("shifts");

            shiftsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    shiftsList.clear();

                    for (DataSnapshot shiftSnapshot : dataSnapshot.getChildren()) {
                        Shift shift = shiftSnapshot.getValue(Shift.class);
                        if (shift != null) {
                            shiftsList.add(shift.toString());
                        }
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

}
