package com.anomalyDetection;

import java.util.ArrayList;

public class Sorter {
		
	public static ArrayList<Segment> segments= new ArrayList<Segment>();
	
	
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
	
	
}
