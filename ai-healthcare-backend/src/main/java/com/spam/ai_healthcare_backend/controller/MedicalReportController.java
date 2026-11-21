package com.spam.ai_healthcare_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.model.MedicalReport;
import com.spam.ai_healthcare_backend.service.MedicalReportService;

@RestController
@RequestMapping("/api/medical-reports")
public class MedicalReportController {

    @Autowired
    private MedicalReportService medicalReportService;

    @PostMapping
    public MedicalReport uploadReport(@RequestBody MedicalReport report) {
        return medicalReportService.uploadReport(report);
    }

    @GetMapping
    public List<MedicalReport> getAllReports() {
        return medicalReportService.getAllReports();
    }

    @GetMapping("/{id}")
    public MedicalReport getReportById(@PathVariable Long id) {
        return medicalReportService.getReportById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalReport> getReportsByPatientId(@PathVariable Long patientId) {
        return medicalReportService.getReportsByPatientId(patientId);
    }

    @PutMapping("/{id}")
    public MedicalReport updateReport(@PathVariable Long id,
            @RequestBody MedicalReport report) {
        return medicalReportService.updateReport(id, report);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        medicalReportService.deleteReport(id);
    }
}