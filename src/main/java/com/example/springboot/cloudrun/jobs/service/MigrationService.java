package com.example.springboot.cloudrun.jobs.service;

import com.example.springboot.cloudrun.jobs.model.NotificationSettings;
import com.example.springboot.cloudrun.jobs.model.NotificationType;
import com.example.springboot.cloudrun.jobs.service.data.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
@RequiredArgsConstructor
public class MigrationService {
    private final ApiCaller apiCaller;

    public NotificationSettings convertToNotificationSettings(String docId , Map<String, Object> dataMap){
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setId(docId);
        notificationSettings.setIsActive((Boolean) dataMap.get("isActive"));
        notificationSettings.setMessage(dataMap.get("message").toString());
        notificationSettings.setType(dataMap.get("type").equals("Email")?NotificationType.EMAIL:NotificationType.PHONE);

        return notificationSettings;

    }

    public ApiResponse convertToAccountInformation(Map<String, Object> data) {
        try {
            return apiCaller.callApi(new ObjectMapper().writeValueAsString(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
