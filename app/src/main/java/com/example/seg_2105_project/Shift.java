package com.example.seg_2105_project;

import java.util.Calendar;

public class Shift {

    Calendar start;
    Calendar end;

    public Shift(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    public String toString() {
        String date = start.get(Calendar.DAY_OF_MONTH) + "/" + start.get(Calendar.MONTH) + "/" + start.get(Calendar.YEAR);
        String startTime = start.get(Calendar.HOUR) + ":" + start.get(Calendar.MINUTE);
        String endTime = end.get(Calendar.HOUR) + ":" + end.get(Calendar.MINUTE);
        return date + " from " + startTime + " to " + endTime;
    }

}
