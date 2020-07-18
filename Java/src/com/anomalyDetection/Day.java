package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Arrays;
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

    //TODO: maybe smooth bigger "holes" in data
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
    
    public int getVehicleCount(int h) {
    	return this.hours[h].getVehicleCount();
    }
    public int getTotalVehicleCount() {
    	int cnt = 0;
    	for(int i=0;i<24;i++) {
    		cnt+=this.hours[i].getVehicleCount();
    	}
    	return cnt;
    }
    
    public int getHofMaxVehicleCount() {
    	int h = 0;
    	int cnt=0;
    	for(int i=0;i<24;i++) {
    		if(this.hours[i].getVehicleCount()>cnt) {
    			h=i;
    			cnt=this.hours[i].getVehicleCount();
    		}
    	}
    	return h;
    }
    
    public int getMaxVehicleCount() {
    	int h = 0;
    	int cnt=0;
    	for(int i=0;i<24;i++) {
    		if(this.hours[i].getVehicleCount()>cnt) {
    			h=i;
    			cnt=this.hours[i].getVehicleCount();
    		}
    	}
    	return cnt;
    }
    
    public int getMinVehicleCount() {
    	int h = 0;
    	int cnt=0;
    	for(int i=0;i<24;i++) {
    		if(cnt==0||this.hours[i].getVehicleCount()<cnt) {
    			h=i;
    			cnt=this.hours[i].getVehicleCount();
    		}
    	}
    	return cnt;
    }
    public void setHofMinVehicleCount() {
    	int h = 0;
    	int cnt=0;
    	for(int i=0;i<24;i++) {
    		if(cnt==0||this.hours[i].getVehicleCount()<cnt) {
    			h=i;
    			cnt=this.hours[i].getVehicleCount();
    		}
    	}
    	this.hours[h].setEvent("lowVehicleCount");
    }
    public void setHofMaxVehicleCount() {
    	int h = 0;
    	int cnt=0;
    	for(int i=0;i<24;i++) {
    		if(this.hours[i].getVehicleCount()>cnt) {
    			h=i;
    			cnt=this.hours[i].getVehicleCount();
    		}
    	}
    	this.hours[h].setEvent("highVehicleCount");
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
