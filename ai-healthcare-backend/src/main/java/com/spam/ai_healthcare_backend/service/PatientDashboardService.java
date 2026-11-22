package com.spam.ai_healthcare_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spam.ai_healthcare_backend.dto.PatientDashboardDTO;
import com.spam.ai_healthcare_backend.dto.RecentReportDTO;
import com.spam.ai_healthcare_backend.dto.UpcomingAppointmentDTO;
import com.spam.ai_healthcare_backend.model.Patient;
import com.spam.ai_healthcare_backend.repository.AppointmentRepository;
import com.spam.ai_healthcare_backend.repository.MedicalReportRepository;
import com.spam.ai_healthcare_backend.repository.PatientRepository;
import com.spam.ai_healthcare_backend.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class PatientDashboardService {

	private final PatientRepository patientRepository;
	private final AppointmentRepository appointmentRepository;
	private final MedicalReportRepository medicalReportRepository;

	public PatientDashboardService(PatientRepository patientRepository, AppointmentRepository appointmentRepository,
			MedicalReportRepository medicalReportRepository) {

		this.patientRepository = patientRepository;
		this.appointmentRepository = appointmentRepository;
		this.medicalReportRepository = medicalReportRepository;
	}

	public PatientDashboardDTO getDashboard(String email) {

		Patient patient = patientRepository.findByUserEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

		Long patientId = patient.getId();

		Long totalAppointments = appointmentRepository.countByPatientId(patientId);

		Long upcomingAppointments = appointmentRepository.countByPatientIdAndAppointmentDateAfterAndStatusNot(patientId,
				LocalDateTime.now(), "CANCELLED");

		Long totalReports = medicalReportRepository.countByPatientId(patientId);

		return new PatientDashboardDTO(patient.getUser().getName(), totalAppointments, upcomingAppointments,
				totalReports, "NOT_ASSESSED");
	}

	public List<UpcomingAppointmentDTO> getUpcomingAppointments(String email) {

		Patient patient = patientRepository.findByUserEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

		return appointmentRepository.findUpcomingAppointments(patient.getId(), LocalDateTime.now()).stream()
				.map(a -> new UpcomingAppointmentDTO(a.getId(), a.getDoctor().getUser().getName(),
						a.getDoctor().getSpecialization(), a.getAppointmentDate(), a.getStatus()))
				.toList();
	}

	public List<RecentReportDTO> getRecentReports(String email) {

		Patient patient = patientRepository.findByUserEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

		return medicalReportRepository.findTop5ByPatientIdOrderByUploadedAtDesc(patient.getId()).stream()
				.map(r -> new RecentReportDTO(r.getId(), r.getReportName(), r.getReportType(), r.getUploadedAt()))
				.toList();
	}
}