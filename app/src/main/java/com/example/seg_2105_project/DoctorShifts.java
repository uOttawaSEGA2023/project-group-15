package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

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
    private Button buttonAddShift;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> shiftsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_shifts);

        // Initialize UI elements
        listViewShifts = findViewById(R.id.listViewShifts);
        buttonDeleteShift = findViewById(R.id.buttonDeleteShift);
        buttonAddShift = findViewById(R.id.buttonAddShift);

        // Initialize shifts list
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("Doctor");

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
                // Sedra your code should be going here!!!!!
                Shift selectedShift = (Shift) listViewShifts.getItemAtPosition(position);
            }
        });
                    /*
    For the Doctor Screen theres a button to view the shift list screen that has a deleteShiftbutton
    Button yesDeleteShiftButton;
    Button noDeleteShiftButton;
    (Initialize) In oncreate:
                              yesDeleteShiftButton = (Button) findViewByID(R.id.yesDeleteShiftButton (name of button in xml));
                              noDeleteShiftButton = (Button) findViewByID(R.id.noDeleteShiftButton (name of button in xml));

                              ListView listView = findViewById(R.id.listViewShifts);
                              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                              @Override
                                    public void onItemClick(View view, int position) {
                                            User selectedShift = (Shift) listView.getItemAtPosition(position);

    In onclickdeleteShiftButton:
    System.out.println("Select shift to delete.");
    onItemClick();
    System.out.println("Delete the selected shift?");
    if(onclickyesDeleteShiftButton){
            deleteShift(selectedShift);
            TextView text = findViewById(R.id.textViewSelectShiftToDelete);
            text.setVisibility(view.Invisible);
    }
    else{
            startActivity(new Intent(getApplicationContext(),ShiftsList.class));
            TextView text = findViewById(R.id.textViewSelectShiftToDelete);
            text.setVisibility(view.Invisible);
    }

    create a switch
    Switch autoApproveSwitch;
    autoApproveSwitch = (Switch) findViewByID(R.id.autoApproveSwitch (name in xml));
    onclickautoApproveSwitch
    autoApprove = true;
    */

        public void onClickDButtonDeleteShift(){
            TextView text = findViewById(R.id.); //ask user if to select the shift to be deleted
            text.setVisibility(view.Visible);
            onItemClick();
            TextView text = findViewById(R.id.); //confirm with user if they want to delete shift
            if (onClickButtonYes) {
                selectedShift.deleteshift();
                text.setVisibility(view.Invisible);
                text.setVisibility(view.Invisible);
            }

            else{
                text.setVisibility(view.Invisible);
            }


        }

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

}
