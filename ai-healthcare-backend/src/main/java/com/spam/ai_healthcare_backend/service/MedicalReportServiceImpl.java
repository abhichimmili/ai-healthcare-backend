package com.spam.ai_healthcare_backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.spam.ai_healthcare_backend.dto.MedicalReportResponse;
import com.spam.ai_healthcare_backend.model.MedicalReport;
import com.spam.ai_healthcare_backend.model.Patient;
import com.spam.ai_healthcare_backend.repository.MedicalReportRepository;
import com.spam.ai_healthcare_backend.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalReportServiceImpl implements MedicalReportService {

	private final MedicalReportRepository reportRepository;
	private final PatientRepository patientRepository;
	private final Path storagePath;

	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

	private static final List<String> ALLOWED_TYPES = List.of("application/pdf", "image/jpeg", "image/png");

	@Override
	public MedicalReportResponse uploadReport(String email, String reportName, String reportType, MultipartFile file) {

		if (file == null || file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please select a file");
		}

		if (file.getSize() > MAX_FILE_SIZE) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size must not exceed 5 MB");
		}

		String contentType = file.getContentType();

		if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PDF, JPG and PNG files are allowed");
		}

		if (reportName == null || reportName.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Report name is required");
		}

		Patient patient = patientRepository.findByUserEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient profile not found"));

		String originalName = Path.of(file.getOriginalFilename() == null ? "report" : file.getOriginalFilename())
				.getFileName().toString();

		String extension = getExtension(originalName);

		// Validate extension as well as the supplied content type.
		if (!List.of("pdf", "jpg", "jpeg", "png").contains(extension)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file extension");
		}

		String storedName = UUID.randomUUID() + "." + extension;

		Path destination = storagePath.resolve(storedName).normalize();

		if (!destination.startsWith(storagePath)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
		}

		try {
			Files.copy(file.getInputStream(), destination);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to store uploaded file");
		}

		try {
			MedicalReport report = new MedicalReport();

			report.setPatient(patient);
			report.setReportName(reportName.trim());
			report.setReportType(reportType);
			report.setOriginalFileName(originalName);
			report.setContentType(contentType);
			report.setFilePath(storedName);
			report.setFileUrl(null);
			report.setUploadedAt(java.time.LocalDateTime.now());

			return convertToResponse(reportRepository.save(report));

		} catch (RuntimeException e) {
			try {
				Files.deleteIfExists(destination);
			} catch (IOException ignored) {
			}
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<MedicalReportResponse> getMyReports(String email) {

		return reportRepository.findByPatientUserEmailOrderByUploadedAtDesc(email).stream().map(this::convertToResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public MedicalReportResponse getReportById(Long id, String email) {

		return convertToResponse(getOwnedReport(id, email));
	}

	@Override
	@Transactional(readOnly = true)
	public Resource downloadReport(Long id, String email) {

		MedicalReport report = getOwnedReport(id, email);

		try {
			Path file = storagePath.resolve(report.getFilePath()).normalize();

			if (!file.startsWith(storagePath)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
			}

			Resource resource = new UrlResource(file.toUri());

			if (!resource.exists() || !resource.isReadable()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report file not found");
			}

			return resource;

		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to read report file");
		}
	}

	@Override
	public void deleteReport(Long id, String email) {

		MedicalReport report = getOwnedReport(id, email);

		reportRepository.delete(report);

		try {
			Files.deleteIfExists(storagePath.resolve(report.getFilePath()).normalize());
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Report deleted from database but file cleanup failed");
		}
	}

	private MedicalReport getOwnedReport(Long id, String email) {

		MedicalReport report = reportRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical report not found"));

		if (!report.getPatient().getUser().getEmail().equals(email)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical report not found");
		}

		return report;
	}

	private MedicalReportResponse convertToResponse(MedicalReport report) {

		return new MedicalReportResponse(report.getId(), report.getReportName(), report.getReportType(),
				report.getOriginalFileName(), report.getContentType(), report.getUploadedAt(), report.getAiSummary());
	}

	private String getExtension(String filename) {

		int index = filename.lastIndexOf('.');

		if (index < 0) {
			return "";
		}

		return filename.substring(index + 1).toLowerCase();
	}
}