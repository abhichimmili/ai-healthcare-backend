package com.spam.ai_healthcare_backend.dto;

public class HealthGuidanceRequest {
    private String symptoms;
    private String duration;
    private String severity;
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
}
