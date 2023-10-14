package com.example.seg_2105_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Button patientButton = findViewById(R.id.button1);
        Button doctorButton = findViewById(R.id.button2);

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_PatientSignUp(v);
            }
        });

        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_DoctorSignUp(v);
            }
        });
    }

    public void onClick_PatientSignUp(View view) {
        Intent intent = new Intent(this, PatientSignUp.class);
        startActivity(intent);
    }

    public void onClick_DoctorSignUp(View view) {
        Intent intent = new Intent(this, Doctor_SignUp.class);
        startActivity(intent);
    }
}
