package com.spam.ai_healthcare_backend.dto;

public class PatientDashboardDTO {

    private String patientName;
    private Long totalAppointments;
    private Long upcomingAppointments;
    private Long totalMedicalReports;
    private String healthStatus;

    public PatientDashboardDTO(String patientName,
            Long totalAppointments,
            Long upcomingAppointments,
            Long totalMedicalReports,
            String healthStatus) {

        this.patientName = patientName;
        this.totalAppointments = totalAppointments;
        this.upcomingAppointments = upcomingAppointments;
        this.totalMedicalReports = totalMedicalReports;
        this.healthStatus = healthStatus;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getTotalAppointments() {
        return totalAppointments;
    }

    public Long getUpcomingAppointments() {
        return upcomingAppointments;
    }

    public Long getTotalMedicalReports() {
        return totalMedicalReports;
    }

    public String getHealthStatus() {
        return healthStatus;
    }
}
