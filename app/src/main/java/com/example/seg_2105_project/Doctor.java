package com.example.seg_2105_project;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Doctor extends User {
    int employee_number;
    ArrayList<String> specialties;

    public Doctor() {}
    public Doctor(String firstName, String lastName, String email, String password, long phoneNumber, String address, int employee_number, ArrayList<String> specialties) {
        super(firstName, lastName, email, password, phoneNumber, address);
        this.employee_number = employee_number;
        this.specialties = specialties;
    }

    public int get_employee_number() {
        return employee_number;

    }

    public ArrayList<String> get_specialties() {
        return specialties;
    }

    /*
    Gives a list of doctors with a certain registration status from firebase
    @param  status                      Registration status of doctors
    @param  dataSnapshot                DataSnapshot of doctor information in firebase
    @return                             An ArrayList of doctors
    @throws IllegalArguementException   if dataSnapshot is null or doesn't contain a snapshot for the doctors
     */
    public static ArrayList<User> getDoctors(RegistrationStatus status, DataSnapshot dataSnapshot) {

        ArrayList<User> doctors = new ArrayList<User>();

        //Make sure dataSnapshot isn't null and contains the Doctor path
        if (dataSnapshot.exists() && dataSnapshot.getRef().getKey().equals("Doctors")) {
            for (DataSnapshot doctor : dataSnapshot.getChildren()) {
                Doctor d = doctor.getValue(Doctor.class);
                //Check registration status of doctor and add it to list if it matches
                if (d.getRegistrationStatus() == status) {
                    doctors.add(d);
                }
            }
        }
        else {
            throw new IllegalArgumentException("dataSnapshot should not be null and should be from the Doctor path");
        }
        return doctors;

    }

    /*
    Displays all information of this doctor
     */
    @Override
    public String display() {
        String display = super.display() + "\nEmployee Number: " + this.employee_number;
        if (specialties != null && !specialties.isEmpty()) {
            for (int i = 0; i < specialties.size(); i++) {
                display += specialties.get(i);
            }
        }
        return display;

    }


}
