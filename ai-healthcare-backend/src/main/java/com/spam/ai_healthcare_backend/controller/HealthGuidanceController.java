package com.spam.ai_healthcare_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.spam.ai_healthcare_backend.dto.HealthGuidanceRequest;
import com.spam.ai_healthcare_backend.dto.HealthGuidanceResponse;
import com.spam.ai_healthcare_backend.service.HealthGuidanceService;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize("hasRole('PATIENT')")
public class HealthGuidanceController {
    private final HealthGuidanceService service;
    public HealthGuidanceController(HealthGuidanceService service) { this.service = service; }
    @PostMapping("/health-guidance")
    public ResponseEntity<HealthGuidanceResponse> getGuidance(@RequestBody HealthGuidanceRequest request) {
        return ResponseEntity.ok(service.getGuidance(request));
    }
}
