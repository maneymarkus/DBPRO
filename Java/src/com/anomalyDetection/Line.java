package com.anomalyDetection;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Line {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String dateTime;
    private int utcTimestamp;
    private int hour;
    private int weekHour;
    private int month;
    private int day;
    private int dayOfWeek; //0-6; 0 = Sunday
    private int season; //1-4; 1=spring,2=summer,3=autumn,4=winter
    private String geom; //GeoCoordinates Coded in WKB
    private int osmId; //segment identificator in Open Street Maps
    private String roadName;
    private int laneQuantity;
    private int lclDirection; // is the number of directions (either positive or negative) in the specific segment, it does not affect in this case as the directions are in different segments separated by something physical, like a fence
    private int numSensors;
    private int roadType; //1-3; is the type of road, in case of 1 is an Autobahn (A), 2 a Bundesstraße (B) and 3 Landstraße (L)
    private int clcCode; //can be ignored, it is the land use code from corine system
    private float avgVs; //average speed per hour
    private int vehicleCount; //number of vehicles per segment per hour, this column has some NA values, because the accidents happened where there is no sensor on the roads.
    private float stddevAvgVs; //Standard deviation of the speed within 1 hours of measurements in the sensors.
    private String eventType; //type of wild animals accident, only deer accidents
    private int lc; //1-4; light conditions, ###!!! It should be 1=Sunrise,2=Daylight,3=Sunset,4=Night
    private String dayDate; //date without hour
    private Date date;
    private int year; //2017 || 2018
    private float deerAccidentProb; //probability of deer accident
    private float pmix; //gaussian mixture probability of deer accident

    public float getPmix() {
        return pmix;
    }

    public float getDeerAccidentProb() {
        return deerAccidentProb;
    }

    public int getYear() {
        return year;
    }

    public String getDayDate() {
        return dayDate;
    }

    public Date getDate() {
        return date;
    }

    public int getLc() {
        return lc;
    }

    public String getEventType() {
        return eventType;
    }

    public float getStddevAvgVs() {
        return stddevAvgVs;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public float getAvgVs() {
        return avgVs;
    }

    public int getClcCode() {
        return clcCode;
    }

    public int getRoadType() {
        return roadType;
    }

    public int getNumSensors() {
        return numSensors;
    }

    public int getLclDirection() {
        return lclDirection;
    }

    public int getLaneQuantity() {
        return laneQuantity;
    }

    public String getRoadName() {
        return roadName;
    }

    public int getOsmId() {
        return osmId;
    }

    public String getGeom() {
        return geom;
    }

    public int getSeason() {
        return season;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getWeekHour() {
        return weekHour;
    }

    public int getUtcTimestamp() {
        return utcTimestamp;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Line(String line) {
        String[] lineParts;
        if (line.indexOf(",") >= 0) {
            lineParts = line.split(",");
        } else {
            lineParts = line.split(";");
        }
        if (lineParts.length <= 1) {
            return;
        }
        this.dateTime = lineParts[0];
        this.utcTimestamp = Integer.parseInt(lineParts[1]);
        this.hour = Integer.parseInt(lineParts[2]);
        this.month = Integer.parseInt(lineParts[3]);
        this.day = Integer.parseInt(lineParts[4]);
        // map day of week newly, since our day of week starts with Monday and given day of week starts with Sunday
        this.dayOfWeek = (Integer.parseInt(lineParts[5]) + 6) % 7;
        this.weekHour = (this.dayOfWeek - 1) * 24 + this.hour;
        this.season = Integer.parseInt(lineParts[6]);
        this.geom = lineParts[7];
        this.osmId = Integer.parseInt(lineParts[8]);
        this.roadName = lineParts[9];
        this.laneQuantity = Integer.parseInt(lineParts[10]);
        this.lclDirection = Integer.parseInt(lineParts[11]);
        this.numSensors = Integer.parseInt(lineParts[12]);
        this.roadType = Integer.parseInt(lineParts[13]);
        this.clcCode = Integer.parseInt(lineParts[14]);
        if (!lineParts[15].equals("NA")) {
            this.avgVs = Float.parseFloat(lineParts[15]);
        } else {
            this.avgVs = 0;
        }
        if (!lineParts[16].equals("NA")) {
            this.vehicleCount = Integer.parseInt(lineParts[16]);
        } else {
            this.vehicleCount = 0;
        }
        if (!lineParts[17].equals("NA")) {
            this.stddevAvgVs = Float.parseFloat(lineParts[17]);
        } else {
            this.stddevAvgVs = 0;
        }
        this.eventType = lineParts[18];
        this.lc = Integer.parseInt(lineParts[19]);
        this.dayDate = lineParts[20];
        // parse dayDate to usable Date Object
        try {
            this.date = dateFormat.parse(dayDate);
        } catch (Exception e) {
            System.out.println(e);
        }
        this.year = Integer.parseInt(lineParts[21]);
        this.deerAccidentProb = Float.parseFloat(lineParts[22]);
        this.pmix = Float.parseFloat(lineParts[23]);
    }

    public Line(Line a, Line b) {
        //TODO: DateTime and TimeStamp
        this.dateTime = "";
        this.utcTimestamp = 0;
        this.hour = (a.getHour() + b.getHour()) / 2;
        this.month = a.getMonth();
        this.day = a.getDay();
        this.dayOfWeek = a.getDayOfWeek();
        this.weekHour = (a.getWeekHour() + b.getWeekHour()) / 2;
        this.season = a.getSeason();
        this.geom = a.getGeom();
        this.osmId = a.getOsmId();
        this.roadName = a.getRoadName();
        this.laneQuantity = a.getLaneQuantity();
        this.lclDirection = a.getLclDirection();
        this.numSensors = a.getNumSensors();
        this.roadType = a.getRoadType();
        this.clcCode = a.getClcCode();
        if (a.getAvgVs() == 0) {
            this.avgVs = b.getAvgVs();
        } else if (b.getAvgVs() == 0) {
            this.avgVs = a.getAvgVs();
        } else {
            this.avgVs = (a.getAvgVs() + b.getAvgVs()) / 2;
        }
        if (a.getVehicleCount() == 0) {
            this.vehicleCount = b.getVehicleCount();
        } else if (b.getVehicleCount() == 0) {
            this.vehicleCount = a.getVehicleCount();
        } else {
            this.vehicleCount = (a.getVehicleCount() + b.getVehicleCount()) / 2;
        }
        if (a.getStddevAvgVs() == 0) {
            this.stddevAvgVs = b.getStddevAvgVs();
        } else if (b.getStddevAvgVs() == 0) {
            this.stddevAvgVs = a.getStddevAvgVs();
        } else {
            this.stddevAvgVs = (a.getStddevAvgVs() + b.getStddevAvgVs()) / 2;
        }
        this.eventType = "";
        this.lc = a.getLc();
        this.dayDate = a.getDayDate();
        this.date = a.getDate();
        this.year = a.getYear();
        this.deerAccidentProb = (a.getDeerAccidentProb() + b.getDeerAccidentProb()) / 2;
        this.pmix = (a.getPmix() + b.getPmix()) / 2;
    }

    public String toCsvString() {
        String line = "";
        line += this.dateTime + "," + this.utcTimestamp + "," + this.hour + "," + this.month + "," + this.day + "," + this.dayOfWeek + "," + this.season + "," + this.geom + "," + this.osmId + "," + this.roadName + "," + this.laneQuantity + "," + this.lclDirection + "," + this.numSensors + "," + this.roadType + "," + this.clcCode + "," + this.avgVs + "," + this.vehicleCount + "," + this.stddevAvgVs + "," + this.eventType + "," + this.lc + "," + this.dayDate + "," + this.year + "," + this.deerAccidentProb + "," + this.pmix;
        return line;
    }

}
