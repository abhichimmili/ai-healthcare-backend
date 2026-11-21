package com.spam.ai_healthcare_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spam.ai_healthcare_backend.model.MedicalHistory;

public interface MedicalHistoryRepository
        extends JpaRepository<MedicalHistory, Long> {

    List<MedicalHistory> findByPatientIdOrderByDiagnosisDateDesc(
            Long patientId
    );
    
   List<MedicalHistory> findByPatientId(Long patientId);
}