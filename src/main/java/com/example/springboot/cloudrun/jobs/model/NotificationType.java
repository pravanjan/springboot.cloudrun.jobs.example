package com.example.springboot.cloudrun.jobs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum NotificationType {
    EMAIL("email"),

    PHONE("phone");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

}
