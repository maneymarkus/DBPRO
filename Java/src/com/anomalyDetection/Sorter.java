package com.anomalyDetection;

import java.util.ArrayList;
import java.util.List;

public class Sorter {
		
	private static ArrayList<Segment> segments= new ArrayList<Segment>();

	public static List<Segment> getSegments() {
		return segments;
	}

	public static void sortLines(List<Line> lines) {
		lines.stream().forEach(l -> {
			int lineId = l.getOsmId();
			if (!segments.stream().anyMatch(s -> s.getId() == lineId)) {
				segments.add(new Segment(lineId));
			}
			segments.stream().forEach(s -> {
				if (s.getId() == lineId) {
					s.addData(l);
				}
			});
		});
	}

	/* Didn't know how this was going to work
	public static void addSegment(Segment s) {
		if(segments.stream().filter(t->t.getId()==s.getId()).count()<1) {
			segments.add(s);	
		}else {
			//segments.stream().filter(t->t.getId()==s.getId()).add(s.getData()));
			for(Segment t:segments) {
				if(t.getId()==s.getId())t.addData(s.getData());
			}
		}			
	}
	 */
}
