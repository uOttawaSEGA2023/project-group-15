package com.example.seg_2105_project;

import java.io.Serializable;
import java.util.Calendar;

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

    public Shift(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
        this.year = start.get(Calendar.YEAR);
        this.month = start.get(Calendar.MONTH);
        this.day = start.get(Calendar.DAY_OF_MONTH);
        this.startHours = start.get(Calendar.HOUR);
        this.startMinutes = start.get(Calendar.MINUTE);
        this.endHours = end.get(Calendar.HOUR);
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
        end.set(Calendar.YEAR, year);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.HOUR_OF_DAY, endHours);
        end.set(Calendar.MINUTE, endMinutes);
        return end;
    }

    public String toString() {
        String date = day + "/" + month + "/" + year;
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
