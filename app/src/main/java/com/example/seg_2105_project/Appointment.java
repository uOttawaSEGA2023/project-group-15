package com.example.seg_2105_project;

import java.util.*;

public class Appointment {

    private Date date;
    private Time time;
    private Doctor doctor;
    private Patient patient;
    private Status status;

    public Appointment(Date date, Time time, Doctor doctor, Patient patient) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.patient = patient;
        this.status = Status.PENDING;
    }

    public Date getDate() { return date; }
    public Time getTime() { return time;}
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public Status getStatus() { return status; }

}
