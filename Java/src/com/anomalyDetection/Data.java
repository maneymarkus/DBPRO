package com.anomalyDetection;

import java.util.ArrayList;
import java.util.List;

public class Data {

    /*
     *  Unordered list of read lines from the .csv file. Activate if needed
     *  // private ArrayList<Line> lines;
     */

    //geographical segments based on osm_id
    private ArrayList<GeoData> segments;
    //variable which keeps track of the state of the application: if learning is true this means the application still collects data. If learning is false the realtime comparison can be started
    private boolean learning = true;

    public Data() {
        /*
         * // this.lines = new ArrayList<>();
         */
        this.segments = new ArrayList<>();
    }

    /**
     * geographical segmentation based on osm_id
     * @param line  the read line which needs to be distributed
     */
    public void geoSplitData(Line line) {
        int geoId = line.getOsmId();
        //Check if geographical segment exists
        if (this.segments.stream().anyMatch(s -> s.getGeoId() == geoId)) {
            this.segments.stream().filter(s -> s.getGeoId() == geoId).forEach(s -> s.addData(line, this.learning));
        } else {
            //otherwise create it
            this.segments.add(new GeoData(line));
        }
    }

    public void setGoldenBatches() {
        this.segments.stream().forEach(GeoData::findGoldenBatch);
        this.learning = false;
    }

    public ArrayList<GeoData> getSegments() {
        return segments;
    }

    /*
     * // public ArrayList<Line> getLines() {
     * //     return this.lines;
     * // }
     */

    /**
     * this method gets read lines (Hour-Datasets) from the Reader.java and handles them over to geoSplitData method
     * @param line  the read line
     */
    public void addLine(String line) {
        /*
         * // if (!this.lines.contains(line)) {
         * //   this.lines.add(line);
         * // }
         */
        Line l = new Line(line);
        geoSplitData(l);
    }

    public void addLines(List<String> lines) {
        lines.forEach(l -> this.addLine(l));
    }

}
