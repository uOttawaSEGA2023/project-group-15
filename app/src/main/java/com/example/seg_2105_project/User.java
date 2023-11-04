package com.example.seg_2105_project;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long phoneNumber;
    private String address;
    private Status registrationStatus;
    private ArrayList<Appointment> appointments;

    public User(String firstName, String lastName, String email, String password, long phoneNumber, String address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.registrationStatus = Status.PENDING;
        this.appointments = new ArrayList<>();

    }

    public User() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
    public Status getRegistrationStatus() { return registrationStatus; }
    public ArrayList<Appointment> getAppointments() { return appointments; }

    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    /*
     * Acts as a setter for registration status
     */
    public void updateRegistrationStatus(Status registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    /*
     * Adds an appointment to the associated doctor and patient's list of appointments and updates Firebase
     * @param  appointment  Appointment to be added to list and firebase
     */
    public void addAppointment(Appointment appointment) {

        //Get associated patient and doctor
        User doctor = appointment.getDoctor();
        User patient = appointment.getPatient();

        //Add appointment to lists
        doctor.addAppointmentToList(appointment);
        patient.addAppointmentToList(appointment);

        //Update doctor in firebase
        DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference("Doctors");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Look for doctor in Firebase
                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {

                    Doctor d = doctorSnapshot.getValue(Doctor.class);
                    if (d.getEmail().equals(doctor.getEmail())) {
                        //Update appointments
                        DatabaseReference reference = doctorSnapshot.getRef();
                        reference.child("appointments").setValue(doctor.getAppointments());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //Update patient in firebase
        DatabaseReference patientRef = FirebaseDatabase.getInstance().getReference("Patient");
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Look for patient in Firebase
                for (DataSnapshot patientSnapshot : snapshot.getChildren()) {

                    Patient p = patientSnapshot.getValue(Patient.class);
                    if (p.getEmail().equals(patient.getEmail())) {
                        //Update appointments
                        DatabaseReference reference = patientSnapshot.getRef();
                        reference.child("appointments").setValue(patient.getAppointments());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    /*
    * Displays all the information of this user
     */
    public String display() {

        return "Name: " + this +
                "\nEmail: " + this.email +
                "\nPhone Number: " + this.phoneNumber +
                "\nAddress: " + this.address;

    }

    /*
    * Adds the appointment to the list
     */
    private void addAppointmentToList(Appointment a) {
        this.appointments.add(a);
    }

}

