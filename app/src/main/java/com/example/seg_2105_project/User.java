package com.example.seg_2105_project;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private String address;

    public User(String firstName, String lastName, String email, String password, int phoneNumber, String address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}

