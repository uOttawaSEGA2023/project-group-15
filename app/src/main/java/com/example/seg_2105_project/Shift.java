package com.example.seg_2105_project;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

public class Shift implements Serializable {

    private Calendar start;
    private Calendar end;
    private int year;
    private int month;
    private int day;
    private int startHours;
    private int startMinutes;
    private int endHours;
    private int endMinutes;

    // Nested class for comparing shifts
    private static class ShiftComparator<T> implements Comparator<T> {
        @Override
        public int compare(T s1, T s2) {
            Shift shift1 = (Shift) s1;
            Shift shift2 = (Shift) s2;
            Calendar shift1Start = shift1.retrieveStart();
            Calendar shift2Start = shift2.retrieveStart();
            return shift1Start.compareTo(shift2Start);
        }
    }

    public Shift(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
        this.year = start.get(Calendar.YEAR);
        this.month = start.get(Calendar.MONTH);
        this.day = start.get(Calendar.DAY_OF_MONTH);
        this.startHours = start.get(Calendar.HOUR_OF_DAY);
        this.startMinutes = start.get(Calendar.MINUTE);
        this.endHours = end.get(Calendar.HOUR_OF_DAY);
        this.endMinutes = end.get(Calendar.MINUTE);
    }

    public Shift() {}

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getStartHours() {
        return startHours;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public int getEndHours() {
        return endHours;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    // Static methods
    // return a comparator for shifts
    public static ShiftComparator getShiftComparator(){ return new ShiftComparator(); }

    public Calendar retrieveStart() {
        start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, startHours);
        start.set(Calendar.MINUTE, startMinutes);
        start.set(Calendar.YEAR, year);
        start.set(Calendar.MONTH, month);
        start.set(Calendar.DAY_OF_MONTH, day);
        return start;
    }

    public Calendar retrieveEnd() {
        end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, endHours);
        end.set(Calendar.MINUTE, endMinutes);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.MONTH, month);
        end.set(Calendar.DAY_OF_MONTH, day);
        return end;
    }

    public String toString() {
        String date = day + "/" + (month + 1) + "/" + year;
        String startTime = "";
        String endTime = "";

        if (startHours < 10)
            startTime += "0";
        startTime += startHours + ":";

        if (startMinutes < 10)
            startTime += "0";
        startTime += startMinutes;

        if (endHours < 10)
            endTime += "0";
        endTime += endHours + ":";

        if (endMinutes < 10)
            endTime += "0";
        endTime += endMinutes;

        return date + " from " + startTime + " to " + endTime;
    }

}
