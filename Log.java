package ru.geekbrains.HomeWork4;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Log {
    private final String fileName;

    Log(String logFile) {
        fileName = logFile;
    }

    public void append(String logstring) {
        String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
        try (FileWriter logfile = new FileWriter(fileName, true);) {
            logfile.write( time + ": " + logstring + "\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


}
