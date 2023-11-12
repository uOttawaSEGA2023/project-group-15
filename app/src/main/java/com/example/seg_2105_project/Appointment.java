package com.example.seg_2105_project;

import java.io.Serializable;
import java.util.*;

public class Appointment implements Serializable {

    private Calendar dateTime;
    private Doctor doctor;
    private Patient patient;
    private Status status;

    public Appointment(Calendar dateTime, Doctor doctor, Patient patient) {
        this.dateTime = dateTime;
        this.doctor = doctor;
        this.patient = patient;
        this.status = Status.PENDING;
    }

    public Calendar getDateTime() { return dateTime; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public Status getStatus() { return status; }
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
        String date = dateTime.get(Calendar.DAY_OF_MONTH) + "/" + dateTime.get(Calendar.MONTH) + "/" + dateTime.get(Calendar.YEAR);
        String time = dateTime.get(Calendar.HOUR) + ":" + dateTime.get(Calendar.MINUTE);
        return "Name: " + patient.getFirstName() + " " + patient.getLastName() + "      Date: " + date + " at " + time;
    }

}
