package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeoData {

    //geographical segment based on osm_id
    private final int geoId;
    private ArrayList<Day> days = new ArrayList<>();
    private ArrayList<Week> weeks = new ArrayList<>();
    private ArrayList<Day> anomalies = new ArrayList<>();
    private GoldenBatch goldenBatch;

    public GeoData(Line dataset) {
        this.geoId = dataset.getOsmId();
        this.addData(dataset, true);
    }

    //insert new read line out of .csv in Object Data Structure -> line == hour -> when day of this specific hour has already been created -> add -> otherwise check if week of this hour exist -> if yes, add -> if not, create new Week and new day and add given hour
    public void addData(Line l, boolean learning) {
        Date date = l.getDate();
        //Does the day of the given hour exist?
        if (this.days.stream().anyMatch(d -> d.getDate().compareTo(date) == 0)) {
            //If yes, then add given hour to this day
            this.days.stream().filter(d -> d.getDate().compareTo(date) == 0).limit(1).forEach(d -> d.setHour(l));
        } else {
            //If not, then create a new day with this given hour
            Day newDay = new Day(l);
            this.days.add(newDay);
            //Does the week of the given hour exist?
            if (this.weeks.stream().anyMatch(w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0)) {
                //If yes, then add the new day with the given hour to this week
                this.weeks.stream().filter(w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0).limit(1).forEach(w -> w.setDay(newDay));
            } else {
                //If not, then create a new week and add the new day with the given hour
                this.weeks.add(new Week(newDay, this.geoId));
            }
        }
        if (!learning && this.goldenBatch != null) {
            this.goldenBatch.realtimeComparison(l);
        }
    }
    

    
	public float findMinThreshold() {

		// ArrayList<Day> anomalyDays = new ArrayList<>();
		Day errorDay = null;
		int errH = -1;
		float AVGthr = (float) 0.00;
		float ACCthr = (float) 0.00;
		this.findGoldenBatch();
		// find Accident Day and hour
		List<Day> errors = this.getDays().stream().filter(s -> s.hasAccident() && !s.equals(null)).collect(Collectors.toList());

		if (!errors.isEmpty()) {
			while (!errors.isEmpty()) {
				Day errorDayTmp = errors.get(0);
				errors.remove(0);
				int errHtmp = errorDayTmp.getHOfAccident();

				float ACCtmp = DistanceUtility.getDTWDistance(
						DistanceUtility.createCostMatrix(
								(Day) this.getGoldenBatch().getGoldenDay(errorDayTmp.getDayOfWeek()), errorDayTmp),
						errHtmp, errHtmp);

				if (ACCthr == (float) 0.00 || ACCthr >= ACCtmp) {
					ACCthr = ACCtmp;
					errorDay = errorDayTmp;
					errH = errHtmp;
				}
			}
        } else {
            System.out.println("NO ACC DATA FOUND AT " + this.geoId);
        }
        if (errH != -1 && errorDay != null) {
            AVGthr = getAVGDistance(errorDay.getDayOfWeek(), errH);// this.getGoldenBatch().determineThresholdpH(this.getDays(),
            // errorDay.getDayOfWeek(),errH);

            //System.out.println("AVGThreshold at hour " +errH +": "+ AVGthr +"");
            //System.out.println("Distance between GB and ACC in hour X: "+ ACCthr);
            return (float) ACCthr / AVGthr;
        }

        return (float) 0.00000;
        // find Anomalies for real

    }

    public float getAVGDistance(int dayOfWeek, int h) { // to Golden Batch at H and Day
        return this.getGoldenBatch().determineThresholdpH(this.getDays(), dayOfWeek, h);
    }

    public List<Day> findAnomaliesByThr(float thr) {
        ArrayList<Day> anomalyDays = new ArrayList<>();
        Boolean ano;
        for (Day d : this.days) {
            ano = false;
            for (int h = 0; h < 24; h++) {
                float ACCtmp = DistanceUtility.getDTWDistance(DistanceUtility
                        .createCostMatrix((Day) this.getGoldenBatch().getGoldenDay(d.getDayOfWeek()), d), h, h);
                if (ACCtmp >= getAVGDistance(d.getDayOfWeek(), h) * 1 * thr && !ano) { //
                    // System.out.println(thr +" * "+ getAVGDistance(d.getDayOfWeek(),h) +" "+
                    // ACCtmp );
                    ano = true;
                }
            }
            if (ano)
                anomalyDays.add(d);
        }
        return anomalyDays;
    }

    public int getGeoId() {
        return geoId;
    }

    public ArrayList<Day> getDays() {
        return this.days;
    }

    public ArrayList<Week> getWeeks() {
        return this.weeks;
    }

    public GoldenBatch getGoldenBatch() {
        return this.goldenBatch;
    }

    public void setGoldenBatch(GoldenBatch gB) {
        this.goldenBatch = gB;
    }

    public void findGoldenBatch() {
        this.goldenBatch = GoldenBatch.findOptimalWeek(this.weeks);
    }

    public boolean hasAccident() {
        boolean accident = false;
        for (Week w : weeks) {
            if (w != null) {
                if (w.hasAccident()) {
                    accident = true;
                }
            }
        }
        return accident;
    }

    /**
     *  Probably deprecated. Analyses the whole dataset of this segment and tries to find anomalies within this dataset
     * @return  List of Days which this functions determines to be Anomalies
     */
    public List<Day> determineAnomalies() {
        return this.goldenBatch.determineAnomalies(this.weeks);
    }

    public String toCsvString() {
        String weeksString = "";
        for (Week w : this.weeks) {
            if(w != null) {
                weeksString += w.toCsvString();
            } else {
                weeksString += "\n";
            }
        }
        return weeksString;
    }


}
