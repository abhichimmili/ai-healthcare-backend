package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientRegistrationRequest {
 @NotBlank @Size(max=100) private String name;
 @NotBlank @Email @Size(max=150) private String email;
 @NotBlank @Pattern(regexp="^[0-9+() -]{7,15}$", message="Enter a valid phone number") private String phoneNumber;
 @NotNull @Past(message="Date of birth must be in the past") private LocalDate dateOfBirth;
 @NotBlank @Pattern(regexp="(?i)^(MALE|FEMALE|OTHER)$", message="Gender must be MALE, FEMALE or OTHER") private String gender;
 @Size(max=10) private String bloodGroup;
 @Size(max=255) private String address;
 @NotBlank @Size(min=8,max=72) private String password;
 @NotBlank private String confirmPassword;
}
