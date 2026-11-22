package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicalHistoryRequest {

    @NotBlank(message = "Condition name is required")
    private String conditionName;

    @Size(max = 2000)
    private String description;

    @NotNull(message = "Diagnosis date is required")
    private LocalDate diagnosisDate;

    private String treatment;

    private String allergies;


}