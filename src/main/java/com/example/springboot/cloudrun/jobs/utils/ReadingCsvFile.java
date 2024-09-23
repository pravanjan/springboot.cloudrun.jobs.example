package com.example.springboot.cloudrun.jobs.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadingCsvFile {
    String filePath = "src/main/resources/tmp/myfile.csv";

    public List<String[]> readFile() {

        FileReader reader = null;
        List<String[]> data = null;

        try {
            reader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(reader);
            data = csvReader.readAll();
            csvReader.close();
            reader.close();

        } catch (IOException | CsvException ex) {
            throw new RuntimeException(ex);
        }
        return data;
    }

}
