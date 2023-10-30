package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText email;
    EditText pswd;
    TextView signInError;
    Button signInButton;
    private boolean validLogin = true;
    FirebaseAuth auth;
    DataSnapshot patientDataSnapshot;
    DataSnapshot doctorDataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.emailAddress);
        pswd = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        signInError = findViewById(R.id.signInError);

        auth = FirebaseAuth.getInstance();

        // Get a reference to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference patientRef = database.getReference("Patients");
        DatabaseReference doctorRef = database.getReference("Doctors");

        // Read data
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
        signInError.setVisibility(View.GONE);  // Hide any previous error message
        if (email.getText().toString().isEmpty()) {
            validLogin = false;
            email.setError("Cannot be left blank");
        }
        if (pswd.getText().toString().isEmpty()) {
            validLogin = false;
            pswd.setError("Cannot be left blank");
        }

        if (validLogin) {
            auth.signInWithEmailAndPassword(email.getText().toString(), pswd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    if (isAdmin(user.getEmail())) {
                                        Intent intent = new Intent(getApplicationContext(), AdminScreen.class);
                                        startActivity(intent);
                                    } else if (isDoctorApproved(user.getEmail())) {
                                        Intent intent = new Intent(getApplicationContext(), DoctorScreen.class);
                                        startActivity(intent);
                                    } else if (isPatientApproved(user.getEmail())) {
                                        Intent intent = new Intent(getApplicationContext(), PatientScreen.class);
                                        startActivity(intent);
                                    } else if (isPatientRejected(user.getEmail()) || isDoctorRejected(user.getEmail())) {
                                        signInError.setText("Your registration was rejected. Contact the Administrator at (613)-614-6123");
                                        signInError.setVisibility(View.VISIBLE);
                                    } else {
                                        signInError.setText("Your registration has not been approved yet.");
                                        signInError.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                // Handle invalid email or password
                                signInError.setText("Invalid email or password");
                                signInError.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else {
            validLogin = true;
        }
    }


    private boolean isPatientRejected(String email) {
        for (DataSnapshot patient : patientDataSnapshot.getChildren()) {
            Patient p = patient.getValue(Patient.class);
            if (p.getEmail().equals(email) && p.getRegistrationStatus() == User.RegistrationStatus.REJECTED) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoctorRejected(String email) {
        for (DataSnapshot doctor : doctorDataSnapshot.getChildren()) {
            Doctor d = doctor.getValue(Doctor.class);
            if (d.getEmail().equals(email) && d.getRegistrationStatus() == User.RegistrationStatus.REJECTED) {
                return true;
            }
        }
        return false;
    }


    private boolean isPatientApproved(String email) {
        for (DataSnapshot patient : patientDataSnapshot.getChildren()) {
            Patient p = patient.getValue(Patient.class);
            if (p.getEmail().equals(email) && p.getRegistrationStatus() == User.RegistrationStatus.APPROVED) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoctorApproved(String email) {
        for (DataSnapshot doctor : doctorDataSnapshot.getChildren()) {
            Doctor d = doctor.getValue(Doctor.class);
            if (d.getEmail().equals(email) && d.getRegistrationStatus() == User.RegistrationStatus.APPROVED) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdmin(String email) {
        if (email.equals(Administrator.email) && pswd.getText().toString().equals(Administrator.password)) {
            return true;
        }
        return false;
    }
}