package com.anomalyDetection;

import java.util.ArrayList;
import java.util.List;

public class Segment {
	private int id;
	private List<Line> data;
		
public Segment(int id) {
	this.id = id;
	this.data = new ArrayList<Line>();
}

public List<Line> getData() {
	return data;
}

public void setData(List<Line> data) {
	this.data = data;
}

public void addData(List<Line> data) {
	this.data.addAll(data);
}

public void addData(Line line) {
	this.data.add(line);
}

public int getId() {
	return id;
}


}

