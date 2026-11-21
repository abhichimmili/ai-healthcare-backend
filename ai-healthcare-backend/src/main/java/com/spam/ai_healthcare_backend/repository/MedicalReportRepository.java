package com.spam.ai_healthcare_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spam.ai_healthcare_backend.model.MedicalReport;

public interface MedicalReportRepository
        extends JpaRepository<MedicalReport, Long> {

    long countByPatientId(Long patientId);

    List<MedicalReport> findTop5ByPatientIdOrderByUploadedAtDesc(
            Long patientId
    );
    
    List<MedicalReport> findPatientById(
            Long patientId
    );
    

}