package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doctor_SignUp extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);
        auth = FirebaseAuth.getInstance();
    }

    public void onClickSignUp(View view) {

        //Get inputs of text views
        EditText input = findViewById(R.id.first_name_enter);
        String firstName = input.getText().toString();

        input = findViewById(R.id.last_name_enter);
        String lastName = input.getText().toString();

        input = findViewById(R.id.email_address_enter);
        String email = input.getText().toString();

        input = findViewById(R.id.account_password_enter);
        String password = input.getText().toString();

        input = findViewById(R.id.address_enter);
        String address = input.getText().toString();

        input = findViewById(R.id.phone_number_enter);
        String phoneNumberStr = input.getText().toString();
        int phoneNumber;

        input = findViewById(R.id.employee_num_enter);
        String employeeNumberStr = input.getText().toString();
        int employeeNumber;

        input = findViewById(R.id.specialty_enter);
        String specialtiesStr = input.getText().toString();
        String[] specialties;
        specialties = specialtiesStr.split(",");


        //Check if numerical text views are empty
        phoneNumber =  !phoneNumberStr.equals("")  ? Integer.parseInt(phoneNumberStr)  : -1;
        employeeNumber = !employeeNumberStr.equals("")  ? Integer.parseInt(employeeNumberStr)  : -1;

        //Create patient object
        Doctor doctor = new Doctor(firstName, lastName, email, password, phoneNumber, address, employeeNumber, specialties);

        //Validate input
        if (inputValid(view, doctor)) {

            //Write to database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseRef = database.getReference("Doctors");
            databaseRef.push().setValue(doctor);

            //Authenticate user
            userAuthentication(doctor);

        }

    }

    /*
     * Validates user input
     * @param view View of current activity
     * @param user Patient object that contains user info that they entered
     */
    private boolean inputValid(View view, Doctor user) {

        boolean isValid = true;

        //Check if each input is correct or empty
        if (user.getFirstName().equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.first_name);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.first_name_enter);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.first_name_enter);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.first_name);
            text.setVisibility(view.INVISIBLE);

        }
        if (user.getLastName().equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.last_name);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.last_name_enter);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.last_name_enter);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.last_name);
            text.setVisibility(view.INVISIBLE);

        }
        if (user.getEmail().equals("") || !user.getEmail().contains("@")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.email_address_enter);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.email_address);
            text.setVisibility(view.INVISIBLE);

            //Clear input
            EditText input = findViewById(R.id.email_address_enter);
            input.setText("");

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.email_address);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.email_address_enter);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.getPassword().equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.account_password_enter);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.account_pass);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.account_pass);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.account_password_enter);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.getPhoneNumber() == -1 || user.getPhoneNumber()< 100000000 || user.getPhoneNumber() > 999999999) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.phone_number_enter);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.phone_number);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.phone_number);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.phone_number_enter);
            text.setVisibility(view.INVISIBLE);

        }
        if (user.getAddress().equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.address_enter);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.address);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.address);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.address_enter);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.get_employee_number()== -1 || user.get_employee_number() < 1000000000) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.employee_num_enter);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.employee_num);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.employee_num);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.employee_num_enter);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.get_specialties().equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.specialty_enter);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.specialty);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.specialty);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.specialty_enter);
            text.setVisibility(view.INVISIBLE);
        }
        return isValid;

    }

    /*
    Authenticate user info
     */
    private void userAuthentication(Doctor doctor) {
        auth.createUserWithEmailAndPassword(doctor.getEmail(), doctor.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        //Check if authentication was successful
                        if (task.isSuccessful()) {
                            verifyEmail();

                        } else {
                            // If unsuccessful, display a message to the user.
                            Toast.makeText(Doctor_SignUp.this, "This email already has an account",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*
    Send email to user to verify their email
     */
    private void verifyEmail() {
        FirebaseUser user = auth.getCurrentUser();
        //Send email to user
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        //Send message to user that email has been sent and change screen
                        Toast.makeText(getApplicationContext(), "Verification email sent",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SignIn.class); //TODO switch to sign in screen
                        startActivity(intent);

                    }
                });
    }

}
