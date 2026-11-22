package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentRequest {

    private Long doctorId;
    private LocalDateTime appointmentDate;
    private String reason;

}