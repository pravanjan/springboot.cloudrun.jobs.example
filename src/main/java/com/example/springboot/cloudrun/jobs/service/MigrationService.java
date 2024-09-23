package com.example.springboot.cloudrun.jobs.service;

import com.example.springboot.cloudrun.jobs.model.NotificationSettings;
import com.example.springboot.cloudrun.jobs.model.NotificationType;

import java.util.Map;

public class MigrationService {

    public NotificationSettings convertToNotificationSettings(String docId , Map<String, Object> dataMap){
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setId(docId);
        notificationSettings.setIsActive((Boolean) dataMap.get("isActive"));
        notificationSettings.setMessage(dataMap.get("message").toString());
        notificationSettings.setType(dataMap.get("type").equals("Email")?NotificationType.EMAIL:NotificationType.PHONE);

        return notificationSettings;

    }
}
