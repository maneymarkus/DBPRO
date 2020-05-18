package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day {

    private final String dayDate;
    private final Date date;
    private final int month;
    private final int day;
    private final int dayOfWeek;
    private final int season;
    // INFO: Be aware of Nullpointers! Data may not be consistent, especially new Data within one Day
    private Line[] hours = new Line[24];

    public Day(Line l) {
        this.setHour(l);
        this.month = l.getMonth();
        this.day = l.getDay();
        this.dayOfWeek = l.getDayOfWeek();
        this.dayDate = l.getDayDate();
        this.date = l.getDate();
        this.season = l.getSeason();
    }

    public String getDayDate() {
        return dayDate;
    }

    public int getSeason() {
        return this.season;
    }

    public Date getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public Line[] getHours() {
        return hours;
    }

    public Line getHour(int h) {
        if (h >= 0 && h <= 23) {
            if (hours[h] != null) {
                return hours[h];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //insert new Hour (/Dataset) in this Day
    public void setHour(Line line) {
        int h = line.getHour();
        if (h >= 0 && h <= 23) {
            this.hours[h] = line;
        }
    }

    //not a normal setter
    public void setHours(List<Line> lines) {
        lines.forEach(l -> setHour(l));
    }


}
