package com.spam.ai_healthcare_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "medical_reports")
@Data
public class MedicalReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@Column(nullable = false)
	private String reportName;

	private String reportType;

	private String fileUrl;

	private LocalDateTime uploadedAt;

	@Column(length = 3000)
	private String aiSummary;

	@Column(name = "file_path", length = 1000)
	private String filePath;

	@Column(name = "original_file_name")
	private String originalFileName;

	@Column(name = "content_type")
	private String contentType;
}