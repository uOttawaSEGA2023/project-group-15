package com.example.seg_2105_project;

public class Doctor extends User {
    long employee_number;
    String[] specialties;

    public Doctor(String firstName, String lastName, String email, String password, int phoneNumber, String address, long employee_number, String[] specialties) {
        super(firstName, lastName, email, password, phoneNumber, address);
        this.employee_number = employee_number;
        this.specialties = specialties;
    }

    public long get_employee_number() {
        return employee_number;

    }

    public String[] get_specialties() {
        return specialties;
    }


}
