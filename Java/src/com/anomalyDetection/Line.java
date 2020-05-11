package com.anomalyDetection;

public class Line {

    private String dateTime; //TODO: change to date
    private int utcTimestamp;
    private int hour;
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

    public int getUtcTimestamp() {
        return utcTimestamp;
    }

    public String getDateTime() {
        return dateTime;
    }
/*
    //not used right now
    public Line (String dateTime, int utcTimestamp, int hour, int month, int day, int dayOfWeek, int season, String geom, int osmId, String roadName, int laneQuantity, int lclDirection, int numSensors, int roadType, int clcCode, float avgVs, int vehicleCount, float stddevAvgVs, String eventType, int lc, String dayDate, int year, float deerAccidentProb, float pmix) {
        this.dateTime = dateTime;
        this.utcTimestamp = utcTimestamp;
        this.hour = hour;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.season = season;
        this.geom = geom;
        this.osmId = osmId;
        this.roadName = roadName;
        this.laneQuantity = laneQuantity;
        this.lclDirection = lclDirection;
        this.numSensors = numSensors;
        this.roadType = roadType;
        this.clcCode = clcCode;
        this.avgVs = avgVs;
        this.vehicleCount = vehicleCount;
        this.stddevAvgVs = stddevAvgVs;
        this.eventType = eventType;
        this.lc = lc;
        this.dayDate = dayDate;
        this.year = year;
        this.deerAccidentProb = deerAccidentProb;
        this.pmix = pmix;
    }
     */

    public Line(String line) {
        String[] lineParts = line.split(",");
        this.dateTime = lineParts[0];
        this.utcTimestamp = Integer.parseInt(lineParts[1]);
        this.hour = Integer.parseInt(lineParts[2]);
        this.month = Integer.parseInt(lineParts[3]);
        this.day = Integer.parseInt(lineParts[4]);
        this.dayOfWeek = Integer.parseInt(lineParts[5]);
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
        this.year = Integer.parseInt(lineParts[21]);
        this.deerAccidentProb = Float.parseFloat(lineParts[22]);
        this.pmix = Float.parseFloat(lineParts[23]);
    }

}
