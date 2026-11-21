
package com.spam.ai_healthcare_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.dto.DoctorRegistrationRequest;
import com.spam.ai_healthcare_backend.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<String> registerDoctor(
            @RequestBody DoctorRegistrationRequest request) {

        String response = doctorService.registerDoctor(request);

        return ResponseEntity.ok(response);
    }
}
