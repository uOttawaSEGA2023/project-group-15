package com.example.seg_2105_project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Patient extends User {

    private long healthCardNumber;

    public Patient() {}
    public Patient(String firstName, String lastName, String email, String password, long phoneNumber, String address, long healthCardNumber) {
        super(firstName, lastName, email, password, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
    }

    public long getHealthCardNumber() {
        return healthCardNumber;
    }

    /*
    Gives a list of patients with a certain registration status from firebase
    @param  status                      Registration status of patients
    @param  dataSnapshot                DataSnapshot of patient information in firebase
    @return                             An ArrayList of patients
    @throws IllegalArguementException   if dataSnapshot is null or doesn't contain a snapshot for the patients
     */
    public static ArrayList<User> getPatients(Status status, DataSnapshot dataSnapshot) {

        ArrayList<User> patients = new ArrayList<User>();

        //Make sure dataSnapshot isn't null and contains the Patient path
        if (dataSnapshot.exists() && dataSnapshot.getRef().getKey().equals("Patients")) {
            for (DataSnapshot patient : dataSnapshot.getChildren()) {
                Patient p = patient.getValue(Patient.class);
                //Check registration status of patient and add it to list if it matches
                if (p.getRegistrationStatus() == status) {
                    patients.add(p);
                }
            }
        }
        else {
            throw new IllegalArgumentException("dataSnapshot should not be null and should be from the Patient path");
        }
        return patients;

    }

    /*
    * Acts as a setter and changes the registration status in Firebase
     */
    public void updateRegistrationStatus(Status registrationStatus) {
        super.updateRegistrationStatus(registrationStatus);

        //Get firebase reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("Patients");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Search through patients
                for (DataSnapshot patient : dataSnapshot.getChildren()) {
                    Patient p = patient.getValue(Patient.class);
                    if (p.getEmail().equals(getEmail())) {
                        //Change status
                        DatabaseReference reference = patient.getRef();
                        reference.child("registrationStatus").setValue(registrationStatus);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
    /*
    Reads through the database for patients and return Patient object if found
     */
    public static Patient getPatient(String email, String password, DataSnapshot dataSnapshot) {
        for (DataSnapshot patientSnapshot : dataSnapshot.getChildren()) {
            Patient patient = patientSnapshot.getValue(Patient.class);
            if (patient.getEmail().equals(email) && patient.getPassword().equals(password)) {
                return patient;
            }
        }
        return null;
    }

    /*
    Displays all the information of this patient
     */
    @Override
    public String display() {
        return super.display() +
                "\nHealth Card Number: " + healthCardNumber;
    }

}
