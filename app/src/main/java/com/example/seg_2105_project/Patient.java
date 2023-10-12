package com.example.seg_2105_project;

public class Patient extends User {

    int healthCardNumber;

    public Patient(String firstName, String lastName, String email, String password, int phoneNumber, String address, int healthCardNumber) {
        super(firstName, lastName, email, password, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
    }

}
