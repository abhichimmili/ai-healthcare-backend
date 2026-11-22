package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

public record AppointmentResponse(Long id, String patientName, String patientEmail, String doctorName, String specialty,
		LocalDateTime appointmentDate, String reason, String status) {
}