package com.spam.ai_healthcare_backend.service;

import java.util.List;

import com.spam.ai_healthcare_backend.model.MedicalReport;

public interface MedicalReportService {

    MedicalReport uploadReport(MedicalReport report);

    List<MedicalReport> getAllReports();

    MedicalReport getReportById(Long id);

//    List<MedicalReport> getReportsByPatientId(Long patientId);

    MedicalReport updateReport(Long id, MedicalReport report);

    void deleteReport(Long id);
}