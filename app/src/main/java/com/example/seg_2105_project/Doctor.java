package com.example.seg_2105_project;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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


    /*
    *Deletes an existing shift from list
     */
    public void deleteShift(Shift shift){
        this.shifts.remove(shift);
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

    /*
     * Gives a list of appointments that are either past or upcoming
     * @param  dataSnapshot                DataSnapshot of doctor information in firebase
     * @param  passed                      boolean to indicate if it is a passed appointment or not
     * @param  currentDate                 Current Date
     * @return                             An ArrayList of appointments
     * @throws IllegalArgumentException   if dataSnapshot is null or doesn't contain a snapshot for the doctors
     */
    public static ArrayList<Appointment> getDoctorAppointments(DataSnapshot dataSnapshot, boolean passed, Calendar currentDate) {
        ArrayList<Appointment> specificAppointments = new ArrayList<>();

        // Make sure dataSnapshot isn't null and contains the Doctor path
        if (dataSnapshot.exists() && dataSnapshot.getRef().getKey().equals("Doctors")) {
            for (DataSnapshot doctor : dataSnapshot.getChildren()) {
                User d = doctor.getValue(Doctor.class);

                // Check if d is not null and has appointments
                if (d != null && d.getAppointments() != null) {
                    ArrayList<Appointment> doctorAppointments = d.getAppointments();

                    for (Appointment appointment : doctorAppointments) {
                        // add upcoming appointments
                        if (!passed) {
                            // make sure appointment status is either pending or approved and the appointments time is greater than current time
                            if ((appointment.getStatus() == Status.PENDING || appointment.getStatus() == Status.APPROVED) && currentDate.before(appointment.getDateTime())) {
                                specificAppointments.add(appointment);
                            }
                        }
                        // add past appointments
                        else {
                            // make sure appointment status is approved and the appointment time is less than current time
                            if (appointment.getStatus() == Status.APPROVED && currentDate.after(appointment.getDateTime())) {
                                specificAppointments.add(appointment);
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("dataSnapshot should not be null and should be from the Doctor path");
        }

        return specificAppointments;
    }


}
