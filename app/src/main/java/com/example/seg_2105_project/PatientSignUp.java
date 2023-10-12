package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.*;
import android.widget.*;

import android.os.Bundle;

import java.util.*;

public class PatientSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
    }

    public void onClickSignUp(View view) {

        //Get inputs of text views
        EditText input = findViewById(R.id.editTextFirstName);
        String firstName = input.getText().toString();

        input = findViewById(R.id.editTextLastName);
        String lastName = input.getText().toString();

        input = findViewById(R.id.editTextEmailAddress);
        String email = input.getText().toString();

        input = findViewById(R.id.editTextPassword);
        String password = input.getText().toString();

        input = findViewById(R.id.editTextPostalAddress);
        String address = input.getText().toString();

        input = findViewById(R.id.editTextPhone);
        String phoneNumberStr = input.getText().toString();
        int phoneNumber;

        input = findViewById(R.id.editTextHealthNumber);
        String healthNumberStr = input.getText().toString();
        int healthNumber;

        //Check if numerical text views are empty
        phoneNumber =  !phoneNumberStr.equals("")  ? Integer.parseInt(phoneNumberStr)  : -1;
        healthNumber = !healthNumberStr.equals("") ? Integer.parseInt(healthNumberStr) : -1;

        //Create patient object
        Patient patient = new Patient(firstName, lastName, email, password, phoneNumber, address, healthNumber);

        //Validate input
        if (inputValid(view, patient)) {
            //TODO Write to database
            
            //Go to next screen
            Intent intent = new Intent(getApplicationContext(), MainActivity.class); //TODO set to sign in page
            startActivity(intent);
        }

    }

    /*
     * Validates user input
     * @param view View of current activity
     * @param user Patient object that contains user info that they entered
    */
    private boolean inputValid(View view, Patient user) {

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
        if (user.phoneNumber == -1) {
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
        if (user.healthCardNumber == -1) {
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