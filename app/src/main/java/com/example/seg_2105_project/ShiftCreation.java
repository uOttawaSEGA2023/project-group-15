package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShiftCreation extends AppCompatActivity {

    Calendar currentTime;

    Calendar calendar;
    CalendarView calendarView;
    Spinner timeStart;
    Spinner timeEnd;

    Button confirmDate;

    Doctor doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_creation);
        calendarView = findViewById(R.id.calendarView);
        currentTime = Calendar.getInstance();
        // Restrict dates to be selected
        calendarView.setMinDate(currentTime.getTimeInMillis());
        confirmDate = findViewById(R.id.confirmDate);
        calendar = Calendar.getInstance();
        timeStart = findViewById(R.id.timeStartInput);
        timeEnd = findViewById(R.id.timeEndInput);


        //Get doctor
        Intent intent = getIntent();
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        setDate(2001, 9, 12);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Toast.makeText(ShiftCreation.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });

        //Load times to time inputs
        ArrayList<String> times = new ArrayList<>();
        for (int i = 0; i <= 23; i ++) {
            times.add(i + ":00");
            times.add(i + ":30");
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

    public void onClickConfirmDate(View view) {
        long date = calendarView.getDate();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        // Parse start time
        int hoursStart, minutesStart;
        String selectedItemStart = (String) timeStart.getSelectedItem();
        String[] timeValuesStart = selectedItemStart.split(":");
        hoursStart = Integer.parseInt(timeValuesStart[0]);
        minutesStart = Integer.parseInt(timeValuesStart[1]);

        // Parse end time
        int hoursEnd, minutesEnd;
        String selectedItemEnd = (String) timeEnd.getSelectedItem();
        String[] timeValuesEnd = selectedItemEnd.split(":");
        hoursEnd = Integer.parseInt(timeValuesEnd[0]);
        minutesEnd = Integer.parseInt(timeValuesEnd[1]);

        // Ensure that the end time is after start time
        if (hoursEnd < hoursStart) {
            Toast.makeText(ShiftCreation.this, "Please select a different end time", Toast.LENGTH_SHORT).show();
        } else if (hoursEnd == hoursStart) {
            if (minutesEnd < minutesStart) {
                Toast.makeText(ShiftCreation.this, "Please select a different end time", Toast.LENGTH_SHORT).show();
            }
        }

        if ((calendar.get(Calendar.YEAR) == currentTime.get(Calendar.YEAR)) && (calendar.get(Calendar.MONTH) == currentTime.get(Calendar.MONTH)) && (calendar.get(Calendar.DAY_OF_MONTH) == currentTime.get(Calendar.DAY_OF_MONTH))) {
            if (hoursStart <= calendar.get(Calendar.HOUR_OF_DAY)) {
                Toast.makeText(ShiftCreation.this, "Please select a different time", Toast.LENGTH_SHORT).show();
            } else if (hoursStart == calendar.get(Calendar.HOUR_OF_DAY)) {
                if (minutesStart < calendar.get(Calendar.MINUTE)) {
                    Toast.makeText(ShiftCreation.this, "Please select a different time", Toast.LENGTH_SHORT).show();
                } else {
                    // add shift
                    Calendar shiftStart = Calendar.getInstance();
                    Calendar shiftEnd = Calendar.getInstance();
                    shiftStart.setTimeInMillis(calendar.getTimeInMillis());
                    shiftEnd.setTimeInMillis(calendar.getTimeInMillis());
                    shiftStart.set(Calendar.HOUR_OF_DAY, hoursStart);
                    shiftStart.set(Calendar.MINUTE, minutesStart);
                    shiftEnd.set(Calendar.HOUR_OF_DAY, hoursEnd);
                    shiftEnd.set(Calendar.MINUTE, minutesEnd);
                    Shift shiftToAdd = new Shift(shiftStart, shiftEnd);
                    doctor.addShift(shiftToAdd);


                }
            } else {
                // add shift
                Calendar shiftStart = Calendar.getInstance();
                Calendar shiftEnd = Calendar.getInstance();
                shiftStart.setTimeInMillis(calendar.getTimeInMillis());
                shiftEnd.setTimeInMillis(calendar.getTimeInMillis());
                shiftStart.set(Calendar.HOUR_OF_DAY, hoursStart);
                shiftStart.set(Calendar.MINUTE, minutesStart);
                shiftEnd.set(Calendar.HOUR_OF_DAY, hoursEnd);
                shiftEnd.set(Calendar.MINUTE, minutesEnd);
                Shift shiftToAdd = new Shift(shiftStart, shiftEnd);
                doctor.addShift(shiftToAdd);
            }
        } else {
            // add shift
            Calendar shiftStart = Calendar.getInstance();
            Calendar shiftEnd = Calendar.getInstance();
            shiftStart.setTimeInMillis(calendar.getTimeInMillis());
            shiftEnd.setTimeInMillis(calendar.getTimeInMillis());
            shiftStart.set(Calendar.HOUR_OF_DAY, hoursStart);
            shiftStart.set(Calendar.MINUTE, minutesStart);
            shiftEnd.set(Calendar.HOUR_OF_DAY, hoursEnd);
            shiftEnd.set(Calendar.MINUTE, minutesEnd);
            Shift shiftToAdd = new Shift(shiftStart, shiftEnd);
            doctor.addShift(shiftToAdd);
        }
    }

}