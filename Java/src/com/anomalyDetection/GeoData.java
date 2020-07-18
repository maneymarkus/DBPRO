package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeoData {

	// geographical segment based on osm_id
	private final int geoId;
	private ArrayList<Day> days = new ArrayList<>();
	private ArrayList<Week> weeks = new ArrayList<>();
	private GoldenBatch goldenBatch;
	public float thresh = (float) 0.000;

	public GeoData(Line dataset) {
		this.geoId = dataset.getOsmId();
		this.addData(dataset);
	}

	// insert new read line out of .csv in Object Data Structure -> line == hour ->
	// when day of this specific hour has already been created -> add -> otherwise
	// check if week of this hour exist -> if yes, add -> if not, create new Week
	// and new day and add given hour
	public void addData(Line l) {
		Date date = l.getDate();
		// Does the day of the given hour exist?
		if (this.days.stream().anyMatch(d -> d.getDate().compareTo(date) == 0)) {
			// If yes, then add given hour to this day
			this.days.stream().filter(d -> d.getDate().compareTo(date) == 0).limit(1).forEach(d -> d.setHour(l));
		} else {
			// If not, then create a new day with this given hour
			Day newDay = new Day(l);
			this.days.add(newDay);
			// Does the week of the given hour exist?
			if (this.weeks.stream().anyMatch(
					w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0)) {
				// If yes, then add the new day with the given hour to this week
				this.weeks.stream()
						.filter(w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0)
						.limit(1).forEach(w -> w.setDay(newDay));
			} else {
				// If not, then create a new week and add the new day with the given hour
				this.weeks.add(new Week(newDay, this.geoId));
			}
		}
	}

	// insert new read line out of .csv in Object Data Structure -> line == hour ->
	// when day of this specific hour has already been created -> add -> otherwise
	// check if week of this hour exist -> if yes, add -> if not, create new Week
	// and new day and add given hour
	public void addData(Line l, boolean learning) {
		Date date = l.getDate();
		// Does the day of the given hour exist?
		if (this.days.stream().anyMatch(d -> d.getDate().compareTo(date) == 0)) {
			// If yes, then add given hour to this day
			this.days.stream().filter(d -> d.getDate().compareTo(date) == 0).limit(1).forEach(d -> d.setHour(l));
		} else {
			// If not, then create a new day with this given hour
			Day newDay = new Day(l);
			this.days.add(newDay);
			// Does the week of the given hour exist?
			if (this.weeks.stream().anyMatch(
					w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0)) {
				// If yes, then add the new day with the given hour to this week
				this.weeks.stream()
						.filter(w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0)
						.limit(1).forEach(w -> w.setDay(newDay));
			} else {
				// If not, then create a new week and add the new day with the given hour
				this.weeks.add(new Week(newDay, this.geoId));
			}
		}
		if (!learning && this.goldenBatch != null) {
			this.goldenBatch.realtimeComparison(l);
		}
	}

	public float findAVGThreshold() {

		Day errorDay = null;
		int errH = -1;
		float AVGthr = (float) 0.00;
		float ACCthr = (float) 0.00;
		this.findGoldenBatch();
		int count = 0;

		// find Accident Day and hour

		// MORE DATA

		List<Day> vChigh = this.getDays();
		vChigh.sort(Comparator.comparing(o -> o.getMaxVehicleCount()));
		vChigh = vChigh.stream().limit(73).collect(Collectors.toList());
		vChigh.forEach(Day::setHofMaxVehicleCount);

		List<Day> vClow = this.getDays();
		vClow.sort(Comparator.comparing(o -> o.getMinVehicleCount()));
		vClow = vClow.stream().limit(73).collect(Collectors.toList());
		vClow.forEach(Day::setHofMinVehicleCount);

		List<Day> errors = this.getDays().stream().filter(s -> s.hasAccident() && !s.equals(null))
				.collect(Collectors.toList());

		if (!errors.isEmpty()) {
			while (!errors.isEmpty()) {
				Day errorDayTmp = errors.get(0);
				errors.remove(0);
				int errHtmp = errorDayTmp.getHOfAccident();

				// euk
				float ACCtmp = DistanceUtility.getEuklideanDistanceToDayAtH(
						(Day) this.getGoldenBatch().getGoldenDay(errorDayTmp.getDayOfWeek()), errorDayTmp, errHtmp);

				ACCthr += ACCtmp;
				errorDay = errorDayTmp;
				errH = errHtmp;
				count += 1;
				AVGthr += getAVGDistance(errorDay.getDayOfWeek(), errH);

				// }
			}

		} else
			System.out.println("NO ACC DATA FOUND AT " + this.geoId);

		if (errH != -1 && errorDay != null) {

			thresh = (float) ((ACCthr / count) / (AVGthr / count));
			return (float) ((ACCthr / count) / (AVGthr / count));
			// return (float)
		} else
			return (float) 0.00000;
	}

	
	
	
	
	public float findAVGThresholdDTW() {

		Day errorDay = null;
		int errH = -1;
		float AVGthr = (float) 0.00;
		float ACCthr = (float) 0.00;
		this.findGoldenBatch();
		int count = 0;

		// find Accident Day and hour

		// MORE DATA

		List<Day> vChigh = this.getDays();
		vChigh.sort(Comparator.comparing(o -> o.getMaxVehicleCount()));
		vChigh = vChigh.stream().limit(5).collect(Collectors.toList());
		vChigh.forEach(Day::setHofMaxVehicleCount);

		List<Day> vClow = this.getDays();
		vClow.sort(Comparator.comparing(o -> o.getMinVehicleCount()));
		vClow = vClow.stream().limit(5).collect(Collectors.toList());
		vClow.forEach(Day::setHofMinVehicleCount);

		List<Day> errors = this.getDays().stream().filter(s -> s.hasAccident() && !s.equals(null))
				.collect(Collectors.toList());

		if (!errors.isEmpty()) {
			while (!errors.isEmpty()) {
				Day errorDayTmp = errors.get(0);
				errors.remove(0);
				int errHtmp = errorDayTmp.getHOfAccident();

				// DTW
				float ACCtmp = DistanceUtility.getDTWDistance(
						DistanceUtility.createCostMatrix(
								(Day) this.getGoldenBatch().getGoldenDay(errorDayTmp.getDayOfWeek()), errorDayTmp),
						errHtmp, errHtmp);

				ACCthr += ACCtmp;
				errorDay = errorDayTmp;
				errH = errHtmp;
				count += 1;
				AVGthr += getAVGDistanceDTW(errorDay.getDayOfWeek(), errH);

			}

		} else
			System.out.println("NO ACC DATA FOUND AT " + this.geoId);

		if (errH != -1 && errorDay != null) {

			thresh = (float) ((ACCthr / count) / (AVGthr / count));
			return (float) ((ACCthr / count) / (AVGthr / count));

		} else
			return (float) 0.00000;
	}

	public float getAVGDistance(int dayOfWeek, int h) { // to Golden Batch at H and Day
		return this.getGoldenBatch().determineEUKThresholdpH(this.getDays(), dayOfWeek, h);

	}

	public float getAVGDistanceDTW(int dayOfWeek, int h) { // to Golden Batch at H and Day
		return this.getGoldenBatch().determineThresholdpH(this.getDays(), dayOfWeek, h);

	}

	public List<Day> findAnomaliesByThr(float thr) {
		ArrayList<Day> anomalyDays = new ArrayList<>();
		Boolean ano;
		for (Day d : this.days) {
			ano = false;
			for (int h = 0; h < 24; h++) {

				// EUK
				float ACCtmp = DistanceUtility
						.getEuklideanDistanceToDayAtH((Day) this.getGoldenBatch().getGoldenDay(d.getDayOfWeek()), d, h);
				if (ACCtmp >= getAVGDistance(d.getDayOfWeek(), h) * 1 * thr && !ano) {

					ano = true;
				}
			}
			if (ano)
				anomalyDays.add(d);
		}
		return anomalyDays;
	}

	public List<Day> findAnomaliesByThrDTW(float thr) {
		ArrayList<Day> anomalyDays = new ArrayList<>();
		Boolean ano;
		for (Day d : this.days) {
			ano = false;
			for (int h = 0; h < 24; h++) {
				// DTW
				float ACCtmp = DistanceUtility.getDTWDistance(
						DistanceUtility.createCostMatrix((Day) this.getGoldenBatch().getGoldenDay(d.getDayOfWeek()), d),
						h, h);

				if (ACCtmp >= getAVGDistanceDTW(d.getDayOfWeek(), h) * 1 * thr && !ano) {
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

	public List<Day> determineAnomalies() {
		return this.goldenBatch.determineAnomalies(this.weeks);
	}

	public String toCsvString() {
		String weeksString = "";
		for (Week w : this.weeks) {
			if (w != null) {
				weeksString += w.toCsvString();
			} else {
				weeksString += "\n";
			}
		}
		return weeksString;
	}

}
