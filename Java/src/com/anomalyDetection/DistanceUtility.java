package com.anomalyDetection;

import java.util.List;

public class DistanceUtility {

    public static float[][] createCostMatrix(Day a, Day b){
        float[][] costMatrix = new float[24][24];
        if(a != null && b != null) {
            for(int i = 0; i < 24; i++) { //i = counter for day a's hours
                //float last= (float) 0.00;
                for(int j = 0; j < 24; j++) { // counter for day B's hours
                    //costMatrix[i][j]=Math.abs(a.getHour(i).getAvgVs()-b.getHour(j).getAvgVs())+last;

                    //all neighbours left and down are null
                    if(i - 1 < 0 && j - 1 < 0) {
                        costMatrix[i][j] = Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());

                    }else if(i - 1 < 0 && j - 1 >= 0) { // only left neighbour
                        costMatrix[i][j] = costMatrix[i][j-1] + Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());

                    }else if(i - 1 >= 0 && j - 1 < 0) { //only bottom neighbour
                        costMatrix[i][j] = costMatrix[i-1][j] + Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());

                    }else if(i - 1 >= 0 && j - 1 >= 0) { //left, bottom and bottom-left neighbour

                        //System.out.println(Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1])+Math.abs(a.getHour(i).getAvgVs()-b.getHour(j).getAvgVs()));
                        costMatrix[i][j] = Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1]) + Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());
                    }

                    // i, j-1 : left
                    // i-1, j : bottom
                    // i-1, j-1	 : bottom-left

                }
            }
        }
        return costMatrix;
    }

    public static float[][] createAccumulatedCostMatrix(Day a, List<Day> bs){
        float[][] costMatrix = new float[24][24];
        if (a != null) {
            for (int i = 0; i < 24; i++) { //i = counter for day a's hours
                //float last= (float) 0.00;
                for (Day b : bs) {
                    if (b != null) {
                        for (int j = 0; j < 24; j++) { // counter for day B's hours
                            //costMatrix[i][j] = Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs())+last; //Wichtig für warp

                            //all neighbours left and down are null
                            if (i - 1 < 0 && j - 1 < 0) {
                                costMatrix[i][j] += Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());

                            } else if (i - 1 < 0 && j - 1 >= 0) { // only left neighbour
                                costMatrix[i][j] += costMatrix[i][j - 1] + Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());

                            } else if (i - 1 >= 0 && j - 1 < 0) { //only bottom neighbour
                                costMatrix[i][j] += costMatrix[i - 1][j] + Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());

                            } else if (i - 1 >= 0 && j - 1 >= 0) { //left bottom and bottom-left neighbour

                                //System.out.println(Math.min(Math.min(costMatrix[i-1][j], costMatrix[i][j-1]), costMatrix[i-1][j-1])+Math.abs(a.getHour(i).getAvgVs()-b.getHour(j).getAvgVs()));
                                costMatrix[i][j] += Math.min(Math.min(costMatrix[i - 1][j], costMatrix[i][j - 1]), costMatrix[i - 1][j - 1]) + Math.abs(a.getHour(i).getAvgVs() - b.getHour(j).getAvgVs());
                            }
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
    public static float getTotalEuklideanDistanceToDay(Day a, Day b) {
        float dist = (float) 0.00;
        for(int i = 0; i < 24; i++) {
            dist += Math.abs(a.getHour(i).getAvgVs() - b.getHour(i).getAvgVs());
        }
        //System.out.println(dist);
        return dist;
    }
    
    public static float getEuklideanDistanceToDayAtH(Day a, Day b,int h) {
        float dist = (float) 0.00;
            dist = Math.abs(a.getHour(h).getAvgVs() - b.getHour(h).getAvgVs());
        
        //System.out.println(dist);
        return dist;
    }


    //DTW-Distance
    public static float getDTWDistance(float[][] costMatrix) {
        return costMatrix[23][23];
    }

    public static float getDTWDistance(float[][] costMatrix, int i, int j) {
        return costMatrix[i][j];
    }
}
