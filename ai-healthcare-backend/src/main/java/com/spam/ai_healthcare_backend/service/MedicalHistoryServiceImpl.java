package com.spam.ai_healthcare_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.model.MedicalHistory;
import com.spam.ai_healthcare_backend.repository.MedicalHistoryRepository;
import com.spam.ai_healthcare_backend.service.MedicalHistoryService;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public MedicalHistory addMedicalHistory(MedicalHistory history) {
        return medicalHistoryRepository.save(history);
    }

    @Override
    public List<MedicalHistory> getAllMedicalHistory() {
        return medicalHistoryRepository.findAll();
    }

    @Override
    public MedicalHistory getMedicalHistoryById(Long id) {
        return medicalHistoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<MedicalHistory> getHistoryByPatientId(Long patientId) {
        return medicalHistoryRepository.findByPatientId(patientId);
    }

    @Override
    public MedicalHistory updateMedicalHistory(Long id, MedicalHistory history) {

        MedicalHistory existingHistory =
                medicalHistoryRepository.findById(id).orElse(null);

        if (existingHistory != null) {
            existingHistory.setConditionName(history.getConditionName());
            existingHistory.setDescription(history.getDescription());
            existingHistory.setDiagnosisDate(history.getDiagnosisDate());
            existingHistory.setTreatment(history.getTreatment());
            existingHistory.setAllergies(history.getAllergies());

            return medicalHistoryRepository.save(existingHistory);
        }

        return null;
    }

    @Override
    public void deleteMedicalHistory(Long id) {
        medicalHistoryRepository.deleteById(id);
    }
}
