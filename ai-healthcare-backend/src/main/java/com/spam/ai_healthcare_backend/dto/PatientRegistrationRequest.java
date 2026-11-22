
package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientRegistrationRequest {

	private String name;

	private String email;

	private String phoneNumber;

	private LocalDate dateOfBirth;

	private String gender;

	private String bloodGroup;

	private String address;

	private String password;

	private String confirmPassword;
}
