package com.example.seg_2105_project;

public class Time {

    private int minutes;
    private int hours;

    public Time(int minutes, int hours) {
        this.minutes = minutes;
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public String toString() {
        return hours + ":" + minutes;
    }

}
