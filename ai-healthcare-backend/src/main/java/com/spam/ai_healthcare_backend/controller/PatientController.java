
package com.spam.ai_healthcare_backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.dto.PatientRegistrationRequest;
import com.spam.ai_healthcare_backend.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping("/register")
	public ResponseEntity<String> registerPatient(@Valid @RequestBody PatientRegistrationRequest request) {

		String response = patientService.registerPatient(request);

		return ResponseEntity.ok(response);
	}
}
