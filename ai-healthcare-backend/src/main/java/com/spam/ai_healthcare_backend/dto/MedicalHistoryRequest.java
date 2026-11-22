package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicalHistoryRequest {

    @NotBlank(message = "Condition name is required")
    @Size(max = 150)
    private String conditionName;

    @Size(max = 2000)
    private String description;

    @Size(max = 50)
    private String category;

    @Size(max = 150)
    private String doctorOrHospital;

    @NotNull(message = "Diagnosis date is required")
    private LocalDate diagnosisDate;

    @Size(max = 1000)
    private String treatment;

    @Size(max = 1000)
    private String allergies;


}