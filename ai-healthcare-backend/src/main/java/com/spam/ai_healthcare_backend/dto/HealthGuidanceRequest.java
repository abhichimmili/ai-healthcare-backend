package com.spam.ai_healthcare_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HealthGuidanceRequest {
 @NotBlank(message="Please describe your symptoms") @Size(max=2000) private String symptoms;
 @NotBlank @Size(max=100) private String duration;
 @NotBlank @Size(max=50) private String severity;
 public String getSymptoms(){return symptoms;} public void setSymptoms(String symptoms){this.symptoms=symptoms;}
 public String getDuration(){return duration;} public void setDuration(String duration){this.duration=duration;}
 public String getSeverity(){return severity;} public void setSeverity(String severity){this.severity=severity;}
}
