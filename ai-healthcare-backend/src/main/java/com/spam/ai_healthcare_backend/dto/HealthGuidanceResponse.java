package com.spam.ai_healthcare_backend.dto;

public class HealthGuidanceResponse {
    private final String guidance;
    private final String disclaimer;
    private final boolean urgent;
    public HealthGuidanceResponse(String guidance, String disclaimer, boolean urgent) {
        this.guidance = guidance; this.disclaimer = disclaimer; this.urgent = urgent;
    }
    public String getGuidance() { return guidance; }
    public String getDisclaimer() { return disclaimer; }
    public boolean isUrgent() { return urgent; }
}
