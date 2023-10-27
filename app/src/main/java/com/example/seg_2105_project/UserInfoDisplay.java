package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
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
        User user = (User) getIntent().getSerializableExtra("User");

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
        startActivity(new Intent(getApplicationContext(), RegistrationsInbox.class));
    }

    /*Method is called when request accept button is clicked
     */
    public void onClickAccept(View view) {
        User user = (User) getIntent().getSerializableExtra("User");
        sendNotification(user);
        //user.setRegistrationStatus(User.RegistrationStatus.valueOf("APPROVED"));
    }

    public void onClickReject(View view) {
        User user = (User) getIntent().getSerializableExtra("User");
        sendNotification(user);
        //user.setRegistrationStatus(User.RegistrationStatus.valueOf("REJECTED"));
    }

    public void sendNotification(User user) {
        String content;
        if (user.getRegistrationStatus().equals("APPROVED")) {
            content = "Congratulations! Your registration request has been approved";
        } else {
            content = "Your registration request has been rejected";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(UserInfoDisplay.this, "status notification");
        builder.setContentTitle("HAMS profile status");
        builder.setContentText(content);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(UserInfoDisplay.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(1, builder.build());
    }

}