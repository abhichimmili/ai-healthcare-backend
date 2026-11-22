package com.spam.ai_healthcare_backend.dto;

import lombok.Data;

@Data
public class LoginRequest {

	private String email;
	private String password;

}