package com.spam.ai_healthcare_backend.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.spam.ai_healthcare_backend.dto.MedicalReportResponse;

public interface MedicalReportService {

	MedicalReportResponse uploadReport(String email, String reportName, String reportType, MultipartFile file);

	List<MedicalReportResponse> getMyReports(String email);

	MedicalReportResponse getReportById(Long id, String email);

	Resource downloadReport(Long id, String email);

	void deleteReport(Long id, String email);
}