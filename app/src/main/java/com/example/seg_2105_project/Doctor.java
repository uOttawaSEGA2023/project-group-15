package com.example.seg_2105_project;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Doctor extends User {
    private int employee_number;
    private ArrayList<String> specialties;
    private ArrayList<Shift> shifts;
    private boolean autoApprove;

    public Doctor() {}
    public Doctor(String firstName, String lastName, String email, String password, long phoneNumber, String address, int employee_number, ArrayList<String> specialties) {
        super(firstName, lastName, email, password, phoneNumber, address);
        this.employee_number = employee_number;
        this.specialties = specialties;
        this.shifts = new ArrayList<>();
        this.autoApprove = false;
    }

    /**GETTERS**/
    public int get_employee_number() { return employee_number; }
    public ArrayList<String> get_specialties() { return specialties; }
    public ArrayList<Shift> getShifts() { return shifts; }
    public boolean getAutoApprove() { return autoApprove; }

    /**SETTERS**/
    public void updateAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
        updateFirebase("Doctors", "autoApprove", autoApprove, this);
    }

    /*
     * Acts as a setter and changes the registration status in Firebase
     */
    public void updateRegistrationStatus(Status registrationStatus) {
        super.updateRegistrationStatus(registrationStatus);
        updateFirebase("Doctors", "registrationStatus", registrationStatus, this);
    }

    /**OTHER METHODS**/

    /*
    * Displays all information of this doctor
     */
    @Override
    public String display() {
        String display = super.display() + "\nEmployee Number: " + get_employee_number();
        if (get_specialties() != null && !get_specialties().isEmpty()) {
            for (int i = 0; i < get_specialties().size(); i++) {
                display += get_specialties().get(i);
            }
        }
        return display;

    }

    /*
    * Adds shift to list
     */
    public void addShift(Shift shift) {
        this.shifts.add(shift);
        updateFirebase("Doctors", "shifts", shifts, this);
    }

    /**CLASS METHODS**/

    /*
    * Reads through the database for doctors and return Doctor object if found
     */
    public static Doctor getDoctor(String email, String password, DataSnapshot dataSnapshot) {
        for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
            Doctor doctor = doctorSnapshot.getValue(Doctor.class);
            if (doctor.getEmail().equals(email) && doctor.getPassword().equals(password)) {
                return doctor;
            }
        }
        return null;
    }

    /*
     * Gives a list of doctors with a certain registration status from firebase
     * @param  status                      Registration status of doctors
     * @param  dataSnapshot                DataSnapshot of doctor information in firebase
     * @return                             An ArrayList of doctors
     * @throws IllegalArgumentException   if dataSnapshot is null or doesn't contain a snapshot for the doctors
     */
    public static ArrayList<User> getDoctors(Status status, DataSnapshot dataSnapshot) {

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


}
