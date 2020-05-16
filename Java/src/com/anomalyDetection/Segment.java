package com.anomalyDetection;

import java.util.ArrayList;
import java.util.List;

public class Segment {
	private int id;
	private List<Line> data;
	@SuppressWarnings("unchecked")
	private List<Line>[] daylieData=new ArrayList[730];
	float[] favoriteDistance= new float[730];
	Line[] favorite= new Line[730];
	String[] date= new String[730];
	
	
	//2*365=730 tage für 2 jahre... theoretisch noch optimierbar für stream aber so erstmal der entspannteste weg...
	//0-364   =2017
	//365-730 =2018
	
//constructor		
public Segment(int id) {
	this.id = id;
	this.data = new ArrayList<Line>();
}


//sort by Day
public void sortByDay() {
	
	for(Line l:data) {
		if(l.getYear()==2017) {
			if(daylieData[l.getDay()-1]==null) {
				daylieData[l.getDay()-1]=new ArrayList<Line>();
				System.out.println("done adding 17");
			}
			daylieData[l.getDay()-1].add(l);
		}else {// 2018
			if(daylieData[364+l.getDay()]==null) {
				daylieData[364+l.getDay()]=new ArrayList<Line>();
				System.out.println("done adding 18");
			}
			daylieData[364+l.getDay()].add(l);
		}
	}
		
}

//evaluate
public void evaluateSegmentbyDay() {
	for(int i=0; i<730; i++) {
	if(this.daylieData[i]!=null) {
		for(Line l:this.daylieData[i]) { //für jede einzelne Line in dem Tag
			//Line temp=l;
			float dist=(float) 0.00; //math.abs
				for(Line m:this.daylieData[i]) { //check den abstand zu den anderen lines an dem Tag
					dist+=Math.abs(l.getAvgVs()-m.getAvgVs()); 
				}
			
			if(favorite[i]==null) { //falls favorit noch nicht bekannt
				favorite[i]=l;
			}else if(favoriteDistance[i]>dist) { //falls die gemessene Distanz besser ist als der derzeitige wert
				favorite[i]=l;
				System.out.println(i + "     " + dist);
			}
		}
	}
	}
}


//Getter und Setter:
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

public Line getFavorite(int i) {
	return favorite[i];
}

public void setFavorite(Line favorite, int i) {
	this.favorite[i] = favorite;
}

public float getFavoriteDistance(int i) {
	return favoriteDistance[i];
}

public void setFavoriteDistance(float favoriteDistance, int i) {
	this.favoriteDistance[i] = favoriteDistance;
}


}


