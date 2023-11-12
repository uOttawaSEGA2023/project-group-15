package com.example.seg_2105_project;

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

    public Appointment(Calendar dateTime, Doctor doctor, Patient patient) {
        this.dateTime = dateTime;
        this.doctor = doctor;
        this.patient = patient;
        this.status = Status.PENDING;
    }

    public Appointment() {
        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, month);
        dateTime.set(Calendar.DAY_OF_MONTH, day);
        dateTime.set(Calendar.HOUR_OF_DAY, hours);
        dateTime.set(Calendar.MINUTE, minutes);
    }

    public Calendar retrieveDateTime() { return dateTime; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public Status getStatus() { return status; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }

    public void updateStatus(Status status) {
        this.status = status;
        User.updateFirebase("Doctors", "appointments", doctor.getAppointments(), doctor);
        User.updateFirebase("Patients", "appointments", patient.getAppointments(), patient);
    }

    /*
    * Adds appointment to doctor and patient appointment lists and updates Firebase
    */
    public void bookAppointment() {

        //Add appointment to lists
        doctor.addAppointment(this);
        patient.addAppointment(this);

        //Update doctor and patient in firebase
        User.updateFirebase("Doctors", "appointments", doctor.getAppointments(), doctor);
        User.updateFirebase("Patients", "appointments", patient.getAppointments(), patient);

    }

    public String toString() {
        String date = day + "/" + month + "/" + year;
        String time = hours + ":" + minutes;
        return "Name: " + patient.getFirstName() + " " + patient.getLastName() + "\t\tDate: " + date + " at " + time;
    }

}
