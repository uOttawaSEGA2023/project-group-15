package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShiftCreation extends AppCompatActivity {

    Calendar currentTime;

    CalendarView calendarView;

    boolean validDate;

    Button confirmDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_creation);
        calendarView = findViewById(R.id.calendarView);
        confirmDate = findViewById(R.id.confirmDate);
        currentTime = Calendar.getInstance();
        validDate = true;


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                // Restrict dates to be selected
                validDate = checkValidDate(currentTime, year, month, day);
                if (!validDate){
                    Toast.makeText(ShiftCreation.this, "Please select a different date", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShiftCreation.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    // Method to check if the selected date from the calendarView is valid (disregarding time)
    private boolean checkValidDate(Calendar calendar1, int yearToCompare, int monthToCompare, int dayToCompare) {
        if (calendar1.get(Calendar.YEAR) > yearToCompare) { return false; }
        else if (calendar1.get(Calendar.YEAR) == yearToCompare) {
            if (calendar1.get(Calendar.MONTH) > monthToCompare) { return false; }
            else if (calendar1.get(Calendar.MONTH) == monthToCompare) {
                if (calendar1.get(Calendar.DAY_OF_MONTH) > dayToCompare) { return false; }
                else { return true; }
            } else { return true; }
        } else { return true; }
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
            // restrict calendarView date value to date and not time
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            // If the day chosen is the same as the current day, then check if time chosen is valid
            if (simpleDateFormat.format(date).equals(simpleDateFormat.format(currentTime.getTime()))) {
                // if time is valid
                // addShift()

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