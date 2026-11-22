package com.spam.ai_healthcare_backend.controller;

import java.security.Principal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spam.ai_healthcare_backend.dto.MedicalReportResponse;
import com.spam.ai_healthcare_backend.service.MedicalReportService;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequestMapping("/api/medical-reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PATIENT')")
public class MedicalReportController {

	private final MedicalReportService reportService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MedicalReportResponse> uploadReport(Principal principal, @RequestParam @NotBlank @Size(max = 150) String reportName,
			@RequestParam(required = false) @Size(max = 50) String reportType, @RequestParam MultipartFile file) {

		return ResponseEntity.status(201)
				.body(reportService.uploadReport(principal.getName(), reportName, reportType, file));
	}

	@GetMapping
	public ResponseEntity<List<MedicalReportResponse>> getMyReports(Principal principal) {

		return ResponseEntity.ok(reportService.getMyReports(principal.getName()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicalReportResponse> getReport(@PathVariable Long id, Principal principal) {

		return ResponseEntity.ok(reportService.getReportById(id, principal.getName()));
	}

	@GetMapping("/{id}/download")
	public ResponseEntity<Resource> downloadReport(@PathVariable Long id, Principal principal) {

		Resource resource = reportService.downloadReport(id, principal.getName());

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment").body(resource);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReport(@PathVariable Long id, Principal principal) {

		reportService.deleteReport(id, principal.getName());

		return ResponseEntity.noContent().build();
	}
}