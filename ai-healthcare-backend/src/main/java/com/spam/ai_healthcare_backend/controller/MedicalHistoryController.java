package com.spam.ai_healthcare_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.model.MedicalHistory;
import com.spam.ai_healthcare_backend.service.MedicalHistoryService;

@RestController
@RequestMapping("/api/medical-history")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @PostMapping
    public MedicalHistory addMedicalHistory(@RequestBody MedicalHistory history) {
        return medicalHistoryService.addMedicalHistory(history);
    }

    @GetMapping
    public List<MedicalHistory> getAllMedicalHistory() {
        return medicalHistoryService.getAllMedicalHistory();
    }

    @GetMapping("/{id}")
    public MedicalHistory getMedicalHistoryById(@PathVariable Long id) {
        return medicalHistoryService.getMedicalHistoryById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalHistory> getHistoryByPatientId(@PathVariable Long patientId) {
        return medicalHistoryService.getHistoryByPatientId(patientId);
    }

    @PutMapping("/{id}")
    public MedicalHistory updateMedicalHistory(
            @PathVariable Long id,
            @RequestBody MedicalHistory history) {
        return medicalHistoryService.updateMedicalHistory(id, history);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicalHistory(@PathVariable Long id) {
        medicalHistoryService.deleteMedicalHistory(id);
    }
}