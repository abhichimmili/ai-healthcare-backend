package com.spam.ai_healthcare_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.dto.DoctorDashboardDTO;
import com.spam.ai_healthcare_backend.service.DoctorDashboardService;

@RestController
@RequestMapping("/api/doctor/dashboard")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorDashboardController {

    private final DoctorDashboardService dashboardService;

    public DoctorDashboardController(
            DoctorDashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DoctorDashboardDTO> getDashboard(
            Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(
                        authentication.getName()
                )
        );
    }
}