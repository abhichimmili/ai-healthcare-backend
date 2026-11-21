package com.spam.ai_healthcare_backend.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.model.MedicalReport;
import com.spam.ai_healthcare_backend.repository.MedicalReportRepository;
import com.spam.ai_healthcare_backend.service.MedicalReportService;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    @Override
    public MedicalReport uploadReport(MedicalReport report) {
        report.setUploadedAt(LocalDateTime.now());
        return medicalReportRepository.save(report);
    }

    @Override
    public List<MedicalReport> getAllReports() {
        return medicalReportRepository.findAll();
    }

    @Override
    public MedicalReport getReportById(Long id) {
        return medicalReportRepository.findById(id).orElse(null);
    }

//    @Override
//    public List<MedicalReport> getReportsByPatientId(Long patientId) {
//        return medicalReportRepository.findByPatientId(patientId);
//    }

    @Override
    public MedicalReport updateReport(Long id, MedicalReport report) {

        MedicalReport existingReport =
                medicalReportRepository.findById(id).orElse(null);

        if (existingReport != null) {
            existingReport.setReportName(report.getReportName());
            existingReport.setReportType(report.getReportType());
            existingReport.setFileUrl(report.getFileUrl());
            existingReport.setAiSummary(report.getAiSummary());

            return medicalReportRepository.save(existingReport);
        }

        return null;
    }

    @Override
    public void deleteReport(Long id) {
        medicalReportRepository.deleteById(id);
    }
}