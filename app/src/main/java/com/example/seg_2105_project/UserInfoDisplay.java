package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoDisplay extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_display);

        backButton = (Button) findViewById(R.id.backButton);

        Intent intent = getIntent();
        User user = (User)getIntent().getSerializableExtra("User");

        //Intent intent = new Intent(getApplicationContext(), User.class);
        //intent.putExtra("User", selectedUser);
        startActivity(intent);

        TextView userInfo = (TextView) findViewById(R.id.infoDisplayText);
        userInfo.setText(user.display());

        /* user.display();

        TextView userFirstName = (TextView) findViewById(R.id.firstNameText);
        userFirstName.setText(user.getFirstName());

        TextView userLastName = (TextView) findViewById(R.id.lastNameText);
        userLastName.setText(user.getLastName());

        TextView userEmail = (TextView) findViewById(R.id.emailText);
        userEmail.setText(user.getEmail());

        TextView userPhoneNumber = (TextView) findViewById(R.id.phoneNumberText);
        userPhoneNumber.setText((int)user.getPhoneNumber());

        TextView userAddress = (TextView) findViewById(R.id.addressText);
        userAddress.setText(user.getAddress());
         */
    }



    /*Method is called when the back button is clicked
    Returns admin to the registrations inbox.*/
    public void onClickBackButton(View view) {
        startActivity(new Intent(getApplicationContext(),RegistrationsInbox.class));
    }

}