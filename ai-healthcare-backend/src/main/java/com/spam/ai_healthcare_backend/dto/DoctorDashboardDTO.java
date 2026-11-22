package com.spam.ai_healthcare_backend.dto;

public record DoctorDashboardDTO(String doctorName, Long totalPatients, Long todaysAppointments, Long pendingReports,
		Long completedConsultations) {
}
