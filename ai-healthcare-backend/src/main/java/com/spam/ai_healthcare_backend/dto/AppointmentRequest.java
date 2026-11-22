package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppointmentRequest { @NotNull @Positive private Long doctorId; @NotNull @Future private LocalDateTime appointmentDate; @Size(max=1000) private String reason; }
