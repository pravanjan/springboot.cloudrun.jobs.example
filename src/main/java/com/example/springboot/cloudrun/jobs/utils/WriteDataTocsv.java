package com.example.springboot.cloudrun.jobs.utils;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.opencsv.CSVWriter;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteDataTocsv {
    private static final String BUCKET_NAME = "reviews_backup_testing";
    private static final String FILENAME = "MigratedList_review_setting"+new Date().getTime()+".csv";

    @Getter
    private static  List<String[]> csvDataList = new ArrayList<>();

    public static void createRowInCSV(String[] args){
        if(csvDataList.isEmpty()){
            csvDataList.add(new String[]{"key", "activationStatus"});
            csvDataList.add(args);
        }
        else
            csvDataList.add(args);
    }

    public static void uploadIntoCloudStorage(List<String []> csvData){
        Storage storage = StorageOptions.getDefaultInstance().getService();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream))) {
            csvWriter.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, FILENAME).setContentType("text/csv").build();
        Blob blob = storage.create(blobInfo, outputStream.toByteArray());

        System.out.println("Successfully wrote data to Cloud Storage file: " + blob.getName());


    }
}
