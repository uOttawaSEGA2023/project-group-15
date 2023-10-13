package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    EditText email;
    EditText pswd;
    Button signInButton;
    private boolean validLogin = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.emailAddress);
        pswd = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);
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
        //add another else if statement here to determine whether the user's email and/or password
        //correspond to information in the database

        if (validLogin) {
            //send user to next screen
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
        } else {
            //don't change to next screen until valid login provided
            Toast.makeText(SignIn.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            validLogin = true;

        }

    }
}