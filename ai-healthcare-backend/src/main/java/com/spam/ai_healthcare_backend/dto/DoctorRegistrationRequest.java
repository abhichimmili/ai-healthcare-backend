
package com.spam.ai_healthcare_backend.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DoctorRegistrationRequest {

	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	private String confirmPassword;

	private String specialization;
	private String qualification;
	private String medicalLicenseNumber;
	private Integer yearsOfExperience;
	private BigDecimal consultationFee;

}
