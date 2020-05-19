package com.anomalyDetection;

public class Main {

    public static void main(String[] args) {
        //String csvName = "a5_Selektion_9000_Daten.csv";
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        //String pathToCsv ="C:/users/rkrys/Downloads/a5.csv/a5.csv"; 
        //String pathToCsv = currentWorkingDir + "/Data/" + csvName;
        String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
        //System.out.println(pathToCsv);
        Data data = new Data();
        Reader reader = new Reader(pathToCsv, data);

        //reader.readSomeLines();
        reader.readLinesToObjects();

        data.getSegments().get(0).getWeeks().stream().forEach(w -> {
            if (w.getDayOfWeek(0) != null) {
                System.out.println(w.getDayOfWeek(0).getHour(0).getAvgVs());
            }
        });
        
    }
}
