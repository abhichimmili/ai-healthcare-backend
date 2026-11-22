package com.spam.ai_healthcare_backend.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorRegistrationRequest {
 @NotBlank @Size(max=100) private String name;
 @NotBlank @Email @Size(max=150) private String email;
 @NotBlank @Pattern(regexp="^[0-9+() -]{7,15}$", message="Enter a valid phone number") private String phoneNumber;
 @NotBlank @Size(min=8,max=72) private String password;
 @NotBlank private String confirmPassword;
 @NotBlank @Size(max=100) private String specialization;
 @NotBlank @Size(max=150) private String qualification;
 @NotBlank @Size(max=100) private String medicalLicenseNumber;
 @NotNull @Min(0) @Max(70) private Integer yearsOfExperience;
 @NotNull @DecimalMin("0.0") @Digits(integer=8,fraction=2) private BigDecimal consultationFee;
}
