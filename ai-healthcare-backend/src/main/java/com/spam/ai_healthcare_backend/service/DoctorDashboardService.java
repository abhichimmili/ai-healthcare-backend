package com.spam.ai_healthcare_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spam.ai_healthcare_backend.dto.DoctorDashboardDTO;
import com.spam.ai_healthcare_backend.model.Doctor;

import com.spam.ai_healthcare_backend.repository.DoctorRepository;
import com.spam.ai_healthcare_backend.repository.AppointmentRepository;

@Service
@Transactional(readOnly = true)
public class DoctorDashboardService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorDashboardService(
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public DoctorDashboardDTO getDashboard(String email) {

        Doctor doctor = doctorRepository.findByUserEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("Doctor profile not found"));

        Long doctorId = doctor.getId();

        long totalPatients =
                appointmentRepository
                .countDistinctPatientsByDoctorId(doctorId);

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        long todaysAppointments =
                appointmentRepository
                .countByDoctorIdAndAppointmentDateBetween(
                        doctorId,
                        start,
                        end
                );

        long completedConsultations =
                appointmentRepository.countByDoctorIdAndStatus(
                        doctorId,
                        "COMPLETED"
                );

        // Pending reports will be implemented after adding
        // doctor-review relationships to MedicalReport.

        long pendingReports = 0;

        return new DoctorDashboardDTO(
                doctor.getUser().getName(),
                totalPatients,
                todaysAppointments,
                pendingReports,
                completedConsultations
        );
    }
}