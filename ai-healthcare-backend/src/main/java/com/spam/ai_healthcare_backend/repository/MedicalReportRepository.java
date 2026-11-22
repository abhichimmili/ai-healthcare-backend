package com.spam.ai_healthcare_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spam.ai_healthcare_backend.model.MedicalReport;

public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {

	// Dashboard: Total medical reports
	long countByPatientId(Long patientId);

	// Dashboard: Latest 5 reports
	List<MedicalReport> findTop5ByPatientIdOrderByUploadedAtDesc(Long patientId);

	// Medical Reports: Get all reports belonging to a patient
	List<MedicalReport> findByPatientUserEmailOrderByUploadedAtDesc(String email);
}