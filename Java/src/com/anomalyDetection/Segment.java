package com.anomalyDetection;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Segment {
	private int id;
	private List<Line> data;
	@SuppressWarnings("unchecked")
	public List<Line>[][] daylieData=new ArrayList[730][24]; //3d array ... 2d- tage und stunden   3. dimension die line-data
	float[][] favoriteDistance= new float[730][24];
	Line[][] favorite= new Line[730][24];
	//String[] date= new String[730];
	
	
	//2*365=730 tage für 2 jahre...
	//0-364   =2017
	//365-730 =2018
	
	//constructor
	public Segment(int id) {
		this.id = id;
		this.data = new ArrayList<Line>();
	}


	//sort by Hour and Day
	@Deprecated
	public void sortByHour() {

		for(Line l:data) {
			if(l.getYear()==2017) {
				if(daylieData[l.getDay()-1][l.getHour()]==null) {
					daylieData[l.getDay()-1][l.getHour()]=new ArrayList<Line>();
					daylieData[l.getDay()-1][l.getHour()].add(l);
					//System.out.println("done adding 17");
				}
			}else {// 2018
				if(daylieData[364+l.getDay()][l.getHour()]==null) {
					daylieData[364+l.getDay()][l.getHour()]=new ArrayList<Line>();
					//System.out.println("done adding 18");
				}
				daylieData[364+l.getDay()][l.getHour()].add(l);
			}
		}

	}

	//evaluate
	@Deprecated
	public void evaluateSegmentbyHour() {
		for(int d=0; d<730; d++) {  //für jeden tag
			if(this.daylieData[d]!=null) {
				//for(Line l:this.daylieData[i]) {
				for(int h=0;h<24;h++) { //hour counter für jede stunde an dem tag
					//Line temp=l;
					float dist=(float) 0.00; //math.abs
					if(this.daylieData[d][h]!=null) {
						for(Line l:daylieData[d][h]) { //für jede einzelne line an dem tag in der stunde
							for(Line m:daylieData[d][h]) {//check den abstand zu den anderen lines an dem Tag in der stunde
								System.out.println(l.getAvgVs() +"    "+Math.abs(l.getAvgVs())+"      "+l.getAvgVs());
								dist+=Math.abs(l.getAvgVs()-m.getAvgVs());
							}

							if(favorite[d][h]==null) { //falls favorit noch nicht bekannt
								favorite[d][h]=l;
							}else if(favoriteDistance[d][h]>dist) { //falls die gemessene Distanz besser ist als der derzeitige wert
								favorite[d][h]=l;
								System.out.println(d + "     " + dist);
							}
						}
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

	public Line getFavorite(int d, int h) {
		return favorite[d][h];
	}

	public void setFavorite(Line favorite, int d,int h) {
		this.favorite[d][h] = favorite;
	}

	public float getFavoriteDistance(int d,int h) {
		return favoriteDistance[d][h];
	}

	public void setFavoriteDistance(float favoriteDistance, int d,int h) {
		this.favoriteDistance[d][h] = favoriteDistance;
	}

}


