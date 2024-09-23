package com.example.springboot.cloudrun.jobs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettings {
    private String id;
    private NotificationType type;
    private Boolean isActive;
    private String message;

}
