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

    public Data() {
        /*
         * // this.lines = new ArrayList<>();
         */
        this.segments = new ArrayList<>();
    }

    //geographical segmentation based on osm_id
    public void geoSplitData(Line line) {
        int geoId = line.getOsmId();
        //Check if geographical segment exists
        if (this.segments.stream().anyMatch(s -> s.getGeoId() == geoId)) {
            this.segments.stream().filter(s -> s.getGeoId() == geoId).forEach(s -> s.addData(line));
        } else {
            //otherwise create it
            this.segments.add(new GeoData(line));
        }
    }

    public ArrayList<GeoData> getSegments() {
        return segments;
    }

    /*
     * // public ArrayList<Line> getLines() {
     * //     return this.lines;
     * // }
     */

    //this method gets read lines (Hour-Datasets) from the Reader.java and handles them over to geoSplitData method
    public void addLine(Line line) {
        /*
         * // if (!this.lines.contains(line)) {
         * //   this.lines.add(line);
         * // }
         */
        geoSplitData(line);
    }

    public void addLines(List<Line> lines) {
        lines.forEach(l -> this.addLine(l));
    }

}
