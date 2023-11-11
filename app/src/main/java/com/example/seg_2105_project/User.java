package com.example.seg_2105_project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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

    /**GETTERS**/

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

    public ArrayList<Appointment>  getAppointments() {
        return appointments;
    }


    /**SETTERS**/

    /*
     * Acts as a setter for registration status
     */
    public void updateRegistrationStatus(Status registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    /**OTHER METHODS**/

    /*
     * Adds appointment to list
     */
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
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

    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    /**CLASS METHODS**/

    /*
     * Updates a certain attribute in Firebase
     * @param  referencePath  "Patients" or "Doctors"
     * @param  attributePath  Name of the attribute to be updated
     * @param  attribute      Object of type attribute to overwrite current database data
     * @param  user           User to have attribute updated
     * @throws IllegalArgumentException   if arguments don't fit constraints specified
     */
    protected static void updateFirebase(String referencePath, String attributePath, Object attribute, User user) {

        //Argument validation
        validateArguments(referencePath, attributePath, attribute, user);

        //Get firebase reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(referencePath);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Search through patients
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User u = userSnapshot.getValue(user.getClass());
                    if (u.getEmail().equals(user.getEmail())) {
                        //Change status
                        DatabaseReference reference = userSnapshot.getRef();
                        reference.child(attributePath).setValue(attribute);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    /*
    * Used to validate arguments of updateFirebase
     */
    private static void validateArguments(String referencePath, String attributePath, Object attribute, User user) {
        if (!(referencePath.equals("Patients") || referencePath.equals("Doctors")) ||
                !((attributePath.equals("firstName") && attribute instanceof String) ||
                        (attributePath.equals("lastName") && attribute instanceof String) ||
                        (attributePath.equals("email") && attribute instanceof String) ||
                        (attributePath.equals("password") && attribute instanceof String) ||
                        (attributePath.equals("phoneNumber") && attribute instanceof Long) ||
                        (attributePath.equals("address") && attribute instanceof String) ||
                        (attributePath.equals("registrationStatus") && attribute instanceof Status) ||
                        (attributePath.equals("appointments") && attribute instanceof Appointment) ||
                        (attributePath.equals("employee_number") && referencePath.equals("Doctors") && attribute instanceof Long) ||
                        (attributePath.equals("specialties") && referencePath.equals("Doctors") && attribute instanceof ArrayList) ||
                        (attributePath.equals("shifts") && referencePath.equals("Doctors") && attribute instanceof ArrayList) ||
                        (attributePath.equals("autoApprove") && referencePath.equals("Doctors") && attribute instanceof Boolean) ||
                        (attributePath.equals("healthCardNumber") && referencePath.equals("Patients") && attribute instanceof Long))) {
            throw new IllegalArgumentException();
        }
    }

}

