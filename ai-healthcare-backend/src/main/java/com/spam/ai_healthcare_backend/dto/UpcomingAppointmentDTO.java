package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

public record UpcomingAppointmentDTO(Long appointmentId, String doctorName, String specialization,
		LocalDateTime appointmentDate, String status) {
}