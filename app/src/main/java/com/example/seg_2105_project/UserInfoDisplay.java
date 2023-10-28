package com.example.seg_2105_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
        User user = (User) getIntent().getSerializableExtra("User");

        //Intent intent = new Intent(getApplicationContext(), User.class);
        //intent.putExtra("User", selectedUser);
        //startActivity(intent);

        TextView userInfo = (TextView) findViewById(R.id.infoDisplayText);
        userInfo.setText(user.display());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "status notification",
                    "Status Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

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
    }
    /*Method is called when request reject button is clicked
     */
    public void onClickReject(View view) {
        User user = (User) getIntent().getSerializableExtra("User");
        sendNotification(user);
    }
    /*Method sends a notification to the users screen indicating status of registration **Note the users notifications for the app must be enabled for this to work
     */
    public void sendNotification(User user) {
        String content;
        if (user.getRegistrationStatus() == User.RegistrationStatus.APPROVED) {
            content = "Congratulations! Your registration request has been approved";
        } else {
            content = "Your registration request has been rejected";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "status notification")
                .setContentTitle("HAMS profile status")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_background); // Set your notification icon

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        managerCompat.notify(1, builder.build());
    }

    public void sendEmail(User user) {

    }





}