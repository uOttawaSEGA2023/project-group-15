package com.example.seg_2105_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BookAppointment extends AppCompatActivity {

    Doctor doctor;
    Calendar date = Calendar.getInstance();
    DataSnapshot appointmentSnapshot;
    DataSnapshot doctorSnapshot;
    DataSnapshot patientSnapshot;
    ListView listViewTimeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        doctor = (Doctor) getIntent().getSerializableExtra("Doctor");

        //Initialize UI variables
        CalendarView calendarView = findViewById(R.id.calendarViewShifts);
        listViewTimeSlots = findViewById(R.id.listViewTimeSlots);
        displayCalendar(calendarView);

        //Get database info
        DatabaseReference databaseReferenceAppointments = FirebaseDatabase.getInstance().getReference("Appointments");
        databaseReferenceAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentSnapshot = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference databaseReferencePatients = FirebaseDatabase.getInstance().getReference("Patients");
        databaseReferencePatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientSnapshot = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        DatabaseReference databaseReferenceDoctors = FirebaseDatabase.getInstance().getReference("Doctors");
        databaseReferenceDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorSnapshot = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //Read date chosen
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, day);
                ArrayList<Calendar> timeSlots = getTimeSlots(date);

                //Check if date has available time slots
                if (!timeSlots.isEmpty()) {
                    //Set UI
                    TextView textView = findViewById(R.id.textViewSelectTimeSlot);
                    textView.setVisibility(View.VISIBLE);
                    listViewTimeSlots.setVisibility(View.VISIBLE);
                    loadListView(timeSlots);

                    TextView textViewUnavailable = findViewById(R.id.textViewSlotUnavailable);
                    textViewUnavailable.setVisibility(View.INVISIBLE);

                }
                else {
                    //Set UI
                    TextView textViewUnavailable = findViewById(R.id.textViewSlotUnavailable);
                    textViewUnavailable.setVisibility(View.VISIBLE);

                    TextView textViewAvailable = findViewById(R.id.textViewSelectTimeSlot);
                    textViewAvailable.setVisibility(View.INVISIBLE);
                    listViewTimeSlots.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    public void onClickConfirmBooking(View view) {

    }

    /*
    Set the min and max date of the calendar based on doctor shifts
     */
    private void displayCalendar(CalendarView calendarView) {

        //Find min and max date in doctor shifts
        ArrayList<Shift> shifts = doctor.getShifts();
        Collections.sort(shifts, Shift.getShiftComparator());
        Calendar maxDate = shifts.get(shifts.size() - 1).retrieveStart();
        Calendar minDate = shifts.get(0).retrieveStart();

        calendarView.setMinDate(minDate.getTimeInMillis());
        calendarView.setMaxDate(maxDate.getTimeInMillis());

    }

    /*
    Get available time slots for user to choose from
     */
    private ArrayList<Calendar> getTimeSlots(Calendar date) {

        //Go through doctor shifts to get those on date
        ArrayList<Shift> shifts = doctor.getShifts();
        ArrayList<Calendar> timeSlots = new ArrayList<>();
        for (Shift shift : shifts) {
            //Check date of shift
            if (shift.retrieveStart().get(Calendar.DAY_OF_MONTH) ==  date.get(Calendar.DAY_OF_MONTH) &&
                    shift.retrieveStart().get(Calendar.MONTH) ==  date.get(Calendar.MONTH) &&
                    shift.retrieveStart().get(Calendar.YEAR) ==  date.get(Calendar.YEAR)) {

                //Iterate through shift in 30 minute increments and check availability
                Map<Calendar, Boolean> allTimeSlots = shift.getTimeSlotAvailability();
                Calendar time = shift.retrieveStart();
                while (time.before(shift.retrieveEnd())) {
                    if (allTimeSlots.get(time)) //if slot is available, add it
                        timeSlots.add(time);

                    //Increment
                    if (time.get(Calendar.MINUTE) == 30)
                        time.set(Calendar.HOUR, time.get(Calendar.HOUR) + 1);
                    time.set(Calendar.MINUTE, 0);

                }

            }
        }
        return timeSlots;

    }

    /*
    Loads the list view with open time slots
     */
    private void loadListView(ArrayList<Calendar> timeSlots) {
        ArrayList<String> timeSlotsStr = new ArrayList<>();
        for (Calendar time : timeSlots) {

            //Convert calendar time to string
            String timeStr = "";
            int hour = time.get(Calendar.HOUR);
            int minute = time.get(Calendar.MINUTE);
            if (hour < 10)
                timeStr += ("0" + hour);
            timeStr += ":";
            if (minute == 0)
                timeStr += "0";
            timeStr += minute;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, timeSlotsStr);
        listViewTimeSlots.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewTimeSlots.setAdapter(adapter);
    }

}