package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShiftCreation extends AppCompatActivity {

    Calendar currentTime;

    Calendar calendar;
    CalendarView calendarView;
    boolean validDate;
    Spinner timeStart;
    Spinner timeEnd;

    Button confirmDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_creation);
        calendarView = findViewById(R.id.calendarView);
        currentTime = Calendar.getInstance();
        calendarView.setMinDate(currentTime.getTimeInMillis());
        confirmDate = findViewById(R.id.confirmDate);
        calendar = Calendar.getInstance();
        timeStart = findViewById(R.id.timeStartInput);
        timeEnd = findViewById(R.id.timeEndInput);
        validDate = true;

        //Get doctor
        Intent intent = getIntent();
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                // Restrict dates to be selected
                //validDate = checkValidDate(currentTime, year, month, day);
                Toast.makeText(ShiftCreation.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();


            }
        });

        //Load times to time inputs
        ArrayList<String> times = new ArrayList<>();
        for (int i = 0; i <= 12; i ++) {
            for (int j = 0; j <= 55; j += 5) {
                if (j == 0 || j == 5)
                    times.add(i + ":0" + j);
                else
                    times.add(i + ":" + j);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, times);
        timeStart.setAdapter(adapter);
        timeEnd.setAdapter(adapter);

    }

    // Method to initialize calendar to a particular date
    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliseconds = calendar.getTimeInMillis();

        calendarView.setDate(milliseconds);
    }

    // Method to get date value from calendarView (NOT NECESSARY FOR NOW)
    /*
    public void getDate() {
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        currentTime.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(currentTime.getTime());
        Toast.makeText(this,selected_date, Toast.LENGTH_SHORT).show();
    }
    */

    public void onClickConfirmDate() {
        if (validDate) {
            long date = calendarView.getDate();
            Calendar calendar2 = Calendar.getInstance();
           // calendar2.set(Calendar)
            // restrict calendarView date value to date and not time
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            // If the day chosen is the same as the current day, then check if time chosen is valid
            if (simpleDateFormat.format(date).equals(simpleDateFormat.format(currentTime.getTime()))) {
                if (true)
                // if time is valid
                // addShift()
                timeStart.getSelectedItem();

                // if time is not valid
                Toast.makeText(ShiftCreation.this, "Please select a different time", Toast.LENGTH_SHORT).show();
            }
            // allow any time to be chosen

            //addShift()
        } else {
            Toast.makeText(ShiftCreation.this, "Please select a different date", Toast.LENGTH_SHORT).show();
        }
    }

}