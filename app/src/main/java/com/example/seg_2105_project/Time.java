package com.example.seg_2105_project;

public class Time implements Comparable {

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

    /*
    * Returns a negative number if the other time is before this time, a positive number
    * if the other time is after this time, and 0 if they are the same
     */
    @Override
    public int compareTo(Object o) {

        Time other = (Time) o;
        if (other.getHours() < hours)
            return -1;
        else if (other.getHours() > hours)
            return 1;
        else {
            if (other.getMinutes() < minutes)
                return -1;
            else if (other.getMinutes() > minutes)
                return 1;
            else
                return 0;
        }

    }
}
