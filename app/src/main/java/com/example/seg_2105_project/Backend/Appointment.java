package com.example.seg_2105_project.Backend;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.*;

public class Appointment implements Serializable {

    private Calendar dateTime;
    private Doctor doctor;
    private Patient patient;
    private Status status;
    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;
    private int id;
    private boolean rated;

    public Appointment(Calendar dateTime, Doctor doctor, Patient patient) {
        this.dateTime = dateTime;
        this.doctor = doctor;
        this.patient = patient;
        this.year = dateTime.get(Calendar.YEAR);
        this.month = dateTime.get(Calendar.MONTH);
        this.day = dateTime.get(Calendar.DAY_OF_MONTH);
        this.hours = dateTime.get(Calendar.HOUR_OF_DAY);
        this.minutes = dateTime.get(Calendar.MINUTE);
        this.id = (int) (Math.random()*100000);

        rated = false;

        //Set status based on doctor preferences
        if (doctor.getAutoApprove()) {
            this.status = Status.APPROVED;
            doctor.updateShiftAvailability(retrieveDateTime(), false);
        }
        else
            this.status = Status.PENDING;
    }

    public Appointment() { }

    public Calendar retrieveDateTime() {
        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, month);
        dateTime.set(Calendar.DAY_OF_MONTH, day);
        dateTime.set(Calendar.HOUR_OF_DAY, hours);
        dateTime.set(Calendar.MINUTE, minutes);
        return dateTime;
    }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public Status getStatus() { return status; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public int getID() { return id; }

    public void updateStatus(Status status) {
        this.status = status;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Appointments");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Search through appointments
                for (DataSnapshot appointmentSnapshot: snapshot.getChildren()) {
                    Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                    if (appointment.getID() == id) {
                        //Change status
                        appointmentSnapshot.getRef().child("status").setValue(status);

                        //Update doctor's shift availability
                        if(status == Status.APPROVED)
                            doctor.updateShiftAvailability(retrieveDateTime(), false);
                        else if (status == Status.REJECTED)
                            doctor.updateShiftAvailability(retrieveDateTime(), true);

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    /*
    * Adds appointment to Firebase
    */
    public void bookAppointment() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Appointments");
        databaseReference.push().setValue(this);

    }

    public String getDateAndTime(){
        return ("Date: " + day + "/" + month + "/" + year + " at " + hours + ":" + minutes) ;
    }

    public boolean isRated(){
        return rated;
    }
    public void rateDoctor(float rating){
        if(!rated){
            doctor.updateRating(rating);
            rated = true;
        }
    }

    public String toString() {
        String date = day + "/" + month + "/" + year;
        String time = hours + ":" + minutes;

        if (minutes == 0)
            time += "0";

        return "Patient: " + patient.getFirstName() + " " + patient.getLastName() +
                " | Date: " + date + " at " + time +
                " | Status " + status;
    }

}
