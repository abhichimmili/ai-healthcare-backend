package com.spam.ai_healthcare_backend.service;

import java.util.List;

import com.spam.ai_healthcare_backend.model.MedicalHistory;

public interface MedicalHistoryService {

    MedicalHistory addMedicalHistory(MedicalHistory history);

    List<MedicalHistory> getAllMedicalHistory();

    MedicalHistory getMedicalHistoryById(Long id);

    List<MedicalHistory> getHistoryByPatientId(Long patientId);

    MedicalHistory updateMedicalHistory(Long id, MedicalHistory history);

    void deleteMedicalHistory(Long id);
}