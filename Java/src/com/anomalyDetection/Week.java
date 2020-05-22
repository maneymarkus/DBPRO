package com.anomalyDetection;

import java.text.SimpleDateFormat;
import java.util.*;

public class Week {

    private final Date dayDateStart;
    private final Date dayDateEnd;
    private final int calendarWeek;
    private final int season;
    //0 = Monday, 1 = Tuesday,  ...
    // INFO: Be aware of Nullpointers! Data may not be consistent, especially when comparing real time data within one week
    private Day[] days = new Day[7];

    public Week(Day day) {
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
        this.setDay(day);
    }

    //insert new Day in this Week
    public void setDay(Day day) {
        int dayOfWeek = day.getDayOfWeek();
        // map day of week newly, since our day of week starts with Monday and given day of week starts with Sunday
        this.days[((dayOfWeek + 6) % 7)] = day;
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
            return null;
        }
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

}
