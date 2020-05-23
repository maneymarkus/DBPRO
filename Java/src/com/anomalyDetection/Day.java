package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day {

    private final String dayDate;
    private final Date date;
    private final int month;
    private final int day;
    private final int dayOfWeek;
    private final int season;
    // INFO: Be aware of Nullpointers! Data may not be consistent, especially new Data within one Day
    private Line[] hours = new Line[24];

    public Day(Line l) {
        this.setHour(l);
        this.month = l.getMonth();
        this.day = l.getDay();
        this.dayOfWeek = l.getDayOfWeek();
        this.dayDate = l.getDayDate();
        this.date = l.getDate();
        this.season = l.getSeason();
    }
    
    public float[][] createCostMatrix(Day b){
    	float[][] costMatrix= new float[24][24];
    	if(b!=null) {
    		for(int i=0; i<24;i++) { //i = counter for day a's hours
                //float last= (float) 0.00;
    			for(int j=0; j<24;j++) { // counter for day B's hours
                    //costMatrix[i][j]=Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs())+last;
    				
    				//all neighbours left and down are null
    				if(i-1<0&&j-1<0) {
    					costMatrix[i][j]=Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    					
    				}else if(i-1<0&&j-1>=0) { // only left neighbour
    					costMatrix[i][j]=costMatrix[i][j-1]+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    					
    				}else if(i-1>=0&&j-1<0) { //only bottom neighbour
    					costMatrix[i][j]=costMatrix[i-1][j]+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    					
    				}else if(i-1>=0&&j-1>=0) { //left, bottom and bottom-left neighbour
    					
    				 //System.out.println(Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1])+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs()));
    					costMatrix[i][j]=Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1])+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    				}

    				// i, j-1 : left
    				// i-1, j : bottom
    				// i-1, j-1	 : bottom-left

    			}
    		}	
    	}
    	return costMatrix;
    }
    
    public float[][] createAccumulatedCostMatrix(List<Day> bs){
    	float[][] costMatrix= new float[24][24];
    	
    		for(int i=0; i<24;i++) { //i = counter for day a's hours
//    			float last= (float) 0.00;
    			for(Day b:bs) {
    				if(b!=null) {
    					for(int j=0; j<24;j++) { // counter for day B's hours
//    						costMatrix[i][j]=Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs())+last; //Wichtig für warp
    				
    							//all neighbours left and down are null
    						if(i-1<0&&j-1<0) {
    							costMatrix[i][j]+=Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    					
    						}else if(i-1<0&&j-1>=0) { // only left neighbour
    							costMatrix[i][j]+=costMatrix[i][j-1]+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    					
    						}else if(i-1>=0&&j-1<0) { //only bottom neighbour
    							costMatrix[i][j]+=costMatrix[i-1][j]+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    					
    						}else if(i-1>=0&&j-1>=0) { //left bottom and bottom-left neighbour 
    					
    							//System.out.println(Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1])+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs()));
    							costMatrix[i][j]+=Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1])+Math.abs(this.hours[i].getAvgVs()-b.getHour(j).getAvgVs());
    						}
    					}
    				}
    			}	
    		}
    		return costMatrix;
    	} 
//    public static Line[] perfectDay=new Line[24]; //evtl die funktion auslagern
//    public static Line[] DTWtest(List<Day> days, int h1, int h2,Line[] data){  //h1=i h2=j
//    	Line[] perfectData;
//    	if(data==null) {
//    		perfectData=new Line[24];
//    	}else {
//    		perfectData=data;
//    	}
//    	
//    	
//    	if(h1-1<0&&h2-1<0) {
//    		
//    	}
//    	return 
//    }
//    
//    public float getAVGSpeedDifferenceToOthers(Day d,List<Day> otherDays) {
//    	float difference=(float) 0.00;
//    	
//    	
//    	return difference;
//    }
    				// i, j-1 : left
    				// i-1, j : bottom
    				// i-1, j-1	 : bottom-left


    
//    public Line[] warp(List<Day>days) {
//     Line[] warp=new Line[23];
//     for(int i=23; i>=0;i--) {
////    	 for(int j=23;j>=0;j--) {
//    	 Line minErrAvg;
//    	 float err;
//    	 
//    	 
//    	 	for(Day a:days) {
//    	 		if(a!=null) {    	 			
//    			 a.createAccumulatedCostMatrix(days)[i]
//    	 		}
//    	 	}//for(day a..) ende 
////    	 }
//     } 
//    	
//    	
//    return warp;	
//    }
    

    //euklidean distance  no use of costMatrix required
    public float getTotalEuklideanDistanceToDay(Day b) {
    	float dist= (float) 0.00;
    	for(int i=0;i<24;i++) {
    		dist+=Math.abs(this.hours[i].getAvgVs()-b.getHour(i).getAvgVs());
    	}
    	//System.out.println(dist);
    	return dist;
    }
    
    //DTW-Distance
    public float getDTWDistance(float[][] costMatrix) {
        return costMatrix[23][23];
    }

    public static float getDTWDistance(float[][] costMatrix, int i, int j) {
    	return costMatrix[i][j];
    }
    
    //Getter and setter
    public String getDayDate() {
        return dayDate;
    }

    public int getSeason() {
        return this.season;
    }

    public Date getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public Line[] getHours() {
        return hours;
    }

    // returns the wanted hour of this day based on the given index
    public Line getHour(int h) {
        if (h >= 0 && h <= 23) {
            if (hours[h] != null) {
                return hours[h];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //insert new Hour (/Dataset) in this Day
    public void setHour(Line line) {
        int h = line.getHour();
        if (h >= 0 && h <= 23) {
            this.hours[h] = line;
        }
    }

    //not a normal setter
    public void setHours(List<Line> lines) {
        lines.forEach(l -> setHour(l));
    }

    public boolean hasAccident() {
        boolean accident = false;
        for (Line h : hours) {
            if (h != null) {
                if (!h.getEventType().equals("")) {
                    accident = true;
                }
            }
        }
        return accident;
    }


}
