package com.example.springboot.cloudrun.jobs.model;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSettings {

    private String id;
    private String accountId;
    private Boolean isAccountEnabled;
    private Boolean isDeleted; // default false
    private Boolean isNotificationEnabled; // default true
    private  String notificationType; // default "email"
    private String notificationEmail;
    private String message; // default "Welcome to the system"
    private Timestamp createdDate;
    private Timestamp updatedDate;



}
