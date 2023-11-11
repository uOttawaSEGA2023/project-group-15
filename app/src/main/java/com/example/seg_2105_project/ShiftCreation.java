package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShiftCreation extends AppCompatActivity {

    Calendar currentTime;

    CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_creation);
        calendarView = findViewById(R.id.calendarView);
        currentTime = Calendar.getInstance();

        setDate(2023, 1, 1);
        getDate();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Toast.makeText(ShiftCreation.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void setDate(int year, int month, int day) {
        currentTime.set(Calendar.YEAR, year);
        currentTime.set(Calendar.MONTH, month - 1);
        currentTime.set(Calendar.DAY_OF_MONTH, day);

        long milliseconds = currentTime.getTimeInMillis();

        calendarView.setDate(milliseconds);
    }

    public void getDate() {
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        currentTime.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(currentTime.getTime());
        Toast.makeText(this,selected_date, Toast.LENGTH_SHORT).show();
    }

}