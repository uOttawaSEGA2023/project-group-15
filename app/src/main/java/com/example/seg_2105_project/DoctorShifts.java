package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    private Button buttonYesDeleteShift;
    private Button buttonNoDeleteShift;
    private Switch autoApproveSwitch;

    private Doctor doctor;

    Shift selectedShift;

    private Button buttonAddShift;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> shiftsList = new ArrayList<>();

    public DoctorShifts() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_shifts);

        // Initialize UI elements
        listViewShifts = findViewById(R.id.listViewShifts);
        buttonDeleteShift = findViewById(R.id.buttonDeleteShift);
        buttonAddShift = findViewById(R.id.buttonAddShift);

        buttonYesDeleteShift = findViewById(R.id.buttonYesDeleteShift);
        buttonNoDeleteShift = findViewById(R.id.buttonNoDeleteShift);
        autoApproveSwitch = findViewById((R.id.autoApproveSwitch));

        //sets the switch to the correct initial value ("on" or "off") depending on the value of the autoApprove boolean
        if(doctor.getAutoApprove()){
            autoApproveSwitch.setChecked(true);
        }
        else{
            autoApproveSwitch.setChecked(false);
        }

        // Initialize shifts list
        doctor = (Doctor) getIntent().getSerializableExtra("Doctor");
        ArrayList<Shift> shifts = doctor.getShifts();
        if (shifts != null) {
            for(Shift shift : shifts) {
                shiftsList.add(shift.toString());
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shiftsList);
            listViewShifts.setAdapter(adapter);
        }


        // click listeners
        listViewShifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedShift = (Shift) listViewShifts.getItemAtPosition(position);
            }
        });


        /*buttonDeleteShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorShifts.this, ShiftCreation.class);
                intent.putExtra("Doctor", doctor);
                startActivity(intent);
            }
        });*/

        buttonAddShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorShifts.this, ShiftCreation.class);
                intent.putExtra("Doctor", doctor);
                startActivity(intent);
            }
        });

        // Fetch shifts from Firebase
        //fetchShiftsFromFirebase();

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

    //Method is called if doctor wants to delete a selected shift
    public void onClickDeleteShiftButton(View view) {
        TextView text1 = findViewById(R.id.textWarning);
        TextView text2 = findViewById(R.id.textConfirmation); //confirm with user if they want to delete shift

        if (selectedShift == null) {
            text1.setVisibility(view.VISIBLE);
        } else {
            text1.setVisibility(View.INVISIBLE);
            text2.setVisibility(View.VISIBLE);
            buttonYesDeleteShift.setVisibility(View.VISIBLE);
            buttonNoDeleteShift.setVisibility(View.VISIBLE);
            buttonAddShift.setVisibility(View.INVISIBLE);
            buttonDeleteShift.setVisibility(View.INVISIBLE);
        }
    }


    //Method is called if doctor confirms the removal of a shift
    public void onClickYesDeleteShiftButton(View view){
        TextView text2 = findViewById(R.id.textConfirmation);
        doctor.deleteShift(selectedShift);
        text2.setVisibility(View.INVISIBLE);
        buttonYesDeleteShift.setVisibility(View.INVISIBLE);
        buttonNoDeleteShift.setVisibility(View.INVISIBLE);
        buttonAddShift.setVisibility(View.VISIBLE);
        buttonDeleteShift.setVisibility(View.VISIBLE);
    }

    //Method is called if doctor does not want to delete a selected shift
    public void onClickNoDeleteButton(View view){
        TextView text2 = findViewById(R.id.textConfirmation);
        text2.setVisibility(View.INVISIBLE);
        buttonYesDeleteShift.setVisibility(View.INVISIBLE);
        buttonNoDeleteShift.setVisibility(View.INVISIBLE);
        buttonAddShift.setVisibility(View.VISIBLE);
        buttonDeleteShift.setVisibility(View.VISIBLE);
    }

    //allows the doctor to automatically approve future appointments
    public void onClickAutoApproveSwitch(View view) {
        if (autoApproveSwitch.isChecked()) {
            doctor.updateAutoApprove(true);
        } else {
            doctor.updateAutoApprove(false);
        }
    }

}
