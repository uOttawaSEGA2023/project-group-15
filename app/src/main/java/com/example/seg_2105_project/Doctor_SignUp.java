package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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
        if (user.firstName.equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewFirstNameError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewFirstName);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewFirstName);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewFirstNameError);
            text.setVisibility(view.INVISIBLE);

        }
        if (user.lastName.equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewLastNameError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewLastName);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewLastName);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewLastNameError);
            text.setVisibility(view.INVISIBLE);

        }
        if (user.email.equals("") || !user.email.contains("@")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewEmailError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewEmail);
            text.setVisibility(view.INVISIBLE);

            //Clear input
            EditText input = findViewById(R.id.editTextEmailAddress);
            input.setText("");

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewEmail);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewEmailError);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.password.equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewPasswordError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewPassword);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewPassword);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewPasswordError);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.phoneNumber == -1 || user.phoneNumber < 100000000 || user.phoneNumber > 999999999) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewPhoneNumberError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewPhoneNumber);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewPhoneNumber);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewPhoneNumberError);
            text.setVisibility(view.INVISIBLE);

        }
        if (user.address.equals("")) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewAddressError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewAddress);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewAddress);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewAddressError);
            text.setVisibility(view.INVISIBLE);
        }
        if (user.employeeNumber == -1 || user.healthCardNumber < 1000000000) {
            //Set visibility of error message to visible
            TextView text = findViewById(R.id.textViewHealthNumberError);
            text.setVisibility(view.VISIBLE);

            //Set visibility of label to invisible
            text = findViewById(R.id.textViewHealthNumber);
            text.setVisibility(view.INVISIBLE);

            isValid = false;
        }
        else {
            //Reset visibilities
            TextView text = findViewById(R.id.textViewHealthNumber);
            text.setVisibility(view.VISIBLE);

            text = findViewById(R.id.textViewHealthNumberError);
            text.setVisibility(view.INVISIBLE);
        }
        return isValid;

    }
}