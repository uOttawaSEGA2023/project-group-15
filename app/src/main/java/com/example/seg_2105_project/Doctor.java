package com.example.seg_2105_project;

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

    public long get_employee_number() {
        return employee_number;

    }

    public ArrayList<String> get_specialties() {
        return specialties;
    }


}
