package com.anomalyDetection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reader {

    private String dataPath;
    private Stream<String> dataStream;

    public Reader (String dataPath) {
        this.dataPath = dataPath;
        try {
            this.dataStream = Files.lines(Paths.get(dataPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSomeLines() {
        dataStream.limit(5).forEach(System.out::println);
    }

    public List<Line> readLinesToObjects() {
        List<Line> readLines = dataStream
            .skip(1) //skip first line
            .map(
                line -> {
                    Line l = new Line(line);
                    return l;
                }
            ).collect(Collectors.toList());
        return readLines;
    }

}
