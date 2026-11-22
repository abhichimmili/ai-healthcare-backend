package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

public record MedicalReportResponse(Long id, String reportName, String reportType, String originalFileName,
		String contentType, LocalDateTime uploadedAt, String aiSummary) {
}
