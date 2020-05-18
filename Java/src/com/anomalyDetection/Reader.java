package com.anomalyDetection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reader {

    private String dataPath;
    private Stream<String> dataStream;
    private Data data;

    public Reader (String dataPath, Data data) {
        this.dataPath = dataPath;
        try {
            this.dataStream = Files.lines(Paths.get(dataPath));
            this.data = data;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSomeLines() {
        dataStream.limit(5).forEach(System.out::println);
    }

    public void readLinesToObjects() {
        dataStream
            .skip(1) //skip first line
            .forEach(
                line -> {
                    Line l = new Line(line);
                    this.data.addLine(l);
                }
            );
    }

}
