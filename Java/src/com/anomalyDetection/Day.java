package com.anomalyDetection;

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
    //keep track of the zero values of the read lines in the dataset
    private int zeroValues = 0;
    //if the day has to many zero values we can't use it anymore
    private boolean hasTooManyZeros = false;

    public Day(Line l) {
        this.setHour(l);
        this.month = l.getMonth();
        this.day = l.getDay();
        this.dayOfWeek = l.getDayOfWeek();
        this.dayDate = l.getDayDate();
        this.date = l.getDate();
        this.season = l.getSeason();
    }

    private Line smoothDay(int hIndex) {
        for (int i = 0; i < hours.length; i++) {
            if (hours[i] == null) {
                if (i == 0 || i == 23) {
                    continue;
                }
                if (hours[i - 1] != null && hours[i + 1] != null) {
                    this.setHour(new Line(hours[i - 1], hours[i + 1]));
                }
            }
        }
        if (this.hours[hIndex] != null) {
            return this.hours[hIndex];
        } else {
            return null;
        }
    }
    
    //Getter and setter
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

    public boolean getHasTooManyZeros() {
        return this.hasTooManyZeros;
    }

    // returns the wanted hour of this day based on the given index
    public Line getHour(int h) {
        if (h >= 0 && h <= 23) {
            if (hours[h] != null) {
                return hours[h];
            } else {
                return this.smoothDay(h);
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    //insert new Hour (/Dataset) in this Day
    public void setHour(Line line) {
        int h = line.getHour();
        if (h >= 0 && h <= 23) {
            this.hours[h] = line;
        }
        if (line.getAvgVs() == 0) {
            this.zeroValues++;
        }
        if (this.zeroValues >= 5) {
            this.hasTooManyZeros = true;
        }
    }

    //not a normal setter
    public void setHours(List<Line> lines) {
        lines.forEach(l -> setHour(l));
    }

    public boolean hasAccident() {
        boolean accident = false;
        for (int j=0;j<24;j++) {
            if (hours[j] != null) {
                if (!hours[j].getEventType().equals("")) {
                	//System.out.println("FEHLER IN STUNDE: " +j);
                    accident = true;
                }
            }
        }
        return accident;
    }
    
  
    
    public int getHOfAccident() {
        
        for (int j=0;j<24;j++) {
            if (hours[j] != null) {
                if (!hours[j].getEventType().equals("")) {
                	//System.out.println("FEHLER IN STUNDE: " +j);
                    return j;
                }
            }
        }return -1;
        
    }

    public String toCsvString() {
        String dayString = "";
        for (Line h : hours) {
            if (h != null) {
                dayString += h.toCsvString();
            }
            dayString += "\n";
        }
        return dayString;
    }
}
