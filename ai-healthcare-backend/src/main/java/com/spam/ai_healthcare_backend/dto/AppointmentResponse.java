package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

public class AppointmentResponse {

    private Long id;
    private String patientName;
    private String patientEmail;
    private String doctorName;
    private String specialty;
    private LocalDateTime appointmentDate;
    private String reason;
    private String status;

    public AppointmentResponse(Long id, String patientName,
            String patientEmail, String doctorName, String specialty,
            LocalDateTime appointmentDate, String reason, String status) {

        this.id = id;
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }
}