package com.spam.ai_healthcare_backend.dto;

public class DoctorDashboardDTO {

    private String doctorName;
    private Long totalPatients;
    private Long todaysAppointments;
    private Long pendingReports;
    private Long completedConsultations;

    public DoctorDashboardDTO(String doctorName,
            Long totalPatients,
            Long todaysAppointments,
            Long pendingReports,
            Long completedConsultations) {

        this.doctorName = doctorName;
        this.totalPatients = totalPatients;
        this.todaysAppointments = todaysAppointments;
        this.pendingReports = pendingReports;
        this.completedConsultations = completedConsultations;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Long getTotalPatients() {
        return totalPatients;
    }

    public Long getTodaysAppointments() {
        return todaysAppointments;
    }

    public Long getPendingReports() {
        return pendingReports;
    }

    public Long getCompletedConsultations() {
        return completedConsultations;
    }
}