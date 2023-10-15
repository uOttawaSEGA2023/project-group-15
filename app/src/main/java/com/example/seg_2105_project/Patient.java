package com.example.seg_2105_project;

public class Patient extends User {

    private int healthCardNumber;

    public Patient(String firstName, String lastName, String email, String password, long phoneNumber, String address, int healthCardNumber) {
        super(firstName, lastName, email, password, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
    }

    public int getHealthCardNumber() {
        return healthCardNumber;
    }
}
