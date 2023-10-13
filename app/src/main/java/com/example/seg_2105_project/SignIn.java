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

public class SignIn extends AppCompatActivity {
    EditText email;
    EditText pswd;
    Button signInButton;
    private boolean validLogin = true;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText) findViewById(R.id.emailAddress);
        pswd = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);

        auth = FirebaseAuth.getInstance();
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

        //Authenticate user info
        auth.signInWithEmailAndPassword(email.getText().toString(), pswd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //send user to next screen
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            //don't change to next screen until valid login provided
                            Toast.makeText(SignIn.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
}