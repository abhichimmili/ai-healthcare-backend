package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDate;

public record MedicalHistoryResponse(Long id, String conditionName, String category, String doctorOrHospital, String description, LocalDate diagnosisDate, String treatment, String allergies) { }
