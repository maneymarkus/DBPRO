package com.anomalyDetection;

import java.text.SimpleDateFormat;
import java.util.*;

public class Week {

    private final int geoId;
    private final Date dayDateStart;
    private final Date dayDateEnd;
    private final int calendarWeek;
    private final int season;
    //0 = Monday, 1 = Tuesday,  ...
    // INFO: Be aware of Nullpointers! Data may not be consistent, especially when comparing real time data within one week
    private Day[] days = new Day[7];

    public Week(Day day, int geoId) {
        Date date = day.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // sets Calender to Sunday of the week of the given date
        calendar.set(calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        // sets Calender to Monday == start of week
        calendar.roll(calendar.DAY_OF_WEEK, -6);
        this.dayDateStart = calendar.getTime();
        calendar.roll(calendar.DAY_OF_WEEK, 6);
        this.dayDateEnd = calendar.getTime();
        this.calendarWeek = calendar.WEEK_OF_YEAR;
        this.season = day.getSeason();
        this.geoId = geoId;
        this.setDay(day);
    }

    //insert new Day in this Week
    public void setDay(Day day) {
        int dayOfWeek = day.getDayOfWeek();
        this.days[dayOfWeek] = day;
    }

    //get day of week based on given index: 0 returns Monday, 1 returns Tuesday, etc.
    public Day getDayOfWeek(int index) {
        if (index >= 0 && index <= 6) {
            if (days[index] != null) {
                return days[index];
            } else {
                return null;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     *  Returns an hour of the week. The index has to be between 0 and 168 (hours in one week). This method is used for realtime processing.
     *
     * @param   index   specifies the wanted hour
     * @return          the wanted hour specified by the index
     */
    public Line getHourOfWeek(int index) {
        if (index < 0 || index > 167) {
            System.out.println("The given index has to be between 0 and 167. (Since a week contains 168 hours)");
            return null;
        }
        //determine the day
        int dayIndex = index / 24;
        //determine the hour of this day
        int hourIndex = index % 24;
        return this.getDayOfWeek(dayIndex).getHour(hourIndex);
    }

    public Date getDayDateStart() {
        return dayDateStart;
    }

    public Date getDayDateEnd() {
        return dayDateEnd;
    }

    public int getCalendarWeek() {
        return calendarWeek;
    }

    public int getSeason() {
        return season;
    }

    public Day[] getDays() {
        return days;
    }

    public int getGeoId() {
        return geoId;
    }

    public boolean hasAccident() {
        boolean accident = false;
        for (Day d : days) {
            if (d != null) {
                if (d.hasAccident()) {
                    accident = true;
                }
            }
        }
        return accident;
    }

    public String toCsvString() {
        String weekString = "";
        for (Day d : days) {
            if (d != null) {
                weekString += d.toCsvString();
            } else {
                weekString += "\n";
            }
        }
        return weekString;
    }
}
