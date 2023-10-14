package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText email;
    EditText pswd;
    Button signInButton;
    private boolean validLogin = true;
    private boolean admin = false;
    FirebaseAuth auth;
    DataSnapshot patientDataSnapshot;
    DataSnapshot doctorDataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.emailAddress);
        pswd = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);

        auth = FirebaseAuth.getInstance();

        // Get a reference to database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference patientRef = database.getReference("Patients");
        DatabaseReference doctorRef = database.getReference("Doctors");

        //Read data
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                patientDataSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctorDataSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
    public void onClickSignInButton(View view) {
        //email is left blank
        if (email.getText().toString().equals("")) {
            validLogin = false;
            email.setError("Cannot be left blank");
        }
        //password is left blank
        if (pswd.getText().toString().equals("")) {
            validLogin = false;
            pswd.setError("Cannot be left blank");
        }

        if (validLogin) {
            //Authenticate user info
            auth.signInWithEmailAndPassword(email.getText().toString(), pswd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent;
                                //send user to corresponding user screen
                                if(getDoctor(email.toString(), pswd.toString()) == null && !admin){
                                    intent = new Intent(getApplicationContext(), PatientScreen.class);
                                } else {
                                    intent = new Intent(getApplicationContext(), DoctorScreen.class);
                                }
                                startActivity(intent);
                               // Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                //don't change to next screen until valid login provided
                                Toast.makeText(SignIn.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
        else { validLogin = true; }

    }

    /*
    Reads through the database for patients and return Patient object if found
     */
    private Patient getPatient(String email, String password) {

        for(DataSnapshot patients : patientDataSnapshot.getChildren()) {
            Patient p = patients.getValue(Patient.class);
            if (p.getEmail().equals(email) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;

    }
    /*
    Reads through the database for doctors and return Doctor object if found
     */
    private Doctor getDoctor(String email, String password) {

        for(DataSnapshot doctors : doctorDataSnapshot.getChildren()) {
            Doctor d = doctors.getValue(Doctor.class);
            if (d.getEmail().equals(email) && d.getPassword().equals(password)) {
                return d;
            }
        }
        return null;

    }

}