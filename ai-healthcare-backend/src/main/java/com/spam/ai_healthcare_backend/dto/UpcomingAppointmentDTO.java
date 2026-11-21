package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

public class UpcomingAppointmentDTO {

    private Long appointmentId;
    private String doctorName;
    private String specialization;
    private LocalDateTime appointmentDate;
    private String status;

    public UpcomingAppointmentDTO(
            Long appointmentId,
            String doctorName,
            String specialization,
            LocalDateTime appointmentDate,
            String status) {

        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getStatus() {
        return status;
    }
}