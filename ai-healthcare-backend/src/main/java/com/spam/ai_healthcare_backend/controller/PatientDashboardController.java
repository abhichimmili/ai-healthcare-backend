package com.spam.ai_healthcare_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.dto.PatientDashboardDTO;
import com.spam.ai_healthcare_backend.dto.UpcomingAppointmentDTO;
import com.spam.ai_healthcare_backend.dto.RecentReportDTO;

import com.spam.ai_healthcare_backend.service.PatientDashboardService;

@RestController
@RequestMapping("/api/patient/dashboard")
@PreAuthorize("hasRole('PATIENT')")
public class PatientDashboardController {

    private final PatientDashboardService dashboardService;

    public PatientDashboardController(
            PatientDashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<PatientDashboardDTO> getDashboard(
            Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/upcoming-appointments")
    public ResponseEntity<List<UpcomingAppointmentDTO>>
            getUpcomingAppointments(Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.getUpcomingAppointments(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/recent-reports")
    public ResponseEntity<List<RecentReportDTO>>
            getRecentReports(Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.getRecentReports(
                        authentication.getName()
                )
        );
    }
}